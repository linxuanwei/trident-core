package x.trident.core.db.api.constants;

/**
 * 数据库模块的常量
 *
 * @author 林选伟
 * @date 2020/10/16 11:05
 */
public class DbConstants {
    private DbConstants() {
    }

    /**
     * db模块的名称
     */
    public static final String DB_MODULE_NAME = "trident-core-db";

    /**
     * 异常枚举的步进值
     */
    public static final String DB_EXCEPTION_STEP_CODE = "02";

    /**
     * druid默认servlet的映射url
     */
    public static final String DEFAULT_DRUID_URL_MAPPINGS = "/druid/*";

    /**
     * druid控制台账号
     */
    public static final String DEFAULT_DRUID_ADMIN_ACCOUNT = "admin";

    /**
     * druid控制台的监控数据默认不能清空
     */
    public static final String DEFAULT_DRUID_ADMIN_RESET_ENABLE = "false";

    /**
     * druid web url统计的拦截范围
     */
    public static final String DRUID_WEB_STAT_FILTER_URL_PATTERN = "/*";

    /**
     * druid web url统计的排除拦截表达式
     */
    public static final String DRUID_WEB_STAT_FILTER_EXCLUSIONS = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";

    /**
     * druid web url统计的session统计开关
     */
    public static final String DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE = "false";

    /**
     * druid web url统计的session名称
     */
    public static final String DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME = "";

    /**
     * druid web url统计的session最大监控数
     */
    public static final String DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT = "1000";

    /**
     * druid web url统计的cookie名称
     */
    public static final String DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME = "";

    /**
     * druid web url统计的 是否开启监控单个url调用的sql列表
     */
    public static final String DRUID_WEB_STAT_FILTER_PROFILE_ENABLE = "true";

}
