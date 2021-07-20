
package x.trident.core.system.modular.user.cache;

import x.trident.core.cache.redis.AbstractRedisCacheOperator;
import x.trident.core.system.api.constants.SystemConstants;
import x.trident.core.system.api.pojo.user.SysUserDTO;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 用户的缓存
 *
 * @author 林选伟
 * @date 2021/2/28 10:23
 */
public class SysUserRedisCache extends AbstractRedisCacheOperator<SysUserDTO> {

    public SysUserRedisCache(RedisTemplate<String, SysUserDTO> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return SystemConstants.USER_CACHE_PREFIX;
    }

}
