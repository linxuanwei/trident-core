
package x.trident.core.system.modular.user.controller;

import x.trident.core.auth.api.SessionManagerApi;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.user.OnlineUserDTO;
import x.trident.core.system.api.pojo.user.request.OnlineUserRequest;
import x.trident.core.system.modular.user.service.SysUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 在线用户管理
 *
 * @author 林选伟
 * @date 2021/1/11 22:52
 */
@RestController
@ApiResource(name = "在线用户管理")
public class OnlineUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SessionManagerApi sessionManagerApi;

    /**
     * 当前在线用户列表
     *
     * @author 林选伟
     * @date 2021/1/11 22:53
     */
    @GetResource(name = "当前在线用户列表", path = "/sysUser/onlineUserList",responseClass = OnlineUserDTO.class)
    public ResponseData onlineUserList(OnlineUserRequest onlineUserRequest) {
        return new SuccessResponseData(sysUserService.onlineUserList(onlineUserRequest));
    }

    /**
     * 踢掉在线用户
     *
     * @author 林选伟
     * @date 2021/1/11 22:53
     */
    @PostResource(name = "踢掉在线用户", path = "/sysUser/removeSession")
    public ResponseData removeSession(@Valid @RequestBody OnlineUserRequest onlineUserRequest) {
        sessionManagerApi.removeSession(onlineUserRequest.getToken());
        return new SuccessResponseData();
    }

}
