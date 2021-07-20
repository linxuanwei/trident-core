
package x.trident.core.system.modular.organization.entity;

import x.trident.core.db.api.pojo.entity.BaseEntity;
import x.trident.core.tree.xmtree.base.AbstractXmSelectNode;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 系统职位表
 *
 * @author 林选伟
 * @date 2020/11/04 11:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hr_position")
public class HrPosition extends BaseEntity implements AbstractXmSelectNode {

    /**
     * 主键
     */
    @TableId("position_id")
    @ChineseDescription("主键")
    private Long positionId;

    /**
     * 职位名称
     */
    @TableField("position_name")
    @ChineseDescription("职位名称")
    private String positionName;

    /**
     * 职位编码
     */
    @TableField("position_code")
    @ChineseDescription("职位编码")
    private String positionCode;

    /**
     * 排序
     */
    @TableField("position_sort")
    @ChineseDescription("排序")
    private BigDecimal positionSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 职位备注
     */
    @TableField("position_remark")
    @ChineseDescription("职位备注")
    private String positionRemark;

    /**
     * 删除标记：Y-已删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("删除标记：Y-已删除，N-未删除")
    private String delFlag;

    @Override
    public String getName() {
        return this.positionName;
    }

    @Override
    public String getValue() {
        return String.valueOf(positionId);
    }

    @Override
    public Boolean getSelected() {
        return false;
    }

    @Override
    public Boolean getDisabled() {
        return false;
    }

    @Override
    public List<?> getChildren() {
        return null;
    }
}
