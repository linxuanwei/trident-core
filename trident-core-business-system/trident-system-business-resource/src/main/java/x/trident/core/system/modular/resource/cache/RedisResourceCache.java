
package x.trident.core.system.modular.resource.cache;

import x.trident.core.cache.redis.AbstractRedisHashCacheOperator;
import x.trident.core.scanner.api.constants.ScannerConstants;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 基于redis的资源缓存
 *
 * @author 林选伟
 * @date 2021/5/17 16:05
 */
public class RedisResourceCache extends AbstractRedisHashCacheOperator<ResourceDefinition> {

    /**
     * RedisTemplate的key是资源url，value是ResourceDefinition
     *
     * @author 林选伟
     * @date 2021/5/17 16:06
     */
    public RedisResourceCache(RedisTemplate<String, ResourceDefinition> redisTemplate) {
        super(redisTemplate);
    }

    /**
     * hash结构的key
     *
     * @author 林选伟
     * @date 2021/5/17 17:34
     */
    @Override
    public String getCommonKeyPrefix() {
        return ScannerConstants.RESOURCE_CACHE_KEY;
    }

}
