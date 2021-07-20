
package x.trident.core.auth.session.cache.loginuser;

import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

import static x.trident.core.auth.api.constants.AuthConstants.LOGGED_TOKEN_PREFIX;


/**
 * 基于redis的登录用户缓存
 *
 * @author 林选伟
 * @date 2020/12/24 19:16
 */
public class RedisLoginUserCache extends AbstractRedisCacheOperator<LoginUser> {

    public RedisLoginUserCache(RedisTemplate<String, LoginUser> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGGED_TOKEN_PREFIX;
    }

}
