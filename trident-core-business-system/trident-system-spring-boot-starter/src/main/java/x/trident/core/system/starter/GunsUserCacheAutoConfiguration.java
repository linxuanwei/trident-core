
package x.trident.core.system.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.api.CacheOperatorApi;
import x.trident.core.system.api.constants.SystemConstants;
import x.trident.core.system.api.pojo.user.SysUserDTO;
import x.trident.core.system.modular.user.cache.SysUserMemoryCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户缓存的自动配置
 *
 * @author 林选伟
 * @date 2021/2/28 10:29
 */
@Configuration
public class GunsUserCacheAutoConfiguration {

    /**
     * 用户的缓存，非在线用户缓存，此缓存为了加快查看用户相关操作
     *
     * @author 林选伟
     * @date 2021/2/28 10:30
     */
    @Bean
    @ConditionalOnMissingBean(name = "sysUserCacheOperatorApi")
    public CacheOperatorApi<SysUserDTO> sysUserCacheOperatorApi() {
        TimedCache<String, SysUserDTO> sysUserTimedCache = CacheUtil.newTimedCache(SystemConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
        return new SysUserMemoryCache(sysUserTimedCache);
    }

}
