
package x.trident.core.message.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.message.api.constants.MessageConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;

/**
 * 消息异常枚举
 *
 * @author 林选伟
 * @date 2021/1/1 20:55
 */
public class MessageException extends ServiceException {

    public MessageException(AbstractExceptionEnum exception, Object... params) {
        super(MessageConstants.MESSAGE_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public MessageException(AbstractExceptionEnum exception) {
        super(MessageConstants.MESSAGE_MODULE_NAME, exception);
    }

}
