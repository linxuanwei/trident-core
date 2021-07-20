
package x.trident.core.system.api.pojo.menu.antd;

import x.trident.core.scanner.api.annotation.ChineseDescription;
import lombok.Data;

import java.util.List;

/**
 * 封装antd vue需要的dto
 *
 * @author 林选伟
 * @date 2021/3/23 21:26
 */
@Data
public class AntdSysMenuDTO {

    /**
     * 菜单的名称
     */
    @ChineseDescription("菜单的名称")
    private String title;

    /**
     * 菜单的图标
     */
    @ChineseDescription("菜单的图标")
    private String icon;

    /**
     * 路由地址(要以/开头)，必填
     */
    @ChineseDescription("路由地址(要以/开头)，必填")
    private String path;

    /**
     * 组件地址(组件要放在view目录下)，父级可以省略
     */
    @ChineseDescription("组件地址(组件要放在view目录下)，父级可以省略")
    private String component;

    /**
     * 为true只注册路由不显示在左侧菜单(比如独立的添加页面)
     */
    @ChineseDescription("为true只注册路由不显示在左侧菜单(比如独立的添加页面)")
    private Boolean hide;

    /**
     * 比如修改页面不在侧边栏，打开后侧边栏就没有选中了，这个可以配置选中地址
     */
    @ChineseDescription("比如修改页面不在侧边栏，打开后侧边栏就没有选中了，这个可以配置选中地址")
    private String uid;

    /**
     * 子级
     */
    @ChineseDescription("子级")
    private List<AntdSysMenuDTO> children;

}
