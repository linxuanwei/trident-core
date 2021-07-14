package x.trident.core.scanner.api.constants;

/**
 * 资源扫描模块的常量
 *
 * @author 林选伟
 * @date 2020/11/3 13:50
 */
public class ScannerConstants {
    private ScannerConstants() {
    }

    /**
     * 资源模块的名称
     */
    public static final String RESOURCE_MODULE_NAME = "trident-core-scanner";

    /**
     * 异常枚举的步进值
     */
    public static final String RESOURCE_EXCEPTION_STEP_CODE = "17";

    /**
     * 资源前缀标识
     */
    public static final String RESOURCE_CACHE_KEY = "TRIDENT_RESOURCE_CACHES";

    /**
     * 资源汇报的监听器的顺序
     */
    public static final Integer REPORT_RESOURCE_LISTENER_SORT = 200;

    /**
     * 视图类型的控制器url路径开头
     */
    public static final String VIEW_CONTROLLER_PATH_START_WITH = "/view";

}
