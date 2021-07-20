
package x.trident.core.system.modular.resource.controller;

import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import x.trident.core.system.api.pojo.resource.LayuiApiResourceTreeNode;
import x.trident.core.system.api.pojo.resource.ResourceRequest;
import x.trident.core.system.modular.resource.service.SysResourceService;
import org.springframework.validation.annotation.Validated;
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
@ApiResource(name = "API接口管理")
public class ApiController {

    @Resource
    private SysResourceService sysResourceService;

    /**
     * 获取资源树列表，用于接口文档页面
     *
     * @author 林选伟
     * @date 2020/12/18 15:50
     */
    @GetResource(name = "获取接口树列表（用于接口文档页面）", path = "/resource/getTree", requiredLogin = false, responseClass = LayuiApiResourceTreeNode.class)
    public ResponseData getTree() {
        List<LayuiApiResourceTreeNode> resourceTree = sysResourceService.getApiResourceTree();
        return new SuccessResponseData(resourceTree);
    }

    /**
     * 获取接口详情
     *
     * @author 林选伟
     * @date 2020/12/18 15:50
     */
    @GetResource(name = "获取API详情（用于接口文档页面）", path = "/resource/getDetail", requiredLogin = false, responseClass = ResourceDefinition.class)
    public ResponseData getResourceDetail(@Validated(BaseRequest.detail.class) ResourceRequest resourceRequest) {
        ResourceDefinition resourceDetail = sysResourceService.getApiResourceDetail(resourceRequest);
        return new SuccessResponseData(resourceDetail);
    }

}
