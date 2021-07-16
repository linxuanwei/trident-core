package x.trident.core.config.api.exception;

import x.trident.core.config.api.constants.ConfigConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;


/**
 * 系统配置表的异常
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class ConfigException extends ServiceException {

    public ConfigException(String errorCode, String userTip) {
        super(ConfigConstants.CONFIG_MODULE_NAME, errorCode, userTip);
    }

    public ConfigException(AbstractExceptionEnum exception) {
        super(ConfigConstants.CONFIG_MODULE_NAME, exception);
    }

    public ConfigException(AbstractExceptionEnum exception, String userTip) {
        super(ConfigConstants.CONFIG_MODULE_NAME, exception.getErrorCode(), userTip);
    }

}
