
package x.trident.core.sms.modular.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.sms.modular.param.SysSmsInfoParam;
import x.trident.core.sms.modular.param.SysSmsSendParam;
import x.trident.core.sms.modular.param.SysSmsVerifyParam;
import x.trident.core.sms.modular.service.SysSmsInfoService;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 短信发送控制器
 *
 * @author 林选伟
 * @date 2020/10/26 18:34
 */
@RestController
@ApiResource(name = "短信发送控制器")
public class SmsSenderController {

    @Resource
    private SysSmsInfoService sysSmsInfoService;

    /**
     * 发送记录查询
     */
    @GetResource(name = "发送记录查询", path = "/sms/page")
    public ResponseData page(SysSmsInfoParam sysSmsInfoParam) {
        return new SuccessResponseData(sysSmsInfoService.page(sysSmsInfoParam));
    }

    /**
     * 发送验证码短信
     */
    @PostResource(name = "发送验证码短信", path = "/sms/sendLoginMessage", requiredLogin = false, requiredPermission = false)
    public ResponseData sendMessage(@RequestBody @Validated SysSmsSendParam sysSmsSendParam) {

        // 清空params参数
        sysSmsSendParam.setParams(null);

        // 设置模板中的参数
        HashMap<String, Object> paramMap = CollectionUtil.newHashMap();
        paramMap.put("code", RandomUtil.randomNumbers(6));
        sysSmsSendParam.setParams(paramMap);

        return new SuccessResponseData(sysSmsInfoService.sendShortMessage(sysSmsSendParam));
    }

    /**
     * 验证短信验证码
     */
    @PostResource(name = "验证短信验证码", path = "/sms/validateMessage", requiredLogin = false, requiredPermission = false)
    public ResponseData validateMessage(@RequestBody @Validated SysSmsVerifyParam sysSmsVerifyParam) {
        sysSmsInfoService.validateSmsInfo(sysSmsVerifyParam);
        return new SuccessResponseData("短信验证成功");
    }

}
