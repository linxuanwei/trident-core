
package x.trident.core.email.api.expander;

import x.trident.core.config.api.context.ConfigContext;
import x.trident.core.email.api.constants.MailConstants;

/**
 * 邮件相关的配置
 *
 * @author 林选伟
 * @date 2020/12/1 11:45
 */
public class EmailConfigExpander {

    private EmailConfigExpander() {
    }

    /**
     * smtp服务器地址，默认用126的邮箱
     */
    public static String getSmtpHost() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SMTP_HOST", String.class, MailConstants.DEFAULT_SMTP_HOST);
    }

    /**
     * smtp服务端口
     */
    public static Integer getSmtpPort() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SMTP_PORT", Integer.class, MailConstants.DEFAULT_SMTP_PORT);
    }

    /**
     * 是否启用账号密码验证
     */
    public static Boolean getSmtpAuthEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_ENABLE_AUTH", Boolean.class, MailConstants.DEFAULT_SMTP_AUTH_ENABLE);
    }

    /**
     * 邮箱的账号
     */
    public static String getSmtpUser() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_ACCOUNT", String.class, MailConstants.DEFAULT_SMTP_USERNAME);
    }

    /**
     * 邮箱的密码或者授权码
     */
    public static String getSmtpPass() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_PASSWORD", String.class, MailConstants.DEFAULT_SMTP_PASSWORD);
    }

    /**
     * 邮箱的发送方邮箱
     */
    public static String getSmtpFrom() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SEND_FROM", String.class, MailConstants.DEFAULT_SMTP_SEND_FROM);
    }

    /**
     * 使用 STARTTLS安全连接，STARTTLS是对纯文本通信协议的扩展。它将纯文本连接升级为加密连接（TLS或SSL）， 而不是使用一个单独的加密通信端口。
     *
     * @author 林选伟
     * @date 2020/12/1 11:50
     */
    public static Boolean getStartTlsEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_START_TLS_ENABLE", Boolean.class, MailConstants.DEFAULT_SMTP_TLS_ENABLE);
    }

    /**
     * 使用 SSL安全连接
     */
    public static Boolean getSSLEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_TLS_ENABLE", Boolean.class, MailConstants.DEFAULT_SMTP_TLS_ENABLE);
    }

    /**
     * 指定的端口连接到在使用指定的套接字工厂
     */
    public static Integer getSocketFactoryPort() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SOCKET_FACTORY_PORT", Integer.class, MailConstants.DEFAULT_SMTP_PORT);
    }

    /**
     * SMTP超时时长，单位毫秒
     */
    public static Long getTimeout() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SMTP_TIMEOUT", Long.class, MailConstants.TIMEOUT_MILLISECOND);
    }

    /**
     * Socket连接超时值，单位毫秒，缺省值不超时
     */
    public static Long getConnectionTimeout() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_CONNECTION_TIMEOUT", Long.class, MailConstants.TIMEOUT_MILLISECOND);
    }

}
