
package x.trident.core.dict.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.dict.api.constants.DictConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;

/**
 * 字典模块的异常
 *
 * @author 林选伟
 * @date 2020/10/29 11:57
 */
public class DictException extends ServiceException {

    public DictException(AbstractExceptionEnum exception, Object... params) {
        super(DictConstants.DICT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public DictException(AbstractExceptionEnum exception) {
        super(DictConstants.DICT_MODULE_NAME, exception);
    }

}
