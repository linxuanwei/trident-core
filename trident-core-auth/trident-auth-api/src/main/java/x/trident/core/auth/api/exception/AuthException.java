package x.trident.core.auth.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.auth.api.constants.AuthConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;


/**
 * 认证类异常
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class AuthException extends ServiceException {

    public AuthException(AbstractExceptionEnum exception, Object... params) {
        super(AuthConstants.AUTH_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public AuthException(AbstractExceptionEnum exception) {
        super(AuthConstants.AUTH_MODULE_NAME, exception);
    }

}
