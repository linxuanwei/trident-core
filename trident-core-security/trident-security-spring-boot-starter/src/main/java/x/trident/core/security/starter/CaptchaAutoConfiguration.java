
package x.trident.core.security.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import x.trident.core.security.captcha.CaptchaService;
import x.trident.core.security.captcha.cache.CaptchaMemoryCache;
import x.trident.core.cache.api.constants.CacheConstants;
import x.trident.core.security.api.CaptchaApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 图形验证码自动配置
 *
 * @author 林选伟
 * @date 2020/12/1 21:44
 */
@Configuration
public class CaptchaAutoConfiguration {

    /**
     * 图形验证码
     *
     * @author chenjinlong
     * @date 2021/1/15 11:25
     */
    @Bean
    @ConditionalOnMissingBean(CaptchaApi.class)
    public CaptchaApi captchaApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        CaptchaMemoryCache captchaMemoryCache = new CaptchaMemoryCache(timedCache);
        return new CaptchaService(captchaMemoryCache);
    }

}
