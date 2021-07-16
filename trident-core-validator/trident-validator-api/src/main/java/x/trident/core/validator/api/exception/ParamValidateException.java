
package x.trident.core.validator.api.exception;

import cn.hutool.core.util.StrUtil;

import x.trident.core.validator.api.constants.ValidatorConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;

/**
 * 参数校验异常
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class ParamValidateException extends ServiceException {

    public ParamValidateException(AbstractExceptionEnum exception, Object... params) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public ParamValidateException(AbstractExceptionEnum exception) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception);
    }

}
