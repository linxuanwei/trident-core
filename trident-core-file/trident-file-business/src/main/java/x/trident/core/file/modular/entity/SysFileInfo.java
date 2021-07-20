
package x.trident.core.file.modular.entity;

import x.trident.core.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_file_info")
public class SysFileInfo extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    private Long fileId;

    /**
     * 文件编码,本号升级的依据
     * <p>
     * 解决一个文件多个版本问题，多次上传文件编码不变
     */
    @TableField("file_code")
    private Long fileCode;

    /**
     * 文件版本，从1开始
     */
    @TableField("file_version")
    private Integer fileVersion;

    /**
     * 当前状态：0-历史版，1-最新版
     */
    @TableField("file_status")
    private String fileStatus;

    /**
     * 文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地
     */
    @TableField("file_location")
    private Integer fileLocation;

    /**
     * 文件仓库（文件夹）
     */
    @TableField("file_bucket")
    private String fileBucket;

    /**
     * 文件名称（上传时候的文件全名）
     */
    @TableField("file_origin_name")
    private String fileOriginName;

    /**
     * 文件后缀，例如.txt
     */
    @TableField("file_suffix")
    private String fileSuffix;

    /**
     * 文件大小kb为单位
     */
    @TableField("file_size_kb")
    private Long fileSizeKb;

    /**
     * 文件大小信息，计算后的
     */
    @TableField("file_size_info")
    private String fileSizeInfo;

    /**
     * 存储到bucket中的名称，主键id+.后缀
     */
    @TableField("file_object_name")
    private String fileObjectName;

    /**
     * 存储路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 是否为机密文件
     */
    @TableField("secret_flag")
    private String secretFlag;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

}
