
package x.trident.core.security.api.exception.enums;

import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.security.api.constants.SecurityConstants;
import lombok.Getter;

/**
 * 安全模块异常枚举
 *
 * @author 林选伟
 * @date 2021/2/19 8:46
 */
@Getter
public enum SecurityExceptionEnum implements AbstractExceptionEnum {

    /**
     * xxx
     */
    SECURITY_EXPIRED_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + SecurityConstants.SECURITY_EXCEPTION_STEP_CODE + "01", "安全模块异常");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SecurityExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
