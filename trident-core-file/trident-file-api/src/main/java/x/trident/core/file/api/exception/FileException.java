
package x.trident.core.file.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.file.api.constants.FileConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class FileException extends ServiceException {

    public FileException(AbstractExceptionEnum exception) {
        super(FileConstants.FILE_MODULE_NAME, exception);
    }

    public FileException(AbstractExceptionEnum exception, Object... params) {
        super(FileConstants.FILE_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
