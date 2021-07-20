
package x.trident.core.system.api;

import x.trident.core.system.api.pojo.user.SysUserOrgDTO;

/**
 * 用户组织机构服务api
 *
 * @author 林选伟
 * @date 2020/12/19 22:21
 */
public interface UserOrgServiceApi {

    /**
     * 获取组织机构或职务下是否有人绑定
     *
     * @return true-有人绑定，false-无人
     * @author 林选伟
     * @date 2020/12/19 22:42
     */
    Boolean getUserOrgFlag(Long orgId, Long positionId);

    /**
     * 获取用户的组织机构信息
     *
     * @author 林选伟
     * @date 2020/12/19 22:33
     */
    SysUserOrgDTO getUserOrgByUserId(Long userId);

}
