
package x.trident.core.wrapper.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.wrapper.WrapperAop;

/**
 * Wrapper的自动配置
 *
 * @author 林选伟
 * @date 2021/1/19 22:42
 */
@Configuration
public class TridentWrapperAutoConfiguration {

    /**
     * Wrapper的自动配置
     */
    @Bean
    @ConditionalOnMissingBean(WrapperAop.class)
    public WrapperAop wrapperAop() {
        return new WrapperAop();
    }

}
