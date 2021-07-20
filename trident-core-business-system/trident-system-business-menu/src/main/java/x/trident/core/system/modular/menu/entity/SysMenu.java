
package x.trident.core.system.modular.menu.entity;

import x.trident.core.db.api.pojo.entity.BaseEntity;
import x.trident.core.tree.factory.base.AbstractTreeNode;
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
 * 系统菜单
 *
 * @author stylefeng
 * @date 2020/11/22 21:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity implements AbstractTreeNode<SysMenu> {

    /**
     * 主键
     */
    @TableId("menu_id")
    @ChineseDescription("主键")
    private Long menuId;

    /**
     * 父id，顶级节点的父id是-1
     */
    @TableField("menu_parent_id")
    @ChineseDescription("父id，顶级节点的父id是-1")
    private Long menuParentId;

    /**
     * 父id集合，中括号包住，逗号分隔
     */
    @TableField("menu_pids")
    @ChineseDescription("父id集合，中括号包住，逗号分隔")
    private String menuPids;

    /**
     * 菜单的名称
     */
    @TableField("menu_name")
    @ChineseDescription("菜单的名称")
    private String menuName;

    /**
     * 菜单的编码
     */
    @TableField("menu_code")
    @ChineseDescription("菜单的编码")
    private String menuCode;

    /**
     * 应用编码
     */
    @TableField("app_code")
    @ChineseDescription("应用编码")
    private String appCode;

    /**
     * 排序
     */
    @TableField("menu_sort")
    @ChineseDescription("排序")
    private BigDecimal menuSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField("remark")
    @ChineseDescription("备注")
    private String remark;

    /**
     * 菜单的路径，适用于layui-beetl版本
     */
    @TableField("layui_path")
    @ChineseDescription("菜单的路径，适用于layui-beetl版本")
    private String layuiPath;

    /**
     * 菜单的图标，适用于layui-beetl版本
     */
    @TableField("layui_icon")
    @ChineseDescription("菜单的图标，适用于layui-beetl版本")
    private String layuiIcon;

    /**
     * 是否可见(layui版用)：Y-是，N-否
     */
    @TableField("layui_visible")
    @ChineseDescription("是否显示")
    private String layuiVisible;

    /**
     * 路由地址，浏览器显示的URL，例如/menu，适用于antd vue版本
     */
    @TableField("antdv_router")
    @ChineseDescription("路由地址，浏览器显示的URL，例如/menu，适用于antd vue版本")
    private String antdvRouter;

    /**
     * 图标，适用于antd vue版本
     */
    @TableField("antdv_icon")
    @ChineseDescription("图标，适用于antd vue版本")
    private String antdvIcon;

    /**
     * 前端组件名，适用于antd vue版本
     */
    @TableField("antdv_component")
    @ChineseDescription("前端组件名，适用于antd vue版本")
    private String antdvComponent;

    /**
     * 外部链接打开方式：1-内置打开外链，2-新页面外链，适用于antd vue版本
     */
    @TableField("antdv_link_open_type")
    @ChineseDescription("外部链接打开方式：1-内置打开外链，2-新页面外链，适用于antd vue版本")
    private Integer antdvLinkOpenType;

    /**
     * 外部链接地址
     */
    @TableField("antdv_link_url")
    @ChineseDescription("外部链接地址")
    private String antdvLinkUrl;

    /**
     * 用于非菜单显示页面的重定向url设置
     */
    @TableField("antdv_uid_url")
    @ChineseDescription("用于非菜单显示页面的重定向url设置")
    private String antdvUidUrl;

    /**
     * 是否可见(分离版用)：Y-是，N-否
     */
    @TableField("antdv_visible")
    @ChineseDescription("是否可见")
    private String antdvVisible;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("是否删除：Y-被删除，N-未删除")
    private String delFlag;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    @TableField(exist = false)
    @ChineseDescription("子节点（表中不存在，用于构造树）")
    private List<SysMenu> children;

    /**
     * 应用名称
     */
    @TableField(exist = false)
    @ChineseDescription("应用名称")
    private String appName;

    /**
     * 父级菜单的名称
     */
    @TableField(exist = false)
    @ChineseDescription("父级菜单的名称")
    private String menuParentName;

    @Override
    public String getNodeId() {
        return menuId.toString();
    }

    @Override
    public String getNodeParentId() {
        return menuParentId.toString();
    }

    @Override
    public void setChildrenNodes(List<SysMenu> childrenNodes) {
        this.children = childrenNodes;
    }

}
