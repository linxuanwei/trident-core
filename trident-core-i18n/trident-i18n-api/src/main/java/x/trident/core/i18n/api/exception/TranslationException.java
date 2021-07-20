
package x.trident.core.i18n.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.i18n.api.constants.TranslationConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;

/**
 * 多语言翻译的异常
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class TranslationException extends ServiceException {

    public TranslationException(AbstractExceptionEnum exception, Object... params) {
        super(TranslationConstants.I18N_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public TranslationException(AbstractExceptionEnum exception) {
        super(TranslationConstants.I18N_MODULE_NAME, exception);
    }

}
