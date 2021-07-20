
package x.trident.core.security.blackwhite.cache;

import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.memory.AbstractMemoryCacheOperator;
import x.trident.core.security.api.constants.CounterConstants;

/**
 * 黑名单用户的缓存
 *
 * @author 林选伟
 * @date 2020/11/20 15:50
 */
public class BlackListMemoryCache extends AbstractMemoryCacheOperator<String> {

    public BlackListMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CounterConstants.BLACK_LIST_CACHE_KEY_PREFIX;
    }

}
