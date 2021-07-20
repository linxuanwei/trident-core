
package x.trident.core.wrapper.api.exception.enums;

import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.wrapper.api.constants.WrapperConstants;

/**
 * Wrapper异常的状态码
 *
 * @author 林选伟
 * @date 2021/1/19 22:24
 */
@Getter
public enum WrapperExceptionEnum implements AbstractExceptionEnum {

    /**
     * 被包装的值不能是基本类型
     */
    BASIC_TYPE_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + WrapperConstants.WRAPPER_EXCEPTION_STEP_CODE + "01", "被包装的值不能是基本类型"),

    /**
     * 字段包装转化异常
     */
    TRANSFER_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + WrapperConstants.WRAPPER_EXCEPTION_STEP_CODE + "02", "字段包装转化异常");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    WrapperExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
