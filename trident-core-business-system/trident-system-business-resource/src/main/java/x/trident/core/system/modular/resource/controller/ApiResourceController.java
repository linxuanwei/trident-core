package x.trident.core.system.modular.resource.controller;

import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.resource.ApiResourceRequest;
import x.trident.core.system.modular.resource.entity.ApiResourceField;
import x.trident.core.system.modular.resource.service.ApiResourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 接口信息控制器
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@RestController
@ApiResource(name = "接口信息")
public class ApiResourceController {

    @Resource
    private ApiResourceService apiResourceService;

    /**
     * 添加
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "添加", path = "/apiResource/add")
    public ResponseData add(@RequestBody @Validated(ApiResourceRequest.add.class) ApiResourceRequest apiResourceRequest) {
        apiResourceService.add(apiResourceRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "删除", path = "/apiResource/delete")
    public ResponseData delete(@RequestBody @Validated(ApiResourceRequest.delete.class) ApiResourceRequest apiResourceRequest) {
        apiResourceService.del(apiResourceRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "编辑", path = "/apiResource/edit")
    public ResponseData edit(@RequestBody @Validated(ApiResourceRequest.edit.class) ApiResourceRequest apiResourceRequest) {
        apiResourceService.edit(apiResourceRequest);
        return new SuccessResponseData();
    }

    /**
     * 重置
     *
     * @return {@link x.trident.core.pojo.response.ResponseData}
     * @author majianguo
     * @date 2021/5/27 下午3:33
     **/
    @PostResource(name = "编辑", path = "/apiResource/reset", responseClass = x.trident.core.system.modular.resource.entity.ApiResource.class)
    public ResponseData reset(@RequestBody @Validated(ApiResourceRequest.reset.class) ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.reset(apiResourceRequest));
    }

    /**
     * 请求记录
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "请求记录", path = "/apiResource/record", responseClass = x.trident.core.system.modular.resource.entity.ApiResource.class)
    public ResponseData record(@RequestBody @Validated(ApiResourceRequest.record.class) ApiResourceRequest apiResourceRequest) {
        x.trident.core.system.modular.resource.entity.ApiResource apiResource = apiResourceService.record(apiResourceRequest);
        return new SuccessResponseData(apiResource);
    }

    /**
     * 查看详情
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "查看详情", path = "/apiResource/detail", responseClass = x.trident.core.system.modular.resource.entity.ApiResource.class)
    public ResponseData detail(@Validated(ApiResourceRequest.detail.class) ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.detail(apiResourceRequest));
    }

    /**
     * 获取列表
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "获取列表", path = "/apiResource/list", responseClass = x.trident.core.system.modular.resource.entity.ApiResource.class)
    public ResponseData list(ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.findList(apiResourceRequest));
    }

    /**
     * 获取列表（带分页）
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "分页查询", path = "/apiResource/page", responseClass = x.trident.core.system.modular.resource.entity.ApiResource.class)
    public ResponseData page(ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.findPage(apiResourceRequest));
    }

    /**
     * 查询该资源所有字段
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "查询该资源所有字段", path = "/apiResource/allField", responseClass = ApiResourceField.class)
    public ResponseData allField(@Validated(ApiResourceRequest.allField.class) ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.allField(apiResourceRequest));
    }

}