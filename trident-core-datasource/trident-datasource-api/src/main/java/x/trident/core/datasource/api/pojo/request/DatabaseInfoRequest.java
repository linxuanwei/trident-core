
package x.trident.core.datasource.api.pojo.request;

import x.trident.core.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import x.trident.core.pojo.request.BaseRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据库信息表
 *
 * @author 林选伟
 * @date 2020/11/1 21:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DatabaseInfoRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(message = "dbId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long dbId;

    /**
     * 数据库名称（英文名称）
     */
    @NotBlank(message = "数据库名称不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "数据库名称存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_database_info",
            columnName = "db_name",
            idFieldName = "db_id",
            excludeLogicDeleteItems = true)
    private String dbName;

    /**
     * jdbc的驱动类型
     */
    @NotBlank(message = "jdbc的驱动类型为空", groups = {add.class, edit.class})
    private String jdbcDriver;

    /**
     * jdbc的url
     */
    @NotBlank(message = "jdbc的url", groups = {add.class, edit.class})
    private String jdbcUrl;

    /**
     * 数据库连接的账号
     */
    @NotBlank(message = "数据库连接的账号", groups = {add.class, edit.class})
    private String username;

    /**
     * 数据库连接密码
     */
    @NotBlank(message = "数据库连接密码", groups = {add.class, edit.class})
    private String password;

    /**
     * 数据库schemaName，注意，每种数据库的schema意义不同
     */
    private String schemaName;

    /**
     * 状态标识：1-正常，2-无法连接
     */
    private Integer statusFlag;

    /**
     * 备注，摘要
     */
    private String remarks;

}
