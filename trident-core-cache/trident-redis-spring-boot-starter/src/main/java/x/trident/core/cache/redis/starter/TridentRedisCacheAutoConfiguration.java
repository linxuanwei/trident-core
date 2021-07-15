package x.trident.core.cache.redis.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import x.trident.core.cache.redis.operator.DefaultRedisCacheOperator;
import x.trident.core.cache.redis.operator.DefaultStringRedisCacheOperator;
import x.trident.core.cache.redis.serializer.FastJson2JsonRedisSerializer;
import x.trident.core.cache.redis.util.CreateRedisTemplateUtil;

/**
 * 基于redis缓存的默认配置，默认提供两个RedisTemplate工具类，其他的各个模块自行配置
 *
 * @author 林选伟
 * @date 2021/1/31 20:33
 */
@Configuration
public class TridentRedisCacheAutoConfiguration {

    /**
     * Redis的value序列化器
     */
    @Bean
    public RedisSerializer<?> fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<>(Object.class);
    }

    /**
     * value是object类型的redis操作类
     */
    @Bean
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return CreateRedisTemplateUtil.createObject(redisConnectionFactory);
    }

    /**
     * value是string类型的redis操作类
     */
    @Bean
    public RedisTemplate<String, String> tridentStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return CreateRedisTemplateUtil.createString(redisConnectionFactory);

    }

    /**
     * 创建默认的value是string类型的redis缓存
     */
    @Bean
    public DefaultStringRedisCacheOperator defaultStringRedisOperator(RedisTemplate<String, String> stringRedisTemplate) {
        return new DefaultStringRedisCacheOperator(stringRedisTemplate);
    }

    /**
     * 创建默认的value是object类型的redis缓存
     */
    @Bean
    public DefaultRedisCacheOperator defaultRedisOperator(RedisTemplate<String, Object> objectRedisTemplate) {
        return new DefaultRedisCacheOperator(objectRedisTemplate);
    }

}
