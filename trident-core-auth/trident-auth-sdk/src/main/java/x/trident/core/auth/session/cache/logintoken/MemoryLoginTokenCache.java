
package x.trident.core.auth.session.cache.logintoken;

import cn.hutool.cache.impl.TimedCache;
import x.trident.core.cache.memory.AbstractMemoryCacheOperator;

import java.util.Set;

import static x.trident.core.auth.api.constants.AuthConstants.LOGGED_USERID_PREFIX;


/**
 * 基于内存的token缓存
 *
 * @author 林选伟
 * @date 2020/12/24 19:16
 */
public class MemoryLoginTokenCache extends AbstractMemoryCacheOperator<Set<String>> {

    public MemoryLoginTokenCache(TimedCache<String, Set<String>> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGGED_USERID_PREFIX;
    }

}
