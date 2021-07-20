
package x.trident.core.system.modular.role.entity;

import x.trident.core.db.api.pojo.entity.BaseEntity;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 系统角色表
 *
 * @author majianguo
 * @date 2020/11/5 下午4:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键")
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    @ChineseDescription("角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField("role_code")
    @ChineseDescription("角色编码")
    private String roleCode;

    /**
     * 排序
     */
    @TableField("role_sort")
    @ChineseDescription("排序")
    private BigDecimal roleSort;

    /**
     * 数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据
     */
    @TableField("data_scope_type")
    @ChineseDescription("数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据")
    private Integer dataScopeType;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField(value = "remark", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    @ChineseDescription("备注")
    private String remark;

    /**
     * 删除标记（Y-已删除，N-未删除）
     */
    @TableField(value = "del_flag",fill = FieldFill.INSERT)
    @ChineseDescription("删除标记（Y-已删除，N-未删除）")
    private String delFlag;

    /**
     * 是否是系统角色：Y-是，N-否
     */
    @TableField("role_system_flag")
    @ChineseDescription("是否是系统角色：Y-是，N-否")
    private String roleSystemFlag;

    /**
     * 角色类型
     */
    @TableField("role_type_code")
    @ChineseDescription("角色类型")
    private String roleTypeCode;

}
