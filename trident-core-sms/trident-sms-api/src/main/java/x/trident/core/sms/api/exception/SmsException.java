
package x.trident.core.sms.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.sms.api.constants.SmsConstants;

/**
 * 短信发送的异常
 *
 * @author 林选伟
 * @date 2020/10/26 16:53
 */
public class SmsException extends ServiceException {

    public SmsException(AbstractExceptionEnum exception) {
        super(SmsConstants.SMS_MODULE_NAME, exception);
    }

    public SmsException(AbstractExceptionEnum exception, Object... params) {
        super(SmsConstants.SMS_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
