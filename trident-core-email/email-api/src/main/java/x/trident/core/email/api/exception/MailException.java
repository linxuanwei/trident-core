
package x.trident.core.email.api.exception;

import lombok.Getter;
import x.trident.core.email.api.constants.MailConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;

/**
 * 邮件发送异常
 *
 * @author 林选伟
 * @date 2021-07-06-下午3:00
 */
@Getter
public class MailException extends ServiceException {

    public MailException(String errorCode, String userTip) {
        super(MailConstants.MAIL_MODULE_NAME, errorCode, userTip);
    }

    public MailException(AbstractExceptionEnum exceptionEnum, String userTip) {
        super(MailConstants.MAIL_MODULE_NAME, exceptionEnum.getErrorCode(), userTip);
    }

    public MailException(AbstractExceptionEnum exceptionEnum) {
        super(MailConstants.MAIL_MODULE_NAME, exceptionEnum);
    }

}
