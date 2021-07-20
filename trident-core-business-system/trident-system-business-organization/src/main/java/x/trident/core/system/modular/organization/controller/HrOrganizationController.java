
package x.trident.core.system.modular.organization.controller;

import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.tree.ztree.ZTreeNode;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.organization.HrOrganizationRequest;
import x.trident.core.system.api.pojo.organization.OrganizationTreeNode;
import x.trident.core.system.modular.organization.entity.HrOrganization;
import x.trident.core.system.modular.organization.service.HrOrganizationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统组织机构控制器
 *
 * @author 林选伟
 * @date 2020/11/18 21:55
 */
@RestController
@ApiResource(name = "系统组织机构管理")
public class HrOrganizationController {

    @Resource
    private HrOrganizationService hrOrganizationService;

    /**
     * 添加系统组织机构
     *
     * @author 林选伟
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "添加系统组织机构", path = "/hrOrganization/add")
    public ResponseData add(@RequestBody @Validated(HrOrganizationRequest.add.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.add(hrOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统组织机构
     *
     * @author 林选伟
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "删除系统组织机构", path = "/hrOrganization/delete")
    public ResponseData delete(@RequestBody @Validated(HrOrganizationRequest.delete.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.del(hrOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统组织机构
     *
     * @author 林选伟
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "编辑系统组织机构", path = "/hrOrganization/edit")
    public ResponseData edit(@RequestBody @Validated(HrOrganizationRequest.edit.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.edit(hrOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改组织机构状态
     *
     * @author 林选伟
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "修改组织机构状态", path = "/hrOrganization/updateStatus")
    public ResponseData updateStatus(@RequestBody @Validated(HrOrganizationRequest.updateStatus.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.updateStatus(hrOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看详情系统组织机构
     *
     * @author 林选伟
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "查看详情系统组织机构", path = "/hrOrganization/detail", responseClass = HrOrganization.class)
    public ResponseData detail(@Validated(HrOrganizationRequest.detail.class) HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.detail(hrOrganizationRequest));
    }

    /**
     * 分页查询系统组织机构
     *
     * @author 林选伟
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "分页查询系统组织机构", path = "/hrOrganization/page", responseClass = HrOrganization.class)
    public ResponseData page(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.findPage(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构
     *
     * @author 林选伟
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "获取全部系统组织机构", path = "/hrOrganization/list", responseClass = HrOrganization.class)
    public ResponseData list(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.findList(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构树（用于新增，编辑组织机构时选择上级节点，用于获取用户管理界面左侧组织机构树）
     *
     * @author chenjinlong
     * @date 2021/01/05 15:55
     */
    @GetResource(name = "获取全部系统组织机构树", path = "/hrOrganization/tree", responseClass = OrganizationTreeNode.class)
    public ResponseData organizationTree(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.organizationTree(hrOrganizationRequest));
    }

    /**
     * 获取组织机构树（用于用户绑定数据范围，可以渲染是否选中信息）
     *
     * @author 林选伟
     * @date 2021/3/19 22:20
     */
    @GetResource(name = "获取组织机构树(用于用户绑定数据范围)", path = "/hrOrganization/userBindOrgScope", responseClass = OrganizationTreeNode.class)
    public ResponseData userBindOrgScope(@Validated(HrOrganizationRequest.userBindOrgScope.class) HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.organizationTree(hrOrganizationRequest));
    }

    /**
     * Layui版本--获取组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）
     *
     * @author 林选伟
     * @date 2021/1/9 18:37
     */
    @GetResource(name = "Layui版本--获取组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）", path = "/hrOrganization/roleBindOrgScope", responseClass = ZTreeNode.class)
    public List<ZTreeNode> roleBindOrgScope(@Validated(HrOrganizationRequest.roleBindOrgScope.class) HrOrganizationRequest hrOrganizationRequest) {
        return hrOrganizationService.orgZTree(hrOrganizationRequest, false);
    }

    /**
     * AntdVue版本--获取组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）
     *
     * @author 林选伟
     * @date 2021/1/9 18:37
     */
    @GetResource(name = "AntdVue版本--获取组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）", path = "/hrOrganization/roleBindOrgScopeAntdv", responseClass = ZTreeNode.class)
    public ResponseData roleBindOrgScopeAntdv(@Validated(HrOrganizationRequest.roleBindOrgScope.class) HrOrganizationRequest hrOrganizationRequest) {
        List<ZTreeNode> zTreeNodes = hrOrganizationService.orgZTree(hrOrganizationRequest, true);
        return new SuccessResponseData(zTreeNodes);
    }

}
