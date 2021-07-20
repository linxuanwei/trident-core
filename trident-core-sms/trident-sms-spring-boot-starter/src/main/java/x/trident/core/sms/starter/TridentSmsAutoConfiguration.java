
package x.trident.core.sms.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.sms.aliyun.AliyunSmsSender;
import x.trident.core.sms.aliyun.msign.impl.MapBasedMultiSignManager;
import x.trident.core.sms.api.SmsSenderApi;
import x.trident.core.sms.api.expander.SmsConfigExpander;
import x.trident.core.sms.api.pojo.AliyunSmsProperties;

/**
 * 短信的自动配置类
 *
 * @author 林选伟
 * @date 2020/12/1 21:18
 */
@Configuration
public class TridentSmsAutoConfiguration {

    /**
     * 短信发送器的配置
     */
    @Bean
    @ConditionalOnMissingBean(SmsSenderApi.class)
    public SmsSenderApi smsSenderApi() {

        AliyunSmsProperties aliyunSmsProperties = new AliyunSmsProperties();

        // 配置默认从系统配置表读取
        aliyunSmsProperties.setAccessKeyId(SmsConfigExpander.getAliyunSmsAccessKeyId());
        aliyunSmsProperties.setAccessKeySecret(SmsConfigExpander.getAliyunSmsAccessKeySecret());
        aliyunSmsProperties.setSignName(SmsConfigExpander.getAliyunSmsSignName());

        return new AliyunSmsSender(new MapBasedMultiSignManager(), aliyunSmsProperties);
    }

}
