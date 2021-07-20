
package x.trident.core.security.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.security.api.constants.SecurityConstants;

/**
 * 安全模块异常
 *
 * @author 林选伟
 * @date 2021/2/19 8:48
 */
public class SecurityException extends ServiceException {

    public SecurityException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public SecurityException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
