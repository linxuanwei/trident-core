
package x.trident.core.log.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.log.api.constants.LogConstants;


/**
 * 日志异常枚举
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class LogException extends ServiceException {

    public LogException(AbstractExceptionEnum exception, Object... params) {
        super(LogConstants.LOG_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public LogException(AbstractExceptionEnum exception) {
        super(LogConstants.LOG_MODULE_NAME, exception);
    }

}
