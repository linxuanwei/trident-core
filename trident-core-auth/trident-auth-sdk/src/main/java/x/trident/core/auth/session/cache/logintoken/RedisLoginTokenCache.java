
package x.trident.core.auth.session.cache.logintoken;

import x.trident.core.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static x.trident.core.auth.api.constants.AuthConstants.LOGGED_USERID_PREFIX;


/**
 * 基于redis的token的缓存
 *
 * @author 林选伟
 * @date 2020/12/24 19:16
 */
public class RedisLoginTokenCache extends AbstractRedisCacheOperator<Set<String>> {

    public RedisLoginTokenCache(RedisTemplate<String, Set<String>> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGGED_USERID_PREFIX;
    }

}
