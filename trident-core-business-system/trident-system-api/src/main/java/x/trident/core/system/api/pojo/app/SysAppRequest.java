
package x.trident.core.system.api.pojo.app;

import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import x.trident.core.validator.api.validators.status.StatusValue;
import x.trident.core.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统应用参数
 *
 * @author 林选伟
 * @date 2020/3/24 20:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysAppRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "appId不能为空", groups = {edit.class, delete.class, detail.class, updateActiveFlag.class, updateStatus.class})
    @ChineseDescription("主键")
    private Long appId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "名称存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_app",
            columnName = "app_name",
            idFieldName = "app_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("名称不能为空")
    private String appName;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "编码存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_app",
            columnName = "app_code",
            idFieldName = "app_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("编码不能为空")
    private String appCode;

    /**
     * 是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开
     */
    @ChineseDescription("是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开")
    private String activeFlag;

    /**
     * 状态：1-启用，2-禁用
     */
    @NotNull(message = "状态为空", groups = {updateStatus.class})
    @StatusValue(groups = updateStatus.class)
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 设置为默认状态
     */
    public @interface updateActiveFlag {
    }

}
