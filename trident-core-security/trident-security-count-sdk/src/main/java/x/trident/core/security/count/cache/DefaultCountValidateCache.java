
package x.trident.core.security.count.cache;

import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.memory.AbstractMemoryCacheOperator;
import x.trident.core.security.api.constants.CounterConstants;


/**
 * 计数用的缓存
 *
 * @author 林选伟
 * @date 2020/11/15 15:26
 */
public class DefaultCountValidateCache extends AbstractMemoryCacheOperator<Long> {

    public DefaultCountValidateCache(TimedCache<String, Long> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CounterConstants.COUNT_VALIDATE_CACHE_KEY_PREFIX;
    }

}
