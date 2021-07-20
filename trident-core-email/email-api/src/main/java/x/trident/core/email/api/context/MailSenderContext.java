
package x.trident.core.email.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.email.api.MailSenderApi;

/**
 * 邮件发送的api上下文
 *
 * @author 林选伟
 * @date 2020/10/26 10:16
 */
public class MailSenderContext {
    private MailSenderContext() {
    }

    /**
     * 获取邮件发送的接口
     */
    public static MailSenderApi me() {
        return SpringUtil.getBean(MailSenderApi.class);
    }

}
