
package x.trident.core.system.modular.resource.controller;

import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.system.api.pojo.resource.ResourceRequest;
import x.trident.core.system.api.pojo.role.request.SysRoleRequest;
import x.trident.core.system.modular.resource.entity.SysResource;
import x.trident.core.system.modular.resource.pojo.ResourceTreeNode;
import x.trident.core.system.modular.resource.service.SysResourceService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源管理控制器
 *
 * @author 林选伟
 * @date 2020/11/24 19:47
 */
@RestController
@ApiResource(name = "资源管理")
public class ResourceController {

    @Resource
    private SysResourceService sysResourceService;

    /**
     * 获取资源列表
     *
     * @author 林选伟
     * @date 2020/11/24 19:47
     */
    @GetResource(name = "获取资源列表", path = "/resource/pageList",responseClass = SysResource.class)
    public ResponseData pageList(ResourceRequest resourceRequest) {
        PageResult<SysResource> result = this.sysResourceService.findPage(resourceRequest);
        return new SuccessResponseData(result);
    }

    /**
     * 获取资源下拉列表（获取菜单资源）
     *
     * @author 林选伟
     * @date 2020/11/24 19:51
     */
    @GetResource(name = "获取资源下拉列表", path = "/resource/getMenuResourceList",responseClass = SysResource.class)
    public ResponseData getMenuResourceList(ResourceRequest resourceRequest) {
        List<SysResource> menuResourceList = this.sysResourceService.findList(resourceRequest);
        return new SuccessResponseData(menuResourceList);
    }

    /**
     * Layui版本--获取资源树列表，用于角色分配接口权限
     *
     * @author majianguo
     * @date 2021/1/9 15:07
     */
    @GetResource(name = "Layui版本--获取资源树列表，用于角色分配接口权限", path = "/resource/getRoleResourceTree",responseClass = ResourceTreeNode.class)
    public List<ResourceTreeNode> getLateralTree(SysRoleRequest sysRoleRequest) {
        return sysResourceService.getResourceTree(sysRoleRequest.getRoleId(), false);
    }

    /**
     * AntdVue版本--获取资源树列表，用于角色分配接口权限
     *
     * @author majianguo
     * @date 2021/1/9 15:07
     */
    @GetResource(name = "AntdVue版本--获取资源树列表，用于角色分配接口权限", path = "/resource/getRoleResourceTreeAntdv",responseClass = ResourceTreeNode.class)
    public ResponseData getLateralTreeChildren(SysRoleRequest sysRoleRequest) {
        List<ResourceTreeNode> resourceLateralTree = sysResourceService.getResourceTree(sysRoleRequest.getRoleId(), true);
        return new SuccessResponseData(resourceLateralTree);
    }

}
