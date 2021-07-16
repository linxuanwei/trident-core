
package x.trident.core.validator.starter;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Guns的校验器的自动配置
 *
 * @author 林选伟
 * @date 2021/3/18 16:03
 */
@Configuration
@AutoConfigureBefore(ValidationAutoConfiguration.class)
public class TridentValidatorAutoConfiguration {

    /**
     * 自定义的spring参数校验器，重写主要为了保存一些在自定义validator中读不到的属性
     *
     * @author 林选伟
     * @date 2020/8/12 20:18
     */
    @Bean
    public TridentValidator gunsValidator() {
        return new TridentValidator();
    }

}
