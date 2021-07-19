
package x.trident.core.jwt.api.expander;

import cn.hutool.core.util.RandomUtil;
import x.trident.core.config.api.context.ConfigContext;

import static x.trident.core.jwt.api.constants.JwtConstants.DEFAULT_JWT_TIMEOUT_SECONDS;

/**
 * jwt工具类的配置获取
 *
 * @author 林选伟
 * @date 2020/12/1 15:05
 */
public class JwtConfigExpander {
    private JwtConfigExpander() {
    }

    /**
     * 获取jwt的密钥
     */
    public static String getJwtSecret() {
        String sysJwtSecret = ConfigContext.me().getConfigValueNullable("SYS_JWT_SECRET", String.class);

        // 没配置就返回一个随机密码
        if (sysJwtSecret == null) {
            return RandomUtil.randomString(20);
        } else {
            return sysJwtSecret;
        }
    }

    /**
     * jwt失效时间，默认1天
     */
    public static Long getJwtTimeoutSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_JWT_TIMEOUT_SECONDS", Long.class, DEFAULT_JWT_TIMEOUT_SECONDS);
    }

}
