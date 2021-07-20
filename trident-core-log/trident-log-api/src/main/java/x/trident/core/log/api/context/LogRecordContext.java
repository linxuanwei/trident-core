
package x.trident.core.log.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.log.api.LogRecordApi;

/**
 * 日志操作api的获取
 *
 * @author 林选伟
 * @date 2020/10/27 16:19
 */
public class LogRecordContext {
    private LogRecordContext() {
    }

    /**
     * 获取日志操作api
     */
    public static LogRecordApi me() {
        return SpringUtil.getBean(LogRecordApi.class);
    }

}
