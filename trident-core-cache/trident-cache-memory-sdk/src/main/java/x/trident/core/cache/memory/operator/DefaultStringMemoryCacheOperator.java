package x.trident.core.cache.memory.operator;

import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.api.constants.CacheConstants;
import x.trident.core.cache.memory.AbstractMemoryCacheOperator;

/**
 * 默认内存缓存的实现，value存放String类型
 *
 * @author 林选伟
 * @date 2021/2/24 22:16
 */
public class DefaultStringMemoryCacheOperator extends AbstractMemoryCacheOperator<String> {

    public DefaultStringMemoryCacheOperator(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_STRING_CACHE_PREFIX;
    }

}
