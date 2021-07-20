
package x.trident.core.system.modular.app.entity;

import x.trident.core.db.api.pojo.entity.BaseEntity;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统应用表
 *
 * @author 林选伟
 * @date 2020/11/24 21:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_app")
public class SysApp extends BaseEntity {

    /**
     * 主键id
     */
    @TableId("app_id")
    @ChineseDescription("主键id")
    private Long appId;

    /**
     * 应用名称
     */
    @TableField("app_name")
    @ChineseDescription("应用名称")
    private String appName;

    /**
     * 编码
     */
    @TableField("app_code")
    @ChineseDescription("编码")
    private String appCode;

    /**
     * 是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开
     */
    @TableField("active_flag")
    @ChineseDescription("是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开")
    private String activeFlag;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 是否删除：Y-已删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("是否删除：Y-已删除，N-未删除")
    private String delFlag;

}
