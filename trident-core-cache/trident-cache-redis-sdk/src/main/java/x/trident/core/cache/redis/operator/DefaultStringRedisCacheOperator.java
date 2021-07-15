package x.trident.core.cache.redis.operator;

import x.trident.core.cache.api.constants.CacheConstants;
import x.trident.core.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 默认redis缓存的实现，value存放String类型
 *
 * @author 林选伟
 * @date 2021/2/24 22:16
 */
public class DefaultStringRedisCacheOperator extends AbstractRedisCacheOperator<String> {

    public DefaultStringRedisCacheOperator(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_STRING_CACHE_PREFIX;
    }

}
