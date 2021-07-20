
package x.trident.core.security.blackwhite.cache;

import x.trident.core.cache.redis.AbstractRedisCacheOperator;
import x.trident.core.security.api.constants.CounterConstants;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 白名单的缓存
 *
 * @author 林选伟
 * @date 2020/11/15 15:26
 */
public class WhiteListRedisCache extends AbstractRedisCacheOperator<Long> {

    public WhiteListRedisCache(RedisTemplate<String, Long> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CounterConstants.WHITE_LIST_CACHE_KEY_PREFIX;
    }

}
