
package x.trident.core.jwt.starter;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.jwt.JwtTokenOperator;
import x.trident.core.jwt.api.JwtApi;
import x.trident.core.jwt.api.expander.JwtConfigExpander;
import x.trident.core.jwt.api.pojo.config.JwtConfig;

/**
 * jwt的自动配置
 *
 * @author 林选伟
 * @date 2020/12/1 14:34
 */
@Configuration
public class TridentJwtAutoConfiguration {

    /**
     * jwt操作工具类的配置
     */
    @Bean
    @ConditionalOnMissingBean(JwtApi.class)
    public JwtApi jwtApi() {

        JwtConfig jwtConfig = new JwtConfig();

        // 从系统配置表中读取配置
        jwtConfig.setJwtSecret(JwtConfigExpander.getJwtSecret());
        jwtConfig.setExpiredSeconds(JwtConfigExpander.getJwtTimeoutSeconds());

        return new JwtTokenOperator(jwtConfig);
    }

}
