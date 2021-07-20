
package x.trident.core.system.modular.user.cache;

import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.memory.AbstractMemoryCacheOperator;
import x.trident.core.system.api.constants.SystemConstants;
import x.trident.core.system.api.pojo.user.SysUserDTO;

/**
 * 用户的缓存
 *
 * @author 林选伟
 * @date 2021/2/28 10:23
 */
public class SysUserMemoryCache extends AbstractMemoryCacheOperator<SysUserDTO> {

    public SysUserMemoryCache(TimedCache<String, SysUserDTO> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return SystemConstants.USER_CACHE_PREFIX;
    }

}
