package x.trident.core.cache.api.constants;

/**
 * 缓存模块的常量
 *
 * @author linxuanwei
 * @date 2020/10/22 16:55
 */
public class CacheConstants {
    private CacheConstants() {
    }

    /**
     * 缓存模块的名称
     */
    public static final String CACHE_MODULE_NAME = "trident-core-cache";

    /**
     * 缓存模块的异常步进值
     */
    public static final String CACHE_EXCEPTION_STEP_CODE = "07";

    /**
     * 缓存的分割符号
     */
    public static final String CACHE_DELIMITER = ":";

    /**
     * 给hutools缓存用的无限过期时间
     */
    public static final Long NONE_EXPIRED_TIME = 1000L * 3600 * 24 * 999;

    /**
     * 默认缓存的过期时间，10分钟
     */
    public static final Long DEFAULT_CACHE_TIMEOUT = 1000L * 60 * 10;

    /**
     * 默认object对象缓存的缓存前缀
     */
    public static final String DEFAULT_OBJECT_CACHE_PREFIX = "DEFAULT:OBJECTS:";

    /**
     * 默认String对象缓存的缓存前缀
     */
    public static final String DEFAULT_STRING_CACHE_PREFIX = "DEFAULT:STRINGS:";

}
