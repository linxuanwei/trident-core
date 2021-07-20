
package x.trident.core.email.api;

import x.trident.core.email.api.pojo.SendMailParam;

/**
 * 邮件收发统一接口
 *
 * @author 林选伟
 * @date 2020/10/23 17:30
 */
public interface MailSenderApi {

    /**
     * 发送普通邮件
     *
     * @param sendMailParam 发送邮件的参数
     */
    void sendMail(SendMailParam sendMailParam);

    /**
     * 发送html的邮件
     *
     * @param sendMailParam 发送邮件的参数
     */
    void sendMailHtml(SendMailParam sendMailParam);

}
