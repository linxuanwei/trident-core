
package x.trident.core.security.captcha.cache;

import x.trident.core.cache.redis.AbstractRedisCacheOperator;
import x.trident.core.security.api.constants.CaptchaConstants;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 图形验证码缓存
 *
 * @author chenjinlong
 * @date 2021/1/15 13:44
 */
public class CaptchaRedisCache extends AbstractRedisCacheOperator<Long> {

    public CaptchaRedisCache(RedisTemplate<String, Long> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CaptchaConstants.CAPTCHA_CACHE_KEY_PREFIX;
    }

}
