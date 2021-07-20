
package x.trident.core.security.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.security.api.constants.SecurityConstants;

/**
 * XSS过滤异常
 *
 * @author 林选伟
 * @date 2021/1/13 23:22
 */
public class XssFilterException extends ServiceException {

    public XssFilterException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public XssFilterException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
