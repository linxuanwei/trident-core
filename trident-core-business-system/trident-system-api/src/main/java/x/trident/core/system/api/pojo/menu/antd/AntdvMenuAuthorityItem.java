
package x.trident.core.system.api.pojo.menu.antd;

import lombok.Data;

import java.util.List;

/**
 * 菜单被哪个权限绑定的详情
 *
 * @author 林选伟
 * @date 2021/1/7 18:16
 */
@Data
public class AntdvMenuAuthorityItem {

    /**
     * 权限编码
     */
    private List<String> permission;

    /**
     * 角色编码
     */
    private List<String> role;

}
