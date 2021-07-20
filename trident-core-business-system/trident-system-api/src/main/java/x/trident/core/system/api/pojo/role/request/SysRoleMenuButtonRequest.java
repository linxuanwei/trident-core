
package x.trident.core.system.api.pojo.role.request;

import lombok.Data;

/**
 * 角色按钮
 *
 * @author majianguo
 * @date 2021/1/9 17:33
 */
@Data
public class SysRoleMenuButtonRequest {

    /**
     * 按钮id
     */
    private Long buttonId;

    /**
     * 按钮编码
     */
    private String buttonCode;

}
