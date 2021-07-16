
package x.trident.core.datasource.api.constants;

/**
 * 数据源容器的常量
 *
 * @author 林选伟
 * @date 2020/10/31 21:58
 */
public class DatasourceContainerConstants {
    private DatasourceContainerConstants() {
    }

    /**
     * db模块的名称
     */
    public static final String DS_CTN_MODULE_NAME = "trident-core-datasource";

    /**
     * 异常枚举的步进值
     */
    public static final String DS_CTN_EXCEPTION_STEP_CODE = "16";

    /**
     * 主数据源名称
     */
    public static final String MASTER_DATASOURCE_NAME = "master";

    /**
     * 多数据源切换的aop的顺序
     */
    public static final int MULTI_DATA_SOURCE_EXCHANGE_AOP = 1;

}
