
package x.trident.core.email.api.constants;

/**
 * 邮件发送模块的常量
 *
 * @author 林选伟
 * @date 2020/10/23 17:33
 */
public class MailConstants {
    private MailConstants() {
    }

    /**
     * 邮件模块的名称
     */
    public static final String MAIL_MODULE_NAME = "trident-core-email";

    /**
     * 异常枚举的步进值
     */
    public static final String MAIL_EXCEPTION_STEP_CODE = "08";

    /**
     * 默认smtp的host
     */
    public static final String DEFAULT_SMTP_HOST = "smtp.126.com";

    /**
     * 默认smtp的端口
     */
    public static final Integer DEFAULT_SMTP_PORT = 465;

    /**
     * 默认邮件发送账号，这里不要修改，在系统配置表改
     */
    public static final String DEFAULT_SMTP_USERNAME = "xxx@126.com";

    /**
     * 默认邮件发送密码或授权码，这里不要修改，在系统配置表改
     */
    public static final String DEFAULT_SMTP_PASSWORD = "xxx";

    /**
     * 默认开启tls
     */
    public static final Boolean DEFAULT_SMTP_TLS_ENABLE = true;

    /**
     * 是否开启账号密码验证
     */
    public static final Boolean DEFAULT_SMTP_AUTH_ENABLE = true;

    /**
     * 默认邮件的发送方
     */
    public static final String DEFAULT_SMTP_SEND_FROM = DEFAULT_SMTP_USERNAME;

    /**
     * 超时时间10秒
     */
    public static final Long TIMEOUT_MILLISECOND = 10000L;

}
