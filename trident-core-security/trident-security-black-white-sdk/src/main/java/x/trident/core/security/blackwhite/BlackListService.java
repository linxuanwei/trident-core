
package x.trident.core.security.blackwhite;

import x.trident.core.cache.api.CacheOperatorApi;
import x.trident.core.security.api.BlackListApi;

import java.util.Collection;

/**
 * 黑名单的实现
 * <p>
 * 黑名单的数据会在访问资源时被限制
 *
 * @author 林选伟
 * @date 2020/11/20 15:52
 */
public class BlackListService implements BlackListApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    public BlackListService(CacheOperatorApi<String> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    @Override
    public void addBlackItem(String content) {
        cacheOperatorApi.put(content, content);
    }

    @Override
    public void removeBlackItem(String content) {
        cacheOperatorApi.remove(content);
    }

    @Override
    public Collection<String> getBlackList() {
        return cacheOperatorApi.getAllKeys();
    }

    @Override
    public boolean contains(String content) {
        return cacheOperatorApi.contains(content);
    }

}
