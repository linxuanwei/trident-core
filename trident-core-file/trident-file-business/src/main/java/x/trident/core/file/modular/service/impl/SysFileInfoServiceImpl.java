
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
 * ??????????????? ???????????????
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

        // ????????????????????????
        SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
        sysFileInfoRequest.setFileId(fileId);
        SysFileInfo sysFileInfo = this.querySysFileInfo(sysFileInfoRequest);

        // ?????????????????????
        byte[] fileBytes;
        try {
            fileBytes = fileOperatorApi.getFileBytes(DEFAULT_BUCKET_NAME, sysFileInfo.getFileObjectName());
        } catch (Exception e) {
            log.error("??????????????????????????????????????????{}", e.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR);
        }

        // ?????????????????????
        SysFileInfoResponse sysFileInfoResult = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, sysFileInfoResult);
        sysFileInfoResult.setFileBytes(fileBytes);

        return sysFileInfoResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileInfoResponse uploadFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

        // ????????????????????????????????????????????????
        SysFileInfo sysFileInfo = FileInfoFactory.createSysFileInfo(file, sysFileInfoRequest);

        // ??????????????????1??????
        sysFileInfo.setFileVersion(1);

        // ??????????????????
        sysFileInfo.setFileCode(IdWorker.getId());

        // ??????????????????????????????
        this.save(sysFileInfo);

        // ?????????????????????
        SysFileInfoResponse fileUploadInfoResult = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, fileUploadInfoResult);
        return fileUploadInfoResult;
    }

    @Override
    public SysFileInfoResponse updateFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

        Long fileCode = sysFileInfoRequest.getFileCode();

        // ????????????????????????????????????
        SysFileInfo sysFileInfo = FileInfoFactory.createSysFileInfo(file, sysFileInfoRequest);
        sysFileInfo.setDelFlag(YesOrNotEnum.Y.getCode());
        sysFileInfo.setFileCode(fileCode);

        // ?????????code?????????????????????????????????
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileInfo::getFileCode, fileCode);
        queryWrapper.eq(SysFileInfo::getDelFlag, YesOrNotEnum.N.getCode());
        queryWrapper.eq(SysFileInfo::getFileStatus, FileStatusEnum.NEW.getCode());
        SysFileInfo fileInfo = this.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(fileInfo)) {
            throw new FileException(FileExceptionEnum.NOT_EXISTED);
        }

        // ??????????????????????????????????????????
        sysFileInfo.setFileVersion(fileInfo.getFileVersion() + 1);

        // ???????????????????????????
        this.save(sysFileInfo);

        // ?????????????????????
        SysFileInfoResponse fileUploadInfoResult = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, fileUploadInfoResult);
        return fileUploadInfoResult;
    }

    @Override
    public void download(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

        // ????????????id???????????????????????????
        SysFileInfoResponse sysFileInfoResponse = this.getFileInfoResult(sysFileInfoRequest.getFileId());

        // ????????????????????????????????????????????????????????????
        if (!sysFileInfoRequest.getSecretFlag().equals(sysFileInfoResponse.getSecretFlag())) {
            throw new FileException(FileExceptionEnum.FILE_DENIED_ACCESS);
        }

        DownloadUtil.download(sysFileInfoResponse.getFileOriginName(), sysFileInfoResponse.getFileBytes(), response);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteReally(SysFileInfoRequest sysFileInfoRequest) {

        // ?????????Code?????????????????????
        LambdaQueryWrapper<SysFileInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysFileInfo::getFileCode, sysFileInfoRequest.getFileCode());
        List<SysFileInfo> fileInfos = this.list(lqw);

        // ????????????
        this.removeByIds(fileInfos.stream().map(SysFileInfo::getFileId).collect(Collectors.toList()));

        // ??????????????????
        for (SysFileInfo fileInfo : fileInfos) {
            this.fileOperatorApi.deleteFile(fileInfo.getFileBucket(), fileInfo.getFileObjectName());
        }
    }

    @Override
    public PageResult<SysFileInfoListResponse> fileInfoListPage(SysFileInfoRequest sysFileInfoRequest) {
        Page<SysFileInfoListResponse> page = PageFactory.defaultPage();
        List<SysFileInfoListResponse> list = this.baseMapper.fileInfoList(page, sysFileInfoRequest);

        // ??????defaultAvatar.png????????????,?????????????????????
        List<SysFileInfoListResponse> newList = list.stream().filter(i -> !i.getFileOriginName().equals("defaultAvatar.png")).collect(Collectors.toList());

        return PageResultFactory.createPageResult(page.setRecords(newList));
    }

    @Override
    public void packagingDownload(String fileIds, String secretFlag, HttpServletResponse response) {

        // ??????????????????
        List<Long> fileIdList = Arrays.stream(fileIds.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<SysFileInfoResponse> fileInfoResponseList = this.getFileInfoListByFileIds(fileIdList);

        // ??????????????????
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(bos);

        try {
            for (int i = 0; i < fileInfoResponseList.size(); i++) {
                SysFileInfoResponse sysFileInfoResponse = fileInfoResponseList.get(i);
                if (ObjectUtil.isNotEmpty(sysFileInfoResponse)) {
                    String fileOriginName = sysFileInfoResponse.getFileOriginName();
                    // ???????????????????????????????????????????????????
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

            // ????????????
            DownloadUtil.download(DateUtil.now() + "-????????????" + FILE_POSTFIX_SEPARATOR + "zip", bos.toByteArray(), response);
        } catch (Exception e) {
            log.error("??????????????????????????????????????????{}", e.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR);
        } finally {
            try {
                zip.closeEntry();
                zip.close();
                bos.close();
            } catch (IOException e) {
                log.error("??????????????????????????????????????????{}", e.getMessage());
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

        // ?????????????????????
        if (FileConstants.DEFAULT_AVATAR_FILE_ID.equals(sysFileInfoRequest.getFileId())) {
            DownloadUtil.renderPreviewFile(response, Base64.decode(FileConfigExpander.getDefaultAvatarBase64()));
            return;
        }

        // ????????????id???????????????????????????
        SysFileInfoResponse sysFileInfoResponse = this.getFileInfoResult(sysFileInfoRequest.getFileId());

        // ????????????????????????????????????????????????????????????
        if (!sysFileInfoRequest.getSecretFlag().equals(sysFileInfoResponse.getSecretFlag())) {
            throw new FileException(FileExceptionEnum.FILE_DENIED_ACCESS);
        }

        // ??????????????????
        String fileSuffix = sysFileInfoResponse.getFileSuffix().toLowerCase();

        // ?????????????????????
        byte[] fileBytes = sysFileInfoResponse.getFileBytes();

        // ????????????
        this.renderPreviewFile(response, fileSuffix, fileBytes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileInfoResponse versionBack(SysFileInfoRequest sysFileInfoRequest) {

        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileInfo::getFileId, sysFileInfoRequest.getFileId());
        SysFileInfo fileInfo = this.getOne(queryWrapper);

        // ???????????????????????????
        if (ObjectUtil.isEmpty(fileInfo)) {
            String userTip = FileExceptionEnum.FILE_NOT_FOUND.getUserTip();
            String errorMessage = StrUtil.format(userTip, "??????:" + fileInfo.getFileId() + "????????????");
            throw new FileException(FILE_NOT_FOUND, errorMessage);
        }

        // ????????????????????????
        LambdaUpdateWrapper<SysFileInfo> oldFileInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        oldFileInfoLambdaUpdateWrapper.eq(SysFileInfo::getFileCode, fileInfo.getFileCode());
        oldFileInfoLambdaUpdateWrapper.eq(SysFileInfo::getFileStatus, FileStatusEnum.NEW.getCode());
        oldFileInfoLambdaUpdateWrapper.set(SysFileInfo::getFileStatus, FileStatusEnum.OLD.getCode());
        this.update(oldFileInfoLambdaUpdateWrapper);

        // ??????????????????
        LambdaUpdateWrapper<SysFileInfo> newFileInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        newFileInfoLambdaUpdateWrapper.eq(SysFileInfo::getFileId, sysFileInfoRequest.getFileId());
        newFileInfoLambdaUpdateWrapper.set(SysFileInfo::getFileStatus, FileStatusEnum.NEW.getCode());
        newFileInfoLambdaUpdateWrapper.set(SysFileInfo::getDelFlag, YesOrNotEnum.N.getCode());
        this.update(newFileInfoLambdaUpdateWrapper);

        // ??????
        return BeanUtil.toBean(fileInfo, SysFileInfoResponse.class);
    }

    @Override
    public void previewByBucketAndObjName(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

        // ?????????????????????
        byte[] fileBytes;
        try {
            fileBytes = fileOperatorApi.getFileBytes(sysFileInfoRequest.getFileBucket(), sysFileInfoRequest.getFileObjectName());
        } catch (Exception e) {
            log.error("??????????????????????????????????????????{}", e.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR);
        }

        // ??????????????????
        String fileSuffix = FileUtil.getSuffix(sysFileInfoRequest.getFileObjectName());

        // ????????????
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

        // bean??????
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

        // ???????????????????????????
        SysFileInfo sysFileInfo = querySysFileInfo(sysFileInfoRequest);

        // ????????????
        SysFileInfoResponse sysFileInfoResponse = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, sysFileInfoResponse);

        return sysFileInfoResponse;
    }

    @Override
    public String getFileAuthUrl(Long fileId) {

        // ?????????????????????token
        String token = LoginContext.me().getToken();

        // ??????context-path
        String contextPath = HttpServletUtil.getRequest().getContextPath();

        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PRIVATE_PREVIEW_URL + "?fileId=" + fileId + "&token=" + token;
    }

    @Override
    public String getFileAuthUrl(Long fileId, String token) {

        // ??????context-path
        String contextPath = HttpServletUtil.getRequest().getContextPath();

        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PRIVATE_PREVIEW_URL + "?fileId=" + fileId + "&token=" + token;
    }

    /**
     * ???????????????????????????servlet???response??????
     *
     * @author ?????????
     * @date 2020/11/29 17:13
     */
    private void renderPreviewFile(HttpServletResponse response, String fileSuffix, byte[] fileBytes) {

        // ?????????????????????????????????pdf?????????????????????
        if (PicFileTypeUtil.getFileImgTypeFlag(fileSuffix) || PdfFileTypeUtil.getFilePdfTypeFlag(fileSuffix)) {
            try {
                // ??????contentType
                if (PicFileTypeUtil.getFileImgTypeFlag(fileSuffix)) {
                    response.setContentType(MediaType.IMAGE_PNG_VALUE);
                } else if (PdfFileTypeUtil.getFilePdfTypeFlag(fileSuffix)) {
                    response.setContentType(MediaType.APPLICATION_PDF_VALUE);
                }

                // ??????outputStream
                ServletOutputStream outputStream = response.getOutputStream();

                // ???????????????
                IoUtil.write(outputStream, true, fileBytes);
            } catch (IOException e) {
                throw new FileException(FileExceptionEnum.WRITE_BYTES_ERROR, e.getMessage());
            }
        } else {
            // ???????????????????????????
            throw new FileException(FileExceptionEnum.PREVIEW_ERROR_NOT_SUPPORT);
        }
    }

    /**
     * ?????????????????????
     *
     * @author ?????????
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
