package x.trident.core.scanner.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.scanner.api.constants.ScannerConstants;

/**
 * 资源模块的异常
 *
 * @author 林选伟
 * @date 2020/11/3 13:54
 */
public class ScannerException extends ServiceException {

    public ScannerException(AbstractExceptionEnum exception) {
        super(ScannerConstants.RESOURCE_MODULE_NAME, exception);
    }

    public ScannerException(AbstractExceptionEnum exception, Object... params) {
        super(ScannerConstants.RESOURCE_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
