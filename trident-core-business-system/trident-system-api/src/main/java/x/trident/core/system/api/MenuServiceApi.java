
package x.trident.core.system.api;

import java.util.List;

/**
 * 菜单api
 *
 * @author 林选伟
 * @date 2020/11/24 21:37
 */
public interface MenuServiceApi {

    /**
     * 根据应用编码判断该机构下是否有状态为正常的菜单
     *
     * @param appCode 应用编码
     * @return 该应用下是否有正常菜单，true是，false否
     * @author 林选伟
     * @date 2020/11/24 21:37
     */
    boolean hasMenu(String appCode);

    /**
     * 获取当前用户所拥有菜单对应的appCode列表
     *
     * @author 林选伟
     * @date 2021/4/21 15:40
     */
    List<String> getUserAppCodeList();

}
