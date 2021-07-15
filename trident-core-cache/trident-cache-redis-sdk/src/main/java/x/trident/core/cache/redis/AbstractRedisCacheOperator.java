package x.trident.core.cache.redis;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.core.RedisTemplate;
import x.trident.core.cache.api.CacheOperatorApi;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 基于redis的缓存封装
 *
 * @author 林选伟
 * @date 2020/7/9 10:09
 */
public abstract class AbstractRedisCacheOperator<T> implements CacheOperatorApi<T> {

    private final RedisTemplate<String, T> redisTemplate;

    protected AbstractRedisCacheOperator(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, T value) {
        redisTemplate.boundValueOps(getCommonKeyPrefix() + key).set(value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        redisTemplate.boundValueOps(getCommonKeyPrefix() + key).set(value, timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public T get(String key) {
        return redisTemplate.boundValueOps(getCommonKeyPrefix() + key).get();
    }

    @Override
    public void remove(String... key) {
        ArrayList<String> keys = CollUtil.toList(key);
        List<String> withPrefixKeys = keys.stream().map(i -> getCommonKeyPrefix() + i).collect(Collectors.toList());
        redisTemplate.delete(withPrefixKeys);
    }

    @Override
    public void expire(String key, Long expiredSeconds) {
        redisTemplate.boundValueOps(getCommonKeyPrefix() + key).expire(expiredSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean contains(String key) {
        T value = redisTemplate.boundValueOps(getCommonKeyPrefix() + key).get();
        return value != null;
    }

    @Override
    public Collection<String> getAllKeys() {
        Set<String> keys = redisTemplate.keys(getCommonKeyPrefix() + "*");
        if (CollUtil.isNotEmpty(keys)) {
            // 去掉缓存key的common prefix前缀
            assert keys != null;
            return keys.stream().map(key -> StrUtil.removePrefix(key, getCommonKeyPrefix())).collect(Collectors.toSet());
        } else {
            return CollUtil.newHashSet();
        }
    }

    @Override
    public Collection<T> getAllValues() {
        Set<String> keys = redisTemplate.keys(getCommonKeyPrefix() + "*");
        if (CollUtil.isNotEmpty(keys)) {
            assert keys != null;
            return redisTemplate.opsForValue().multiGet(keys);
        } else {
            return CollUtil.newArrayList();
        }
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

    /**
     * 获取RedisTemplate
     */
    public RedisTemplate<String, T> getRedisTemplate() {
        return this.redisTemplate;
    }

}
