
package x.trident.core.auth.starter;

import x.trident.core.auth.api.pojo.SsoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 单点配置
 *
 * @author 林选伟
 * @date 2021/5/25 22:29
 */
@Configuration
public class TridentSsoAutoConfiguration {

    /**
     * 单点的开关配置
     *
     * @author 林选伟
     * @date 2021/5/25 22:29
     */
    @Bean
    @ConfigurationProperties(prefix = "sso")
    public SsoProperties ssoProperties() {
        return new SsoProperties();
    }

}
