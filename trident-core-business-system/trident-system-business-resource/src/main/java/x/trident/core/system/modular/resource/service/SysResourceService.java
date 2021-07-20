
package x.trident.core.system.modular.resource.service;

import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import x.trident.core.system.api.pojo.resource.LayuiApiResourceTreeNode;
import x.trident.core.system.api.pojo.resource.ResourceRequest;
import x.trident.core.system.modular.resource.entity.SysResource;
import x.trident.core.system.modular.resource.pojo.ResourceTreeNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 资源服务类
 *
 * @author 林选伟
 * @date 2020/11/24 19:56
 */
public interface SysResourceService extends IService<SysResource> {

    /**
     * 获取资源分页列表
     *
     * @param resourceRequest 请求参数
     * @return 返回结果
     * @author 林选伟
     * @date 2020/11/24 20:45
     */
    PageResult<SysResource> findPage(ResourceRequest resourceRequest);

    /**
     * 通过应用code获取获取资源下拉列表
     * <p>
     * 只获取菜单资源
     *
     * @param resourceRequest 请求参数
     * @return 响应下拉结果
     * @author 林选伟
     * @date 2020/11/24 20:45
     */
    List<SysResource> findList(ResourceRequest resourceRequest);

    /**
     * 获取资源平级树列表，用于分配接口权限
     *
     * @param roleId        角色id
     * @param treeBuildFlag true-带树形结构，false-不组装树形结构的
     * @author majianguo
     * @date 2021/1/9 15:08
     */
    List<ResourceTreeNode> getResourceTree(Long roleId, Boolean treeBuildFlag);

    /**
     * 获取资源树列表，用于生成api接口
     *
     * @return 资源树列表
     * @author 林选伟
     * @date 2020/12/18 15:06
     */
    List<LayuiApiResourceTreeNode> getApiResourceTree();

    /**
     * 获取资源的详情
     *
     * @param resourceRequest 请求参数
     * @return 资源详情
     * @author 林选伟
     * @date 2020/12/18 16:04
     */
    ResourceDefinition getApiResourceDetail(ResourceRequest resourceRequest);

    /**
     * 删除某个项目的所有资源
     *
     * @param projectCode 项目编码，一般为spring application name
     * @author 林选伟
     * @date 2020/11/24 20:46
     */
    void deleteResourceByProjectCode(String projectCode);

}
