
package x.trident.core.system.api;

import x.trident.core.system.api.pojo.organization.HrOrganizationDTO;

import java.util.List;

/**
 * 组织机构api
 *
 * @author 林选伟
 * @date 2021/1/15 10:40
 */
public interface OrganizationServiceApi {

    /**
     * 查询系统组织机构
     *
     * @return 组织机构列表
     * @author 林选伟
     * @date 2021/1/15 10:41
     */
    List<HrOrganizationDTO> orgList();

}
