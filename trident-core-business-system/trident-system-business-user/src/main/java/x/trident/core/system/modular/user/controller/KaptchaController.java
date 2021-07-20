
package x.trident.core.system.modular.user.controller;

import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.security.api.CaptchaApi;
import x.trident.core.security.api.pojo.EasyCaptcha;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 图形验证码
 *
 * @author chenjinlong
 * @date 2021/1/15 15:11
 */
@RestController
@ApiResource(name = "用户登录图形验证码")
public class KaptchaController {

    @Resource
    private CaptchaApi captchaApi;

    @GetResource(name = "获取图形验证码", path = "/captcha", requiredPermission = false, requiredLogin = false, responseClass = EasyCaptcha.class)
    public ResponseData captcha() {
        return new SuccessResponseData(captchaApi.captcha());
    }

}
