
package x.trident.core.log.api.constants;

/**
 * log模块的常量
 *
 * @author 林选伟
 * @date 2020/10/27 15:56
 */
public class LogConstants {
    private LogConstants() {
    }

    /**
     * 日志模块的名称
     */
    public static final String LOG_MODULE_NAME = "trident-core-log";

    /**
     * 异常枚举的步进值
     */
    public static final String LOG_EXCEPTION_STEP_CODE = "12";

    /**
     * 默认日志的名称
     */
    public static final String LOG_DEFAULT_NAME = "API接口日志记录";

    /**
     * 默认日志服务名称
     */
    public static final String LOG_DEFAULT_APP_NAME = "none-app-name";

    /**
     * 默认查询日志分页
     */
    public static final Integer DEFAULT_BEGIN_PAGE_NO = 1;

    /**
     * 默认查询日志分页大小
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

}
