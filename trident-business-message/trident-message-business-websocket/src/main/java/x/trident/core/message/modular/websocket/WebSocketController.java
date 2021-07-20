
package x.trident.core.message.modular.websocket;

import org.springframework.web.bind.annotation.RestController;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;

/**
 * websocket控制器
 *
 * @author 林选伟
 * @date 2021/2/3 21:08
 */
@RestController
@ApiResource(name = "webSocket控制器")
public class WebSocketController {

    /**
     * 获取登录用户ws-url
     */
    @GetResource(name = "获取登录用户ws-url", path = "/webSocket/getWsUrl")
    public ResponseData getWsUrl() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return new SuccessResponseData(loginUser.getWsUrl());
    }

}
