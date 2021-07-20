
package x.trident.core.system.modular.role.controller;

import cn.hutool.core.collection.ListUtil;
import x.trident.core.pojo.dict.SimpleDict;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.role.dto.SysRoleDTO;
import x.trident.core.system.api.pojo.role.request.SysRoleRequest;
import x.trident.core.system.modular.role.entity.SysRole;
import x.trident.core.system.modular.role.service.SysRoleResourceService;
import x.trident.core.system.modular.role.service.SysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统角色控制器
 *
 * @author majianguo
 * @date 2020/11/5 上午10:19
 */
@RestController
@ApiResource(name = "系统角色管理")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleResourceService sysRoleResourceService;

    /**
     * 添加系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:38
     */
    @PostResource(name = "添加系统", path = "/sysRole/add")
    public ResponseData add(@RequestBody @Validated(SysRoleRequest.add.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.add(sysRoleRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:48
     */
    @PostResource(name = "角色删除", path = "/sysRole/delete")
    public ResponseData delete(@RequestBody @Validated(SysRoleRequest.delete.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.del(sysRoleRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:49
     */
    @PostResource(name = "角色编辑", path = "/sysRole/edit")
    public ResponseData edit(@RequestBody @Validated(SysRoleRequest.edit.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.edit(sysRoleRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:50
     */
    @GetResource(name = "角色查看", path = "/sysRole/detail", responseClass = SysRoleDTO.class)
    public ResponseData detail(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData(sysRoleService.detail(sysRoleRequest));
    }

    /**
     * 查询系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:19
     */
    @GetResource(name = "查询角色", path = "/sysRole/page", responseClass = SysRole.class)
    public ResponseData page(SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData(sysRoleService.findPage(sysRoleRequest));
    }

    /**
     * 角色授权资源
     *
     * @author 林选伟
     * @date 2020/11/22 19:51
     */
    @PostResource(name = "角色授权资源", path = "/sysRole/grantResource")
    public ResponseData grantResource(@RequestBody @Validated(SysRoleRequest.grantResource.class) SysRoleRequest sysRoleParam) {
        sysRoleResourceService.grantResource(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 角色授权菜单和按钮
     *
     * @author majianguo
     * @date 2021/1/9 18:04
     */
    @PostResource(name = "授权资源", path = "/sysRole/grantMenuAndButton")
    public ResponseData grantMenuAndButton(@RequestBody @Validated(SysRoleRequest.grantMenuButton.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.grantMenuAndButton(sysRoleRequest);
        return new SuccessResponseData();
    }

    /**
     * 设置角色绑定的数据范围类型和数据范围
     *
     * @author 林选伟
     * @date 2020/3/28 16:05
     */
    @PostResource(name = "设置角色绑定的数据范围类型和数据范围", path = "/sysRole/grantDataScope")
    public ResponseData grantData(@RequestBody @Validated(SysRoleRequest.grantDataScope.class) SysRoleRequest sysRoleParam) {
        sysRoleService.grantDataScope(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 系统角色下拉（用于用户授权角色时选择）
     *
     * @author majianguo
     * @date 2020/11/6 13:49
     */
    @GetResource(name = "角色下拉", path = "/sysRole/dropDown", responseClass = SimpleDict.class)
    public ResponseData dropDown() {
        return new SuccessResponseData(sysRoleService.dropDown());
    }

    /**
     * 拥有菜单
     *
     * @author majianguo
     * @date 2020/11/5 上午10:58
     */
    @GetResource(name = "角色拥有菜单", path = "/sysRole/getRoleMenus", responseClass = Long.class)
    public ResponseData getRoleMenus(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        Long roleId = sysRoleRequest.getRoleId();
        return new SuccessResponseData(sysRoleService.getMenuIdsByRoleIds(ListUtil.toList(roleId)));
    }

    /**
     * 拥有数据
     *
     * @author majianguo
     * @date 2020/11/5 上午10:59
     */
    @GetResource(name = "角色拥有数据", path = "/sysRole/getRoleDataScope", responseClass = Long.class)
    public ResponseData getRoleDataScope(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData(sysRoleService.getRoleDataScope(sysRoleRequest));
    }

}
