
package x.trident.core.file.modular.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.file.api.FileOperatorApi;
import x.trident.core.file.api.enums.FileLocationEnum;
import x.trident.core.file.api.enums.FileStatusEnum;
import x.trident.core.file.api.exception.FileException;
import x.trident.core.file.api.exception.enums.FileExceptionEnum;
import x.trident.core.file.api.pojo.request.SysFileInfoRequest;
import x.trident.core.file.modular.entity.SysFileInfo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import static x.trident.core.file.api.constants.FileConstants.DEFAULT_BUCKET_NAME;
import static x.trident.core.file.api.constants.FileConstants.FILE_POSTFIX_SEPARATOR;

/**
 * 文件信息组装工厂
 *
 * @author 林选伟
 * @date 2020/12/30 22:16
 */
public class FileInfoFactory {

    /**
     * 封装附件信息
     *
     * @author majianguo
     * @date 2020/12/27 12:55
     */
    public static SysFileInfo createSysFileInfo(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

        FileOperatorApi fileOperatorApi = SpringUtil.getBean(FileOperatorApi.class);

        // 生成文件的唯一id
        Long fileId = IdWorker.getId();

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();

        // 获取文件后缀（不包含点）
        String fileSuffix = null;
        if (ObjectUtil.isNotEmpty(originalFilename)) {
            fileSuffix = StrUtil.subAfter(originalFilename, FILE_POSTFIX_SEPARATOR, true);
        }

        // 生成文件的最终名称
        String finalFileName = fileId + FILE_POSTFIX_SEPARATOR + fileSuffix;

        // 桶名
        String fileBucket = DEFAULT_BUCKET_NAME;

        // 存储文件
        byte[] bytes;
        try {
            bytes = file.getBytes();
            if (StrUtil.isNotEmpty(sysFileInfoRequest.getFileBucket())) {
                fileBucket = sysFileInfoRequest.getFileBucket();
            }
            fileOperatorApi.storageFile(fileBucket, finalFileName, bytes);
        } catch (IOException e) {
            throw new FileException(FileExceptionEnum.ERROR_FILE, e.getMessage());
        }

        // 计算文件大小kb
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024)).setScale(0, BigDecimal.ROUND_HALF_UP));

        // 计算文件大小信息
        String fileSizeInfo = FileUtil.readableFileSize(file.getSize());

        // 封装存储文件信息（上传替换公共信息）
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileLocation(FileLocationEnum.LOCAL.getCode());
        sysFileInfo.setFileBucket(fileBucket);
        sysFileInfo.setFileObjectName(finalFileName);
        sysFileInfo.setFileOriginName(originalFilename);
        sysFileInfo.setFileSuffix(fileSuffix);
        sysFileInfo.setFileSizeKb(fileSizeKb);
        sysFileInfo.setFileSizeInfo(fileSizeInfo);
        sysFileInfo.setFileStatus(FileStatusEnum.NEW.getCode());
        sysFileInfo.setSecretFlag(sysFileInfoRequest.getSecretFlag());

        // 返回结果
        return sysFileInfo;
    }

}
