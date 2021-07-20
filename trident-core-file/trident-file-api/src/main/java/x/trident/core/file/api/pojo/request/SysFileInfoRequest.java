
package x.trident.core.file.api.pojo.request;

import x.trident.core.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文件信息表
 * 注意：
 * 1、如果文件上传不关心版本号只关心文件本身，则业务关联fileCode,文件升级code不变
 * 2、如果业务场景类似合同这类，文件升级不影响已签发的文件，则业务关联fileId，每次版本升级都会生成新的fileId
 *
 * @author majianguo
 * @date 2020/12/27 12:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFileInfoRequest extends BaseRequest {

    /**
     * 文件ID
     */
    @NotNull(message = "fileId不能为空", groups = {versionBack.class, detail.class})
    private Long fileId;

    /**
     * 文件编码
     * <p>
     * 解决一个文件多个版本问题，多次上传文件编码不变
     * <p>
     * 版本号升级的依据，code相同id不同视为同一个文件的不同版本
     */
    @NotNull(message = "fileCode不能为空", groups = {edit.class, delete.class,})
    private Long fileCode;

    /**
     * 是否为机密文件，Y-是机密，N-不是机密
     * <p>
     * 机密文件为需要鉴权的文件，非机密文件则不需要任何权限（不登录也可以访问）
     */
    @NotBlank(message = "是否是机密文件不能为空", groups = {add.class, edit.class})
    private String secretFlag;

    /**
     * 文件名称（上传时候的文件全名，例如：开发文档.txt）
     */
    private String fileOriginName;

    /**
     * 其他文件形式传参
     */
    private String token;

    /**
     * 文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地
     */
    private Integer fileLocation;

    /**
     * 文件仓库（文件夹）
     */
    @NotBlank(message = "fileBucket不能为空", groups = {previewByObjectName.class})
    private String fileBucket;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小kb
     */
    private Long fileSizeKb;

    /**
     * 存储到bucket中的名称，主键id+.后缀
     */
    @NotBlank(message = "fileObjectName不能为空", groups = {previewByObjectName.class})
    private String fileObjectName;

    /**
     * 存储路径
     */
    private String filePath;

    /**
     * 版本回退
     */
    public @interface versionBack {
    }

    /**
     * 通过object名称预览文件
     */
    public @interface previewByObjectName {
    }

}
