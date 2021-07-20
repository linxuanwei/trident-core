
package x.trident.core.sms.modular.param;

import x.trident.core.sms.modular.enums.SmsSendSourceEnum;
import x.trident.core.sms.modular.enums.SmsTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 发送短信的参数
 *
 * @author 林选伟
 * @date 2020/10/26 22:16
 */
@Data
public class SysSmsSendParam {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号码为空")
    private String phone;

    /**
     * 模板号
     */
    @NotBlank(message = "模板号为空")
    private String templateCode;

    /**
     * 缓存 key
     */
    private String verKey;

    /**
     * 图形验证码
     */
    private String verCode;

    /**
     * 模板中的参数
     */
    private Map<String, Object> params;

    /**
     * 发送源
     */
    private SmsSendSourceEnum smsSendSourceEnum = SmsSendSourceEnum.PC;

    /**
     * 消息类型，1验证码，2消息，默认不传为验证码
     */
    private SmsTypeEnum smsTypeEnum = SmsTypeEnum.SMS;

}
