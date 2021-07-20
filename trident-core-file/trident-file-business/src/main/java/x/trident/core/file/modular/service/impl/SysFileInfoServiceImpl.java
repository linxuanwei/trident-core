
package x.trident.core.file.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.file.api.FileInfoApi;
import x.trident.core.file.api.FileOperatorApi;
import x.trident.core.file.api.constants.FileConstants;
import x.trident.core.file.api.enums.FileStatusEnum;
import x.trident.core.file.api.exception.FileException;
import x.trident.core.file.api.exception.enums.FileExceptionEnum;
import x.trident.core.file.api.expander.FileConfigExpander;
import x.trident.core.file.api.pojo.request.SysFileInfoRequest;
import x.trident.core.file.api.pojo.response.SysFileInfoListResponse;
import x.trident.core.file.api.pojo.response.SysFileInfoResponse;
import x.trident.core.file.api.util.DownloadUtil;
import x.trident.core.file.api.util.PdfFileTypeUtil;
import x.trident.core.file.api.util.PicFileTypeUtil;
import x.trident.core.file.modular.entity.SysFileInfo;
import x.trident.core.file.modular.factory.FileInfoFactory;
import x.trident.core.file.modular.mapper.SysFileInfoMapper;
import x.trident.core.file.modular.service.SysFileInfoService;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.util.HttpServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static x.trident.core.file.api.constants.FileConstants.DEFAULT_BUCKET_NAME;
import static x.trident.core.file.api.constants.FileConstants.FILE_POSTFIX_SEPARATOR;
import static x.trident.core.file.api.exception.enums.FileExceptionEnum.FILE_NOT_FOUND;

