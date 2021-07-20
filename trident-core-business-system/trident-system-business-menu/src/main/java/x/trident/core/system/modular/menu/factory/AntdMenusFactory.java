
package x.trident.core.system.modular.menu.factory;

import cn.hutool.core.util.ObjectUtil;
import x.trident.core.constants.TreeConstants;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.tree.factory.DefaultTreeBuildFactory;
import x.trident.core.system.api.pojo.menu.antd.AntdMenuSelectTreeNode;
import x.trident.core.system.api.pojo.menu.antd.AntdSysMenuDTO;
import x.trident.core.system.modular.menu.entity.SysMenu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对于antd vue版本的前端菜单的组装
 *
 * @author 林选伟
 * @date 2020/12/30 20:11
 */
public class AntdMenusFactory {

    /**
     * 组装antdv用的获取所有菜单列表详情
     *
     * @author 林选伟
     * @date 2021/1/7 18:17
     */
    public static List<AntdSysMenuDTO> createTotalMenus(List<SysMenu> sysMenuList) {

        // 构造菜单树
        List<SysMenu> treeStructMenu = new DefaultTreeBuildFactory<SysMenu>(TreeConstants.DEFAULT_PARENT_ID.toString()).doTreeBuild(sysMenuList);

        // 模型转化
        return doModelTransfer(treeStructMenu);
    }

    /**
     * menu实体转化为菜单树节点
     *
     * @author 林选伟
     * @date 2020/11/23 21:54
     */
    public static AntdMenuSelectTreeNode parseMenuBaseTreeNode(SysMenu sysMenu) {
        AntdMenuSelectTreeNode menuTreeNode = new AntdMenuSelectTreeNode();
        menuTreeNode.setId(sysMenu.getMenuId());
        menuTreeNode.setParentId(sysMenu.getMenuParentId());
        menuTreeNode.setValue(String.valueOf(sysMenu.getMenuId()));
        menuTreeNode.setTitle(sysMenu.getMenuName());
        menuTreeNode.setWeight(sysMenu.getMenuSort());
        return menuTreeNode;
    }

    /**
     * 添加根节点
     *
     * @author 林选伟
     * @date 2021/4/16 15:52
     */
    public static AntdMenuSelectTreeNode createRootNode() {
        AntdMenuSelectTreeNode antdMenuSelectTreeNode = new AntdMenuSelectTreeNode();
        antdMenuSelectTreeNode.setId(-1L);
        antdMenuSelectTreeNode.setParentId(-2L);
        antdMenuSelectTreeNode.setTitle("根节点");
        antdMenuSelectTreeNode.setValue(String.valueOf(antdMenuSelectTreeNode.getId()));
        antdMenuSelectTreeNode.setWeight(new BigDecimal(-1));
        return antdMenuSelectTreeNode;
    }

    /**
     * 模型转化
     *
     * @author 林选伟
     * @date 2021/3/23 21:40
     */
    private static List<AntdSysMenuDTO> doModelTransfer(List<SysMenu> sysMenuList) {
        if (ObjectUtil.isEmpty(sysMenuList)) {
            return null;
        } else {
            ArrayList<AntdSysMenuDTO> resultMenus = new ArrayList<>();

            for (SysMenu sysMenu : sysMenuList) {
                AntdSysMenuDTO antdvMenuItem = new AntdSysMenuDTO();
                antdvMenuItem.setTitle(sysMenu.getMenuName());
                antdvMenuItem.setIcon(sysMenu.getAntdvIcon());
                antdvMenuItem.setPath(sysMenu.getAntdvRouter());
                antdvMenuItem.setComponent(sysMenu.getAntdvComponent());
                antdvMenuItem.setHide(YesOrNotEnum.N.getCode().equals(sysMenu.getAntdvVisible()));
                antdvMenuItem.setUid(sysMenu.getAntdvUidUrl());
                if (ObjectUtil.isNotEmpty(sysMenu.getChildren())) {
                    antdvMenuItem.setChildren(doModelTransfer(sysMenu.getChildren()));
                }
                resultMenus.add(antdvMenuItem);
            }

            return resultMenus;
        }
    }

}
