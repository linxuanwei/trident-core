
package x.trident.core.auth.session.cache.loginuser;

import cn.hutool.cache.impl.TimedCache;
import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.cache.memory.AbstractMemoryCacheOperator;

import static x.trident.core.auth.api.constants.AuthConstants.LOGGED_TOKEN_PREFIX;

/**
 * 基于内存的登录用户缓存
 *
 * @author 林选伟
 * @date 2020/12/24 19:16
 */
public class MemoryLoginUserCache extends AbstractMemoryCacheOperator<LoginUser> {

    public MemoryLoginUserCache(TimedCache<String, LoginUser> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGGED_TOKEN_PREFIX;
    }

}
