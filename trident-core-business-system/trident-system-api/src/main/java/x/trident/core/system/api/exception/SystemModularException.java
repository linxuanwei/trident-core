
package x.trident.core.system.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.system.api.constants.SystemConstants;

/**
 * 系统管理模块的异常
 *
 * @author 林选伟
 * @date 2020/11/4 15:50
 */
public class SystemModularException extends ServiceException {

    public SystemModularException(AbstractExceptionEnum exception, Object... params) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public SystemModularException(AbstractExceptionEnum exception) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception);
    }

}
