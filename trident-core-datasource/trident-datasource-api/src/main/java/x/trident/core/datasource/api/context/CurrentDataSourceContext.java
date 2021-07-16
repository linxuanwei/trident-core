
package x.trident.core.datasource.api.context;

/**
 * 利用ThreadLocal缓存当前请求的数据源
 *
 * @author 林选伟
 * @date 2020/10/31 22:58
 */
public class CurrentDataSourceContext {
    private CurrentDataSourceContext() {
    }

    private static final ThreadLocal<String> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源类型
     *
     * @param dataSourceName 数据库类型
     */
    public static void setDataSourceName(String dataSourceName) {
        DATASOURCE_CONTEXT_HOLDER.set(dataSourceName);
    }

    /**
     * 获取数据源类型
     */
    public static String getDataSourceName() {
        return DATASOURCE_CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDataSourceName() {
        DATASOURCE_CONTEXT_HOLDER.remove();
    }

}