/**
 * 文件信息表 服务实现类
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
@Service
@Slf4j
public class SysFileInfoServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements SysFileInfoService, FileInfoApi {

    @Resource
    private FileOperatorApi fileOperatorApi;

    @Override
    public SysFileInfoResponse getFileInfoResult(Long fileId) {

        // 查询库中文件信息
        SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
        sysFileInfoRequest.setFileId(fileId);
        SysFileInfo sysFileInfo = this.querySysFileInfo(sysFileInfoRequest);

        // 获取文件字节码
        byte[] fileBytes;
        try {
            fileBytes = fileOperatorApi.getFileBytes(DEFAULT_BUCKET_NAME, sysFileInfo.getFileObjectName());
        } catch (Exception e) {
            log.error("获取文件流异常，具体信息为：{}", e.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR);
        }

        // 设置文件字节码
        SysFileInfoResponse sysFileInfoResult = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, sysFileInfoResult);
        sysFileInfoResult.setFileBytes(fileBytes);

        return sysFileInfoResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileInfoResponse uploadFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

        // 文件请求转换存入数据库的附件信息
        SysFileInfo sysFileInfo = FileInfoFactory.createSysFileInfo(file, sysFileInfoRequest);

        // 默认版本号从1开始
        sysFileInfo.setFileVersion(1);

        // 文件编码生成
        sysFileInfo.setFileCode(IdWorker.getId());

        // 保存附件到附件信息表
        this.save(sysFileInfo);

        // 返回文件信息体
        SysFileInfoResponse fileUploadInfoResult = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, fileUploadInfoResult);
        return fileUploadInfoResult;
    }

    @Override
    public SysFileInfoResponse updateFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

        Long fileCode = sysFileInfoRequest.getFileCode();

        // 转换存入数据库的附件信息
        SysFileInfo sysFileInfo = FileInfoFactory.createSysFileInfo(file, sysFileInfoRequest);
        sysFileInfo.setDelFlag(YesOrNotEnum.Y.getCode());
        sysFileInfo.setFileCode(fileCode);

        // 查询该code下的最新版本号附件信息
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileInfo::getFileCode, fileCode);
        queryWrapper.eq(SysFileInfo::getDelFlag, YesOrNotEnum.N.getCode());
        queryWrapper.eq(SysFileInfo::getFileStatus, FileStatusEnum.NEW.getCode());
        SysFileInfo fileInfo = this.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(fileInfo)) {
            throw new FileException(FileExceptionEnum.NOT_EXISTED);
        }

        // 设置版本号在原本的基础上加一
        sysFileInfo.setFileVersion(fileInfo.getFileVersion() + 1);

        // 存储新版本文件信息
        this.save(sysFileInfo);

        // 返回文件信息体
        SysFileInfoResponse fileUploadInfoResult = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, fileUploadInfoResult);
        return fileUploadInfoResult;
    }

    @Override
    public void download(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

        // 根据文件id获取文件信息结果集
        SysFileInfoResponse sysFileInfoResponse = this.getFileInfoResult(sysFileInfoRequest.getFileId());

        // 如果文件加密等级不符合，文件不允许被访问
        if (!sysFileInfoRequest.getSecretFlag().equals(sysFileInfoResponse.getSecretFlag())) {
            throw new FileException(FileExceptionEnum.FILE_DENIED_ACCESS);
        }

        DownloadUtil.download(sysFileInfoResponse.getFileOriginName(), sysFileInfoResponse.getFileBytes(), response);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteReally(SysFileInfoRequest sysFileInfoRequest) {

        // 查询该Code的所有历史版本
        LambdaQueryWrapper<SysFileInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysFileInfo::getFileCode, sysFileInfoRequest.getFileCode());
        List<SysFileInfo> fileInfos = this.list(lqw);

        // 批量删除
        this.removeByIds(fileInfos.stream().map(SysFileInfo::getFileId).collect(Collectors.toList()));

        // 删除具体文件
        for (SysFileInfo fileInfo : fileInfos) {
            this.fileOperatorApi.deleteFile(fileInfo.getFileBucket(), fileInfo.getFileObjectName());
        }
    }

    @Override
    public PageResult<SysFileInfoListResponse> fileInfoListPage(SysFileInfoRequest sysFileInfoRequest) {
        Page<SysFileInfoListResponse> page = PageFactory.defaultPage();
        List<SysFileInfoListResponse> list = this.baseMapper.fileInfoList(page, sysFileInfoRequest);

        // 排除defaultAvatar.png这个图片,这个是默认头像
        List<SysFileInfoListResponse> newList = list.stream().filter(i -> !i.getFileOriginName().equals("defaultAvatar.png")).collect(Collectors.toList());

        return PageResultFactory.createPageResult(page.setRecords(newList));
    }

    @Override
    public void packagingDownload(String fileIds, String secretFlag, HttpServletResponse response) {

        // 获取文件信息
        List<Long> fileIdList = Arrays.stream(fileIds.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<SysFileInfoResponse> fileInfoResponseList = this.getFileInfoListByFileIds(fileIdList);

        // 输出流等信息
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(bos);

        try {
            for (int i = 0; i < fileInfoResponseList.size(); i++) {
                SysFileInfoResponse sysFileInfoResponse = fileInfoResponseList.get(i);
                if (ObjectUtil.isNotEmpty(sysFileInfoResponse)) {
                    String fileOriginName = sysFileInfoResponse.getFileOriginName();
                    // 判断公有文件下载时是否包含私有文件
                    if (secretFlag.equals(YesOrNotEnum.N.getCode()) && !secretFlag.equals(sysFileInfoResponse.getSecretFlag())) {
                        throw new FileException(FileExceptionEnum.SECRET_FLAG_INFO_ERROR, fileOriginName);
                    }

                    byte[] fileBytes = fileOperatorApi.getFileBytes(DEFAULT_BUCKET_NAME, sysFileInfoResponse.getFileObjectName());
                    ZipEntry entry = new ZipEntry(i + 1 + "." + fileOriginName);
                    entry.setSize(fileBytes.length);
                    zip.putNextEntry(entry);
                    zip.write(fileBytes);
                }
            }
            zip.finish();

            // 下载文件
            DownloadUtil.download(DateUtil.now() + "-打包下载" + FILE_POSTFIX_SEPARATOR + "zip", bos.toByteArray(), response);
        } catch (Exception e) {
            log.error("获取文件流异常，具体信息为：{}", e.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR);
        } finally {
            try {
                zip.closeEntry();
                zip.close();
                bos.close();
            } catch (IOException e) {
                log.error("关闭数据流失败，具体信息为：{}", e.getMessage());
            }
        }
    }

    @Override
    public List<SysFileInfoResponse> getFileInfoListByFileIds(String fileIds) {
        List<Long> fileIdList = Arrays.stream(fileIds.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        return this.getFileInfoListByFileIds(fileIdList);
    }

    @Override
    public void preview(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

        // 如果是默认头像
        if (FileConstants.DEFAULT_AVATAR_FILE_ID.equals(sysFileInfoRequest.getFileId())) {
            DownloadUtil.renderPreviewFile(response, Base64.decode(FileConfigExpander.getDefaultAvatarBase64()));
            return;
        }

        // 根据文件id获取文件信息结果集
        SysFileInfoResponse sysFileInfoResponse = this.getFileInfoResult(sysFileInfoRequest.getFileId());

        // 如果文件加密等级不符合，文件不允许被访问
        if (!sysFileInfoRequest.getSecretFlag().equals(sysFileInfoResponse.getSecretFlag())) {
            throw new FileException(FileExceptionEnum.FILE_DENIED_ACCESS);
        }

        // 获取文件后缀
        String fileSuffix = sysFileInfoResponse.getFileSuffix().toLowerCase();

        // 获取文件字节码
        byte[] fileBytes = sysFileInfoResponse.getFileBytes();

        // 附件预览
        this.renderPreviewFile(response, fileSuffix, fileBytes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileInfoResponse versionBack(SysFileInfoRequest sysFileInfoRequest) {

        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileInfo::getFileId, sysFileInfoRequest.getFileId());
        SysFileInfo fileInfo = this.getOne(queryWrapper);

        // 判断有没有这个文件
        if (ObjectUtil.isEmpty(fileInfo)) {
            String userTip = FileExceptionEnum.FILE_NOT_FOUND.getUserTip();
            String errorMessage = StrUtil.format(userTip, "文件:" + fileInfo.getFileId() + "未找到！");
            throw new FileException(FILE_NOT_FOUND, errorMessage);
        }

        // 把之前的文件刷回
        LambdaUpdateWrapper<SysFileInfo> oldFileInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        oldFileInfoLambdaUpdateWrapper.eq(SysFileInfo::getFileCode, fileInfo.getFileCode());
        oldFileInfoLambdaUpdateWrapper.eq(SysFileInfo::getFileStatus, FileStatusEnum.NEW.getCode());
        oldFileInfoLambdaUpdateWrapper.set(SysFileInfo::getFileStatus, FileStatusEnum.OLD.getCode());
        this.update(oldFileInfoLambdaUpdateWrapper);

        // 修改文件状态
        LambdaUpdateWrapper<SysFileInfo> newFileInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        newFileInfoLambdaUpdateWrapper.eq(SysFileInfo::getFileId, sysFileInfoRequest.getFileId());
        newFileInfoLambdaUpdateWrapper.set(SysFileInfo::getFileStatus, FileStatusEnum.NEW.getCode());
        newFileInfoLambdaUpdateWrapper.set(SysFileInfo::getDelFlag, YesOrNotEnum.N.getCode());
        this.update(newFileInfoLambdaUpdateWrapper);

        // 返回
        return BeanUtil.toBean(fileInfo, SysFileInfoResponse.class);
    }

    @Override
    public void previewByBucketAndObjName(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

        // 获取文件字节码
        byte[] fileBytes;
        try {
            fileBytes = fileOperatorApi.getFileBytes(sysFileInfoRequest.getFileBucket(), sysFileInfoRequest.getFileObjectName());
        } catch (Exception e) {
            log.error("获取文件流异常，具体信息为：{}", e.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR);
        }

        // 获取文件后缀
        String fileSuffix = FileUtil.getSuffix(sysFileInfoRequest.getFileObjectName());

        // 附件预览
        this.renderPreviewFile(response, fileSuffix, fileBytes);
    }

    @Override
    public SysFileInfo detail(SysFileInfoRequest sysFileInfoRequest) {
        return this.querySysFileInfo(sysFileInfoRequest);
    }

    @Override
    public List<SysFileInfoResponse> getFileInfoListByFileIds(List<Long> fileIdList) {
        LambdaQueryWrapper<SysFileInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysFileInfo::getFileId, fileIdList);
        List<SysFileInfo> list = this.list(wrapper);

        // bean转化
        return list.stream().map(i -> {
            SysFileInfoResponse sysFileInfoResponse = new SysFileInfoResponse();
            BeanUtil.copyProperties(i, sysFileInfoResponse);
            return sysFileInfoResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public SysFileInfoResponse getFileInfoWithoutContent(Long fileId) {

        SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
        sysFileInfoRequest.setFileId(fileId);

        // 获取文件的基本信息
        SysFileInfo sysFileInfo = querySysFileInfo(sysFileInfoRequest);

        // 转化实体
        SysFileInfoResponse sysFileInfoResponse = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, sysFileInfoResponse);

        return sysFileInfoResponse;
    }

    @Override
    public String getFileAuthUrl(Long fileId) {

        // 获取登录用户的token
        String token = LoginContext.me().getToken();

        // 获取context-path
        String contextPath = HttpServletUtil.getRequest().getContextPath();

        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PRIVATE_PREVIEW_URL + "?fileId=" + fileId + "&token=" + token;
    }

    @Override
    public String getFileAuthUrl(Long fileId, String token) {

        // 获取context-path
        String contextPath = HttpServletUtil.getRequest().getContextPath();

        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PRIVATE_PREVIEW_URL + "?fileId=" + fileId + "&token=" + token;
    }

    /**
     * 渲染被预览的文件到servlet的response流中
     *
     * @author 林选伟
     * @date 2020/11/29 17:13
     */
    private void renderPreviewFile(HttpServletResponse response, String fileSuffix, byte[] fileBytes) {

        // 如果文件后缀是图片或者pdf，则直接输出流
        if (PicFileTypeUtil.getFileImgTypeFlag(fileSuffix) || PdfFileTypeUtil.getFilePdfTypeFlag(fileSuffix)) {
            try {
                // 设置contentType
                if (PicFileTypeUtil.getFileImgTypeFlag(fileSuffix)) {
                    response.setContentType(MediaType.IMAGE_PNG_VALUE);
                } else if (PdfFileTypeUtil.getFilePdfTypeFlag(fileSuffix)) {
                    response.setContentType(MediaType.APPLICATION_PDF_VALUE);
                }

                // 获取outputStream
                ServletOutputStream outputStream = response.getOutputStream();

                // 输出字节流
                IoUtil.write(outputStream, true, fileBytes);
            } catch (IOException e) {
                throw new FileException(FileExceptionEnum.WRITE_BYTES_ERROR, e.getMessage());
            }
        } else {
            // 不支持别的文件预览
            throw new FileException(FileExceptionEnum.PREVIEW_ERROR_NOT_SUPPORT);
        }
    }

    /**
     * 获取文件信息表
     *
     * @author 林选伟
     * @date 2020/11/29 13:40
     */
    private SysFileInfo querySysFileInfo(SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfo sysFileInfo = this.getById(sysFileInfoRequest.getFileId());
        if (ObjectUtil.isEmpty(sysFileInfo) || sysFileInfo.getDelFlag().equals(YesOrNotEnum.Y.getCode())) {
            throw new FileException(FileExceptionEnum.NOT_EXISTED, sysFileInfoRequest.getFileId());
        }
        return sysFileInfo;
    }

}
