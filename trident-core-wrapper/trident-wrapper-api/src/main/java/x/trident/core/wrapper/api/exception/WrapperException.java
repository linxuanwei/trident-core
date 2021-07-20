
package x.trident.core.wrapper.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.wrapper.api.constants.WrapperConstants;

/**
 * Wrapper异常
 *
 * @author 林选伟
 * @date 2021/1/19 22:24
 */
public class WrapperException extends ServiceException {

    public WrapperException(AbstractExceptionEnum exception, Object... params) {
        super(WrapperConstants.WRAPPER_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public WrapperException(AbstractExceptionEnum exception) {
        super(WrapperConstants.WRAPPER_MODULE_NAME, exception);
    }

}
