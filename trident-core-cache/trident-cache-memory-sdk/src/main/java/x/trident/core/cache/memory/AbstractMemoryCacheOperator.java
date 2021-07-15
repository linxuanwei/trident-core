package x.trident.core.cache.memory;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import x.trident.core.cache.api.CacheOperatorApi;

import java.util.*;

/**
 * 基于内存的缓存封装
 *
 * @author 林选伟
 * @date 2020/7/9 10:09
 */
public abstract class AbstractMemoryCacheOperator<T> implements CacheOperatorApi<T> {

    private final TimedCache<String, T> timedCache;

    protected AbstractMemoryCacheOperator(TimedCache<String, T> timedCache) {
        this.timedCache = timedCache;
    }

    @Override
    public void put(String key, T value) {
        timedCache.put(getCommonKeyPrefix() + key, value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        timedCache.put(getCommonKeyPrefix() + key, value, timeoutSeconds * 1000);
    }

    @Override
    public T get(String key) {
        // 如果用户在超时前调用了get(key)方法，会重头计算起始时间，false的作用就是不从头算
        return timedCache.get(getCommonKeyPrefix() + key, true);
    }

    @Override
    public void remove(String... key) {
        if (key.length > 0) {
            for (String itemKey : key) {
                timedCache.remove(getCommonKeyPrefix() + itemKey);
            }
        }
    }

    @Override
    public void expire(String key, Long expiredSeconds) {
        T value = timedCache.get(getCommonKeyPrefix() + key, true);
        timedCache.put(getCommonKeyPrefix() + key, value, expiredSeconds * 1000);
    }

    @Override
    public boolean contains(String key) {
        return timedCache.containsKey(getCommonKeyPrefix() + key);
    }

    @Override
    public Collection<String> getAllKeys() {
        Iterator<CacheObj<String, T>> cacheObjIterator = timedCache.cacheObjIterator();
        ArrayList<String> keys = CollUtil.newArrayList();
        while (cacheObjIterator.hasNext()) {
            // 去掉缓存key的common prefix前缀
            String key = cacheObjIterator.next().getKey();
            keys.add(StrUtil.removePrefix(key, getCommonKeyPrefix()));
        }
        return keys;
    }

    @Override
    public Collection<T> getAllValues() {
        Iterator<CacheObj<String, T>> cacheObjIterator = timedCache.cacheObjIterator();
        ArrayList<T> values = CollUtil.newArrayList();
        while (cacheObjIterator.hasNext()) {
            values.add(cacheObjIterator.next().getValue());
        }
        return values;
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        Collection<String> allKeys = this.getAllKeys();
        HashMap<String, T> results = CollUtil.newHashMap();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }
}
