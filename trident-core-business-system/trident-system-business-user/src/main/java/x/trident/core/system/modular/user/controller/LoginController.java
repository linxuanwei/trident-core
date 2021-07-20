
package x.trident.core.system.modular.user.controller;

import x.trident.core.auth.api.AuthServiceApi;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.auth.api.pojo.auth.LoginRequest;
import x.trident.core.auth.api.pojo.auth.LoginResponse;
import x.trident.core.auth.api.pojo.auth.LoginWithTokenRequest;
import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.login.CurrentUserInfoResponse;
import x.trident.core.system.modular.user.factory.UserLoginInfoFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录登出控制器
 *
 * @author 林选伟
 * @date 2021/3/17 17:23
 */
@RestController
@Slf4j
@ApiResource(name = "登陆登出管理")
public class LoginController {

    @Resource
    private AuthServiceApi authServiceApi;

    /**
     * 用户登陆
     *
     * @author 林选伟
     * @date 2021/3/17 17:23
     */
    @PostResource(name = "登陆", path = "/login", requiredLogin = false, requiredPermission = false, responseClass = String.class)
    public ResponseData login(@RequestBody @Validated LoginRequest loginRequest) {
        loginRequest.setCreateCookie(true);
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return new SuccessResponseData(loginResponse.getToken());
    }

    /**
     * 用户登陆(提供给分离版用的接口，不会写cookie)
     *
     * @author 林选伟
     * @date 2021/3/17 17:23
     */
    @PostResource(name = "登陆（分离版）", path = "/loginApi", requiredLogin = false, requiredPermission = false, responseClass = LoginResponse.class)
    public ResponseData loginApi(@RequestBody @Validated LoginRequest loginRequest) {
        loginRequest.setCreateCookie(false);
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return new SuccessResponseData(loginResponse);
    }

    /**
     * 基于token登录，适用于单点登录，将caToken请求过来，进行解析，并创建本系统可以识别的token
     *
     * @author 林选伟
     * @date 2021/5/25 22:36
     */
    @PostResource(name = "适用于单点登录", path = "/loginWithToken", requiredLogin = false, requiredPermission = false, responseClass = String.class)
    public ResponseData loginWithToken(@RequestBody @Validated LoginWithTokenRequest loginWithTokenRequest) {
        LoginResponse loginResponse = authServiceApi.loginWithToken(loginWithTokenRequest);
        return new SuccessResponseData(loginResponse.getToken());
    }

    /**
     * 用户登出
     *
     * @author 林选伟
     * @date 2021/3/17 17:24
     */
    @ApiResource(name = "登出", path = "/logout", requiredPermission = false, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData logoutPage() {
        authServiceApi.logout();
        return new SuccessResponseData();
    }

    /**
     * 获取当前用户的用户信息
     *
     * @author 林选伟
     * @date 2021/3/17 17:37
     */
    @GetResource(name = "获取当前用户的用户信息", path = "/getCurrentLoginUserInfo", requiredPermission = false, responseClass = CurrentUserInfoResponse.class)
    public ResponseData getCurrentLoginUserInfo() {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        // 转化返回结果
        CurrentUserInfoResponse currentUserInfoResponse = UserLoginInfoFactory.parseUserInfo(loginUser);

        return new SuccessResponseData(currentUserInfoResponse);
    }

}
