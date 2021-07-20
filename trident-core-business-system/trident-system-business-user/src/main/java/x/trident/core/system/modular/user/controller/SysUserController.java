
package x.trident.core.system.modular.user.controller;

import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.user.SysUserDTO;
import x.trident.core.system.api.pojo.user.UserSelectTreeNode;
import x.trident.core.system.api.pojo.user.request.SysUserRequest;
import x.trident.core.system.modular.user.service.SysUserRoleService;
import x.trident.core.system.modular.user.service.SysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户管理控制器
 *
 * @author luojie
 * @date 2020/11/6 09:47
 */
@RestController
@ApiResource(name = "用户管理")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 注册用户
     *
     * @author chenjinlong
     * @date 2021/01/16 13:50
     */
    @PostResource(name = "系统用户_注册", path = "/sysUser/register", requiredLogin = false, requiredPermission = false)
    public ResponseData register(@RequestBody @Validated(SysUserRequest.reg.class) SysUserRequest sysUserRequest) {
        sysUserService.register(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 增加用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_增加", path = "/sysUser/add")
    public ResponseData add(@RequestBody @Validated(BaseRequest.add.class) SysUserRequest sysUserRequest) {
        sysUserService.add(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_删除", path = "/sysUser/delete")
    public ResponseData delete(@RequestBody @Validated(SysUserRequest.delete.class) SysUserRequest sysUserRequest) {
        sysUserService.del(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 批量删除系统用户
     *
     * @author 林选伟
     * @date 2021/4/7 16:12
     */
    @PostResource(name = "系统用户_批量删除系统用户", path = "/sysUser/batchDelete")
    public ResponseData batchDelete(@RequestBody @Validated(SysUserRequest.batchDelete.class) SysUserRequest sysUserRequest) {
        sysUserService.batchDelete(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_编辑", path = "/sysUser/edit")
    public ResponseData edit(@RequestBody @Validated(SysUserRequest.edit.class) SysUserRequest sysUserRequest) {
        sysUserService.edit(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改状态
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_修改状态", path = "/sysUser/changeStatus")
    public ResponseData changeStatus(@RequestBody @Validated(SysUserRequest.changeStatus.class) SysUserRequest sysUserRequest) {
        sysUserService.editStatus(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 重置密码
     *
     * @author luojie
     * @date 2020/11/6 13:48
     */
    @PostResource(name = "系统用户_重置密码", path = "/sysUser/resetPwd")
    public ResponseData resetPwd(@RequestBody @Validated(SysUserRequest.resetPwd.class) SysUserRequest sysUserRequest) {
        sysUserService.resetPassword(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 授权角色
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_授权角色", path = "/sysUser/grantRole")
    public ResponseData grantRole(@RequestBody @Validated(SysUserRequest.grantRole.class) SysUserRequest sysUserRequest) {
        sysUserService.grantRole(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 授权数据
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_授权数据", path = "/sysUser/grantData")
    public ResponseData grantData(@RequestBody @Validated(SysUserRequest.grantData.class) SysUserRequest sysUserRequest) {
        sysUserService.grantData(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @GetResource(name = "系统用户_查看", path = "/sysUser/detail", responseClass = SysUserDTO.class)
    public ResponseData detail(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        return new SuccessResponseData(sysUserService.detail(sysUserRequest));
    }

    /**
     * 获取当前登录用户的信息
     *
     * @author 林选伟
     * @date 2021/1/1 19:01
     */
    @GetResource(name = "获取当前登录用户的信息", path = "/sysUser/currentUserInfo", requiredPermission = false,responseClass = SysUserDTO.class)
    public ResponseData currentUserInfo() {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setUserId(loginUser.getUid());
        return new SuccessResponseData(sysUserService.detail(sysUserRequest));
    }

    /**
     * 查询系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:49
     */
    @GetResource(name = "系统用户_查询", path = "/sysUser/page",responseClass = SysUserDTO.class)
    public ResponseData page(SysUserRequest sysUserRequest) {
        return new SuccessResponseData(sysUserService.findPage(sysUserRequest));
    }

    /**
     * 导出用户
     *
     * @author luojie
     * @date 2020/11/6 13:57
     */
    @GetResource(name = "系统用户_导出", path = "/sysUser/export")
    public void export(HttpServletResponse response) {
        sysUserService.export(response);
    }

    /**
     * 获取用户选择树数据（用在系统通知，选择发送人的时候）
     *
     * @author 林选伟
     * @date 2021/1/15 8:28
     */
    @GetResource(name = "获取用户选择树数据（用在系统通知，选择发送人的时候）", path = "/sysUser/getUserSelectTree",responseClass = UserSelectTreeNode.class)
    public ResponseData getUserTree() {
        return new SuccessResponseData(this.sysUserService.userSelectTree(new SysUserRequest()));
    }

    /**
     * 获取用户数据范围列表
     *
     * @author luojie
     * @date 2020/11/6 13:51
     */
    @GetResource(name = "系统用户_获取用户数据范围列表", path = "/sysUser/getUserDataScope")
    public ResponseData ownData(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        List<Long> userBindDataScope = sysUserService.getUserBindDataScope(sysUserRequest.getUserId());
        return new SuccessResponseData(userBindDataScope);
    }

    /**
     * 获取用户的角色列表
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @GetResource(name = "系统用户_获取用户的角色列表", path = "/sysUser/getUserRoles")
    public ResponseData ownRole(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        Long userId = sysUserRequest.getUserId();
        return new SuccessResponseData(sysUserRoleService.findListByUserId(userId));
    }

    /**
     * 用户下拉列表，可以根据姓名搜索
     *
     * @param sysUserRequest 请求参数：name 姓名(可选)
     * @return 返回除超级管理员外的用户列表
     * @author luojie
     * @date 2020/11/6 09:49
     */
    @GetResource(name = "系统用户_选择器", path = "/sysUser/selector")
    public ResponseData selector(SysUserRequest sysUserRequest) {
        return new SuccessResponseData(sysUserService.selector(sysUserRequest));
    }

}
