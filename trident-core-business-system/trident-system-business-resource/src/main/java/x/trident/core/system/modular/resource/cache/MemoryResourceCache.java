
package x.trident.core.system.modular.resource.cache;

import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.memory.AbstractMemoryCacheOperator;
import x.trident.core.scanner.api.constants.ScannerConstants;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;


/**
 * 基于内存的资源缓存
 *
 * @author 林选伟
 * @date 2021/5/17 16:05
 */
public class MemoryResourceCache extends AbstractMemoryCacheOperator<ResourceDefinition> {

    /**
     * TimedCache的key是资源url，value是ResourceDefinition
     *
     * @author 林选伟
     * @date 2021/5/17 16:06
     */
    public MemoryResourceCache(TimedCache<String, ResourceDefinition> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return ScannerConstants.RESOURCE_CACHE_KEY;
    }

}
