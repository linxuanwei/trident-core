package x.trident.core.security.request.encrypt.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.security.request.encrypt.constants.EncryptionConstants;

/**
 * 请求解密，响应加密 异常
 *
 * @author luojie
 * @date 2021/3/23 12:54
 */
public class EncryptionException extends ServiceException {

    public EncryptionException(AbstractExceptionEnum exception, Object... params) {
        super(EncryptionConstants.ENCRYPTION_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public EncryptionException(AbstractExceptionEnum exception) {
        super(EncryptionConstants.ENCRYPTION_MODULE_NAME, exception);
    }

}
