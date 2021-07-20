
package x.trident.core.sms.api.expander;

import cn.hutool.core.util.StrUtil;
import x.trident.core.config.api.context.ConfigContext;
import x.trident.core.sms.api.constants.SmsConstants;

/**
 * 短信相关的配置拓展
 *
 * @author 林选伟
 * @date 2020/10/26 22:09
 */
public class SmsConfigExpander {
    private SmsConfigExpander() {
    }

    /**
     * 获取短信验证码失效时间（单位：秒）
     * <p>
     * 默认300秒
     *
     * @author 林选伟
     * @date 2020/10/26 22:09
     */
    public static Integer getSmsValidateExpiredSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SMS_VALIDATE_EXPIRED_SECONDS", Integer.class, SmsConstants.DEFAULT_SMS_INVALID_SECONDS);
    }

    /**
     * 阿里云短信的accessKeyId
     *
     * @author 林选伟
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsAccessKeyId() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_ALIYUN_SMS_ACCESS_KEY_ID", String.class, StrUtil.EMPTY);
    }

    /**
     * 阿里云短信的accessKeySecret
     *
     * @author 林选伟
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsAccessKeySecret() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_ALIYUN_SMS_ACCESS_KEY_SECRET", String.class, StrUtil.EMPTY);
    }

    /**
     * 阿里云短信的签名
     *
     * @author 林选伟
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsSignName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_ALIYUN_SMS_SIGN_NAME", String.class, StrUtil.EMPTY);
    }

}
