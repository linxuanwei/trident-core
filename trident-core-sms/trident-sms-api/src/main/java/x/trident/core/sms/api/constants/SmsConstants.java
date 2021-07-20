
package x.trident.core.sms.api.constants;

/**
 * 短信模块的常量
 *
 * @author 林选伟
 * @date 2020/10/26 15:42
 */
public interface SmsConstants {

    /**
     * 短信模块的名称
     */
    String SMS_MODULE_NAME = "trident-core-sms";

    /**
     * 异常枚举的步进值
     */
    String SMS_EXCEPTION_STEP_CODE = "10";

    /**
     * 发送校验类短信
     * <p>
     * 系统会默认往短信参数中添加参数名为本常量的一个属性，用于放短信验证码
     */
    String SMS_CODE_PARAM_NAME = "code";

    /**
     * 默认短信失效时间（单位：秒）
     */
    Integer DEFAULT_SMS_INVALID_SECONDS = 300;

}
