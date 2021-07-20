
package x.trident.core.pinyin.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.pinyin.api.constants.PinyinConstants;
import lombok.Getter;

/**
 * 拼音异常
 *
 * @author 林选伟
 * @date 2020/12/3 18:10
 */
@Getter
public class PinyinException extends ServiceException {

    public PinyinException(AbstractExceptionEnum exceptionEnum) {
        super(PinyinConstants.PINYIN_MODULE_NAME, exceptionEnum);
    }

    public PinyinException(AbstractExceptionEnum exception, Object... params) {
        super(PinyinConstants.PINYIN_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
