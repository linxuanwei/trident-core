package x.trident.core.cache.memory.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.cache.api.constants.CacheConstants;
import x.trident.core.cache.memory.operator.DefaultMemoryCacheOperator;
import x.trident.core.cache.memory.operator.DefaultStringMemoryCacheOperator;

/**
 * 基于内存缓存的默认配置
 *
 * @author 林选伟
 * @date 2021/1/31 20:32
 */
@Configuration
public class TridentMemoryCacheAutoConfiguration {

    /**
     * 创建默认的value是string类型的内存缓存
     */
    @Bean
    public DefaultStringMemoryCacheOperator defaultStringMemoryCacheOperator() {
        TimedCache<String, String> stringTimedCache = CacheUtil.newTimedCache(CacheConstants.DEFAULT_CACHE_TIMEOUT);
        return new DefaultStringMemoryCacheOperator(stringTimedCache);
    }

    /**
     * 创建默认的value是object类型的内存缓存
     */
    @Bean
    public DefaultMemoryCacheOperator defaultMemoryCacheOperator() {
        TimedCache<String, Object> objectTimedCache = CacheUtil.newTimedCache(CacheConstants.DEFAULT_CACHE_TIMEOUT);
        return new DefaultMemoryCacheOperator(objectTimedCache);
    }

}
