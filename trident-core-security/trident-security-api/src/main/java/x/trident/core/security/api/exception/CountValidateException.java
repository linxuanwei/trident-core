
package x.trident.core.security.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.security.api.constants.SecurityConstants;

/**
 * 计数器校验异常
 *
 * @author 林选伟
 * @date 2020/11/14 17:53
 */
public class CountValidateException extends ServiceException {

    public CountValidateException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public CountValidateException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
