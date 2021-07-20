
package x.trident.core.security.count;

import cn.hutool.core.convert.Convert;
import x.trident.core.cache.api.CacheOperatorApi;
import x.trident.core.cache.api.constants.CacheConstants;
import x.trident.core.security.api.CountValidatorApi;
import x.trident.core.security.api.constants.CounterConstants;
import x.trident.core.security.api.exception.CountValidateException;
import x.trident.core.security.api.exception.enums.CountValidateExceptionEnum;

/**
 * 默认的计数校验器
 *
 * @author 林选伟
 * @date 2020/11/15 12:14
 */
public class DefaultCountValidator implements CountValidatorApi {

    private final CacheOperatorApi<Long> cacheOperatorApi;

    public DefaultCountValidator(CacheOperatorApi<Long> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    @Override
    public synchronized void countAndValidate(String key, Long timeWindowSeconds, Long timeWindowMaxCount) throws CountValidateException {

        // 获取当前时间的秒数
        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        // 上一次操作时间秒数的缓存key COUNT_VALIDATE:key:RECORD_SECONDS
        String recordTimeSecondsKey = CounterConstants.COUNT_VALIDATE_CACHE_KEY_PREFIX + CacheConstants.CACHE_DELIMITER + key + CacheConstants.CACHE_DELIMITER + CounterConstants.RECORD_TIME_SECONDS;

        // 上一次执行次数的记录缓存key COUNT_VALIDATE:key:COUNT_NUMBER
        String countNumberKey = CounterConstants.COUNT_VALIDATE_CACHE_KEY_PREFIX + CacheConstants.CACHE_DELIMITER + key + CacheConstants.CACHE_DELIMITER + CounterConstants.COUNT_NUMBER;

        // 获取缓存中上一次操作时间秒数
        Object recordTimeSecondsObject = cacheOperatorApi.get(recordTimeSecondsKey);
        Long recordTimeSeconds = Convert.toLong(recordTimeSecondsObject);
        if (recordTimeSeconds == null) {
            recordTimeSeconds = currentTimeSeconds;
        }

        // 获取缓存中上一次执行次数的记录
        Object countNumberObject = cacheOperatorApi.get(countNumberKey);
        Long countNumber = Convert.toLong(countNumberObject);
        if (countNumber == null) {
            countNumber = 0L;
        }

        // 当前时间和记录时间的差 超过限制的时间段就归零计数，否则就直接加1
        if ((currentTimeSeconds - recordTimeSeconds) == timeWindowSeconds) {
            countNumber = 0L;
        } else if ((currentTimeSeconds - recordTimeSeconds) > timeWindowSeconds) {
            countNumber = 0L;
        } else if ((currentTimeSeconds - recordTimeSeconds) < timeWindowSeconds) {
            countNumber = countNumber + 1;
        } else if (recordTimeSeconds.equals(currentTimeSeconds)) {
            countNumber = countNumber + 1;
        }

        cacheOperatorApi.put(recordTimeSecondsKey, currentTimeSeconds, timeWindowSeconds);
        cacheOperatorApi.put(countNumberKey, countNumber, timeWindowSeconds);

        // 如果记录次数大于了时间窗内的最大容许值，则抛出异常
        if (countNumber > timeWindowMaxCount) {
            throw new CountValidateException(CountValidateExceptionEnum.INTERRUPT_EXECUTION);
        }

    }

}
