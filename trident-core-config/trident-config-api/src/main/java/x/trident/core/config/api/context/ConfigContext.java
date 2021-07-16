package x.trident.core.config.api.context;

import x.trident.core.config.api.ConfigApi;
import x.trident.core.config.api.exception.ConfigException;

import static x.trident.core.config.api.exception.enums.ConfigExceptionEnum.CONFIG_CONTAINER_IS_NULL;

/**
 * 系统配置表相关的api
 * <p>
 * 系统配置表默认由数据库实现，可实现在线管理，也可以拓展redis等实现
 * <p>
 * 使用之前请调用setConfigApi初始化
 *
 * @author 林选伟
 * @date 2020/10/17 10:27
 */
public class ConfigContext {

    private ConfigContext() {
    }

    private static ConfigApi configApi = null;

    /**
     * 获取config操作接口
     */
    public static ConfigApi me() {
        if (configApi == null) {
            throw new ConfigException(CONFIG_CONTAINER_IS_NULL);
        }
        return configApi;
    }

    /**
     * 设置config api的实现
     */
    public static void setConfigApi(ConfigApi configApi) {
        ConfigContext.configApi = configApi;
    }

}
