
package x.trident.core.system.api.pojo.organization;

import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import x.trident.core.validator.api.validators.status.StatusValue;
import x.trident.core.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 系统组织机构表
 *
 * @author 林选伟
 * @date 2020/11/04 11:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HrOrganizationRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {edit.class, delete.class, detail.class, updateStatus.class})
    @ChineseDescription("主键")
    private Long orgId;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空", groups = {add.class, edit.class})
    @ChineseDescription("父id")
    private Long orgParentId;

    /**
     * 父ids
     */
    @ChineseDescription("父ids")
    private String orgPids;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("组织名称")
    private String orgName;

    /**
     * 组织编码
     */
    @NotBlank(message = "组织编码不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "组织编码存在重复",
            groups = {add.class, edit.class},
            tableName = "hr_organization",
            columnName = "org_code",
            idFieldName = "org_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("组织编码")
    private String orgCode;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    @ChineseDescription("排序")
    private BigDecimal orgSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @NotNull(message = "状态不能为空", groups = {updateStatus.class})
    @StatusValue(groups = updateStatus.class)
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 描述
     */
    @ChineseDescription("描述")
    private String orgRemark;

    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空", groups = roleBindOrgScope.class)
    @ChineseDescription("角色id")
    private Long roleId;

    /**
     * 用户id（作为查询条件）
     */
    @NotNull(message = "用户id不能为空", groups = userBindOrgScope.class)
    @ChineseDescription("用户id（作为查询条件）")
    private Long userId;

    /**
     * 组织机构树zTree形式
     */
    public @interface roleBindOrgScope {
    }

    /**
     * 查询用户的数据范围
     */
    public @interface userBindOrgScope {
    }

}
