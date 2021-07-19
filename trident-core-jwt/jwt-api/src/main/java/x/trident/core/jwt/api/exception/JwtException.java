
package x.trident.core.jwt.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.jwt.api.constants.JwtConstants;


/**
 * jwt异常
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class JwtException extends ServiceException {

    public JwtException(AbstractExceptionEnum exception, Object... params) {
        super(JwtConstants.JWT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public JwtException(AbstractExceptionEnum exception) {
        super(JwtConstants.JWT_MODULE_NAME, exception);
    }

}
