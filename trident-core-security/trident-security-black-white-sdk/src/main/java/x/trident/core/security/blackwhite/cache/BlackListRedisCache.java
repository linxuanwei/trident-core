
package x.trident.core.security.blackwhite.cache;

import x.trident.core.cache.redis.AbstractRedisCacheOperator;
import x.trident.core.security.api.constants.CounterConstants;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 黑名单用户的缓存
 *
 * @author 林选伟
 * @date 2020/11/20 15:50
 */
public class BlackListRedisCache extends AbstractRedisCacheOperator<String> {

    public BlackListRedisCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CounterConstants.BLACK_LIST_CACHE_KEY_PREFIX;
    }

}
