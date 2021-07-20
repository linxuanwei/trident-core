
package x.trident.core.security.blackwhite.cache;

import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.memory.AbstractMemoryCacheOperator;
import x.trident.core.security.api.constants.CounterConstants;

/**
 * 白名单的缓存
 *
 * @author 林选伟
 * @date 2020/11/15 15:26
 */
public class WhiteListMemoryCache extends AbstractMemoryCacheOperator<String> {

    public WhiteListMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CounterConstants.WHITE_LIST_CACHE_KEY_PREFIX;
    }

}
