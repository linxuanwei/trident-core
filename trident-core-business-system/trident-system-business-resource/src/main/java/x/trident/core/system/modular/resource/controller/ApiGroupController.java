package x.trident.core.system.modular.resource.controller;

import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.resource.ApiGroupRequest;
import x.trident.core.system.api.pojo.resource.ApiGroupTreeWrapper;
import x.trident.core.system.modular.resource.entity.ApiGroup;
import x.trident.core.system.modular.resource.service.ApiGroupService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 接口分组控制器
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@RestController
@ApiResource(name = "接口分组")
public class ApiGroupController {

    @Resource
    private ApiGroupService apiGroupService;

    /**
     * 添加
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "添加", path = "/apiGroup/add")
    public ResponseData add(@RequestBody @Validated(ApiGroupRequest.add.class) ApiGroupRequest apiGroupRequest) {
        return new SuccessResponseData(apiGroupService.add(apiGroupRequest));
    }

    /**
     * 删除
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "删除", path = "/apiGroup/delete")
    public ResponseData delete(@RequestBody @Validated(ApiGroupRequest.delete.class) ApiGroupRequest apiGroupRequest) {
        apiGroupService.del(apiGroupRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "编辑", path = "/apiGroup/edit")
    public ResponseData edit(@RequestBody @Validated(ApiGroupRequest.edit.class) ApiGroupRequest apiGroupRequest) {
        apiGroupService.edit(apiGroupRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑树节点排序
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "编辑树节点排序", path = "/apiGroup/editTreeSort")
    public ResponseData editTreeSort(@RequestBody @Validated(ApiGroupRequest.treeSort.class) ApiGroupRequest apiGroupRequest) {
        apiGroupService.editTreeSort(apiGroupRequest.getTreeSortRequestList());
        return new SuccessResponseData();
    }

    /**
     * 查看详情
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "查看详情", path = "/apiGroup/detail", responseClass = ApiGroup.class)
    public ResponseData detail(@Validated(ApiGroupRequest.detail.class) ApiGroupRequest apiGroupRequest) {
        return new SuccessResponseData(apiGroupService.detail(apiGroupRequest));
    }

    /**
     * 获取列表
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "获取列表", path = "/apiGroup/list", responseClass = ApiGroup.class)
    public ResponseData list(ApiGroupRequest apiGroupRequest) {
        return new SuccessResponseData(apiGroupService.findList(apiGroupRequest));
    }

    /**
     * 获取列表（带分页）
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "分页查询", path = "/apiGroup/page", responseClass = ApiGroup.class)
    public ResponseData page(ApiGroupRequest apiGroupRequest) {
        return new SuccessResponseData(apiGroupService.findPage(apiGroupRequest));
    }

    /**
     * 获取树
     *
     * @return {@link ResponseData}
     * @author majianguo
     * @date 2021/5/22 上午11:00
     **/
    @GetResource(name = "获取树", path = "/apiGroup/tree", responseClass = ApiGroupTreeWrapper.class)
    public ResponseData tree(ApiGroupRequest apiGroupRequest) {
        List<ApiGroupTreeWrapper> apiGroupTreeWrapperList = apiGroupService.tree(apiGroupRequest);
        return new SuccessResponseData(apiGroupTreeWrapperList);
    }

    /**
     * 获取分组树
     *
     * @return {@link ResponseData}
     * @author majianguo
     * @date 2021/5/22 上午11:00
     **/
    @GetResource(name = "获取分组树", path = "/apiGroup/groupTree", responseClass = ApiGroupTreeWrapper.class)
    public ResponseData groupTree(ApiGroupRequest apiGroupRequest) {
        List<ApiGroupTreeWrapper> apiGroupTreeWrapperList = apiGroupService.groupTree(apiGroupRequest);
        return new SuccessResponseData(apiGroupTreeWrapperList);
    }

    /**
     * 获取树(平级)
     *
     * @return {@link ResponseData}
     * @author majianguo
     * @date 2021/5/22 上午11:00
     **/
    @GetResource(name = "获取树", path = "/apiGroup/peersTree", responseClass = ApiGroupTreeWrapper.class)
    public ResponseData peersTree(ApiGroupRequest apiGroupRequest) {
        List<ApiGroupTreeWrapper> apiGroupTreeWrapperList = apiGroupService.peersTree(apiGroupRequest);
        return new SuccessResponseData(apiGroupTreeWrapperList);
    }
}