
package x.trident.core.jwt.api.exception.enums;

import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.jwt.api.constants.JwtConstants;

/**
 * jwt异常的状态码
 *
 * @author 林选伟
 * @date 2020/10/16 10:53
 */
@Getter
public enum JwtExceptionEnum implements AbstractExceptionEnum {

    /**
     * jwt解析异常
     */
    JWT_PARSE_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + JwtConstants.JWT_EXCEPTION_STEP_CODE + "01", "jwt解析错误！jwt为：{}"),

    /**
     * jwt过期了
     */
    JWT_EXPIRED_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + JwtConstants.JWT_EXCEPTION_STEP_CODE + "02", "jwt过期了！jwt为：{}"),

    /**
     * jwt参数为空
     */
    JWT_PARAM_EMPTY(BaseConstants.BUSINESS_ERROR_TYPE_CODE + JwtConstants.JWT_EXCEPTION_STEP_CODE + "03", "jwt解析时，秘钥或过期时间为空");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    JwtExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
