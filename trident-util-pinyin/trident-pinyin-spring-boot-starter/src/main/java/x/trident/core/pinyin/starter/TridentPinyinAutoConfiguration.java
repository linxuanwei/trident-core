
package x.trident.core.pinyin.starter;

import x.trident.core.pinyin.PinyinServiceImpl;
import x.trident.core.pinyin.api.PinYinApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 拼音的自动配置
 *
 * @author 林选伟
 * @date 2020/12/4 15:28
 */
@Configuration
public class TridentPinyinAutoConfiguration {

    /**
     * 拼音工具接口的封装
     *
     * @author 林选伟
     * @date 2020/12/4 15:29
     */
    @Bean
    @ConditionalOnMissingBean(PinYinApi.class)
    public PinYinApi pinYinApi() {
        return new PinyinServiceImpl();
    }

}
