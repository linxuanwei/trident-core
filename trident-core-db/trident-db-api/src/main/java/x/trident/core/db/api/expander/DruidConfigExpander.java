package x.trident.core.db.api.expander;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import x.trident.core.config.api.context.ConfigContext;
import x.trident.core.db.api.constants.DbConstants;

/**
 * Druid数据源的一些配置
 *
 * @author 林选伟
 * @date 2021/1/10 11:32
 */
@Slf4j
public class DruidConfigExpander {
    private DruidConfigExpander() {
    }

    /**
     * Druid监控界面的url映射
     */
    public static String getDruidUrlMappings() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_URL_MAPPINGS", String.class, DbConstants.DEFAULT_DRUID_URL_MAPPINGS);
    }

    /**
     * Druid控制台账号
     */
    public static String getDruidAdminAccount() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_ACCOUNT", String.class, DbConstants.DEFAULT_DRUID_ADMIN_ACCOUNT);
    }

    /**
     * Druid控制台账号密码
     */
    public static String getDruidAdminPassword() {
        String sysDruidPassword = ConfigContext.me().getConfigValueNullable("SYS_DRUID_PASSWORD", String.class);

        // 没配置就返回一个随机密码
        if (sysDruidPassword == null) {
            String randomString = RandomUtil.randomString(20);
            log.info("Druid密码未在系统配置表设置，Druid密码为：{}", randomString);
            return randomString;
        } else {
            return sysDruidPassword;
        }
    }

    /**
     * Druid控制台的监控数据是否可以重置清零
     *
     * @return true-可以重置，false-不可以
     */
    public static String getDruidAdminResetFlag() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_RESET_ENABLE", String.class, DbConstants.DEFAULT_DRUID_ADMIN_RESET_ENABLE);
    }

    /**
     * druid web url统计的拦截范围
     */
    public static String getDruidAdminWebStatFilterUrlPattern() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_URL_PATTERN", String.class, DbConstants.DRUID_WEB_STAT_FILTER_URL_PATTERN);
    }

    /**
     * druid web url统计的排除拦截表达式
     */
    public static String getDruidAdminWebStatFilterExclusions() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_EXCLUSIONS", String.class, DbConstants.DRUID_WEB_STAT_FILTER_EXCLUSIONS);
    }

    /**
     * druid web url统计的session统计开关
     */
    public static String getDruidAdminWebStatFilterSessionStatEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE", String.class, DbConstants.DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE);
    }

    /**
     * druid web url统计的session名称
     */
    public static String getDruidAdminWebStatFilterSessionName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME", String.class, DbConstants.DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME);
    }

    /**
     * druid web url统计的session最大监控数
     */
    public static String getDruidAdminWebStatFilterSessionStatMaxCount() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT", String.class, DbConstants.DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT);
    }

    /**
     * druid web url统计的cookie名称
     */
    public static String getDruidAdminWebStatFilterPrincipalCookieName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME", String.class, DbConstants.DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME);
    }

    /**
     * druid web url统计的是否开启监控单个url调用的sql列表
     */
    public static String getDruidAdminWebStatFilterProfileEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_PROFILE_ENABLE", String.class, DbConstants.DRUID_WEB_STAT_FILTER_PROFILE_ENABLE);
    }

}
