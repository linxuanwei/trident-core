
package x.trident.core.system.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.api.CacheOperatorApi;
import x.trident.core.cache.api.constants.CacheConstants;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import x.trident.core.system.modular.resource.cache.MemoryResourceCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 资源缓存自动配置
 *
 * @author 林选伟
 * @date 2021/5/17 16:44
 */
@Configuration
public class GunsResourceCacheAutoConfiguration {

    /**
     * 资源缓存
     *
     * @author 林选伟
     * @date 2021/5/17 16:44
     */
    @Bean
    @ConditionalOnMissingBean(name = "resourceCache")
    public CacheOperatorApi<ResourceDefinition> resourceCache() {
        TimedCache<String, ResourceDefinition> timedCache = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        return new MemoryResourceCache(timedCache);
    }

}
