
package x.trident.core.i18n.starter;

import x.trident.core.i18n.TranslationContainer;
import x.trident.core.i18n.api.TranslationApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多语言翻译的自动配置
 *
 * @author 林选伟
 * @date 2021/1/24 16:42
 */
@Configuration
public class GunsTranslationAutoConfiguration {

    /**
     * 多语言翻译条目存放容器
     *
     * @author 林选伟
     * @date 2021/1/24 19:42
     */
    @Bean
    public TranslationApi translationApi() {
        return new TranslationContainer();
    }

}
