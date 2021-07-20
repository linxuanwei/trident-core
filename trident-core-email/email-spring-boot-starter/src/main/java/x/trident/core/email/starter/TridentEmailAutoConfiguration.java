
package x.trident.core.email.starter;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.email.api.MailSenderApi;
import x.trident.core.email.api.expander.EmailConfigExpander;
import x.trident.core.email.jdk.JavaMailSender;

/**
 * 邮件发送的自动配置类
 *
 * @author 林选伟
 * @date 2020/12/1 11:25
 */
@Configuration
public class TridentEmailAutoConfiguration {

    /**
     * java mail方式发送邮件的接口
     */
    @Bean
    @ConditionalOnMissingBean(MailSenderApi.class)
    public MailSenderApi mailSenderApi() {
        MailAccount mailAccount = new MailAccount();

        // 配置默认都从系统配置表获取
        mailAccount.setHost(EmailConfigExpander.getSmtpHost());
        mailAccount.setPort(EmailConfigExpander.getSmtpPort());
        mailAccount.setAuth(EmailConfigExpander.getSmtpAuthEnable());
        mailAccount.setUser(EmailConfigExpander.getSmtpUser());
        mailAccount.setPass(EmailConfigExpander.getSmtpPass());
        mailAccount.setFrom(EmailConfigExpander.getSmtpFrom());
        mailAccount.setStarttlsEnable(EmailConfigExpander.getStartTlsEnable());
        mailAccount.setSslEnable(EmailConfigExpander.getSSLEnable());
        mailAccount.setSocketFactoryPort(EmailConfigExpander.getSocketFactoryPort());
        mailAccount.setTimeout(EmailConfigExpander.getTimeout());
        mailAccount.setConnectionTimeout(EmailConfigExpander.getConnectionTimeout());

        return new JavaMailSender(mailAccount);
    }

}
