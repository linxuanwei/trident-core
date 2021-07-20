
package x.trident.core.office.api.exception;

import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.office.api.constants.OfficeConstants;


/**
 * Office模块异常
 *
 * @author 林选伟
 * @date 2020/11/4 10:15
 */
public class OfficeException extends ServiceException {

    public OfficeException(AbstractExceptionEnum exception) {
        super(OfficeConstants.OFFICE_MODULE_NAME, exception);
    }

    public OfficeException(String errorCode, String userTip) {
        super(OfficeConstants.OFFICE_MODULE_NAME, errorCode, userTip);
    }

}
