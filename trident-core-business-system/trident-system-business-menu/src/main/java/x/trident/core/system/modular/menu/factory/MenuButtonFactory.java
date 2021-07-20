
package x.trident.core.system.modular.menu.factory;

import cn.hutool.core.collection.CollUtil;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.system.modular.menu.constants.MenuButtonConstant;
import x.trident.core.system.modular.menu.entity.SysMenuButton;

import java.util.List;

/**
 * 组装菜单按钮
 *
 * @author chenjinlong
 * @date 2021/1/27 16:22
 */
public class MenuButtonFactory {

    /**
     * 生成系统默认菜单按钮
     *
     * @param menuId 菜单id
     * @return 系统默认菜单按钮集合
     * @author chenjinlong
     * @date 2021/1/27 15:36
     */
    public static List<SysMenuButton> createSystemDefaultButton(Long menuId) {

        List<SysMenuButton> sysMenuButtonList = CollUtil.newArrayList();

        // 新增按钮
        SysMenuButton addButton = new SysMenuButton();
        addButton.setMenuId(menuId);
        addButton.setButtonCode(MenuButtonConstant.DEFAULT_ADD_BUTTON_CODE);
        addButton.setButtonName(MenuButtonConstant.DEFAULT_ADD_BUTTON_NAME);
        addButton.setDelFlag(YesOrNotEnum.N.getCode());
        sysMenuButtonList.add(addButton);

        // 删除按钮
        SysMenuButton delButton = new SysMenuButton();
        delButton.setMenuId(menuId);
        delButton.setButtonCode(MenuButtonConstant.DEFAULT_DEL_BUTTON_CODE);
        delButton.setButtonName(MenuButtonConstant.DEFAULT_DEL_BUTTON_NAME);
        delButton.setDelFlag(YesOrNotEnum.N.getCode());
        sysMenuButtonList.add(delButton);

        // 修改按钮
        SysMenuButton updateButton = new SysMenuButton();
        updateButton.setMenuId(menuId);
        updateButton.setButtonCode(MenuButtonConstant.DEFAULT_UPDATE_BUTTON_CODE);
        updateButton.setButtonName(MenuButtonConstant.DEFAULT_UPDATE_BUTTON_NAME);
        updateButton.setDelFlag(YesOrNotEnum.N.getCode());
        sysMenuButtonList.add(updateButton);


        // 查询按钮
        SysMenuButton searchButton = new SysMenuButton();
        searchButton.setMenuId(menuId);
        searchButton.setButtonCode(MenuButtonConstant.DEFAULT_SEARCH_BUTTON_CODE);
        searchButton.setButtonName(MenuButtonConstant.DEFAULT_SEARCH_BUTTON_NAME);
        searchButton.setDelFlag(YesOrNotEnum.N.getCode());
        sysMenuButtonList.add(searchButton);

        return sysMenuButtonList;
    }

}
