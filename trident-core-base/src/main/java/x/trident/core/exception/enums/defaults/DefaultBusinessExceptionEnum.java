package x.trident.core.exception.enums.defaults;

import lombok.Getter;
import x.trident.core.exception.AbstractExceptionEnum;

import static x.trident.core.constants.BaseConstants.BUSINESS_ERROR_TYPE_CODE;
import static x.trident.core.constants.BaseConstants.FIRST_LEVEL_WIDE_CODE;

/**
 * 系统执行出错，业务本身逻辑问题导致的错误（一级宏观码）
 *
 * @author 林选伟
 * @date 2020/10/15 17:18
 */
@Getter
public enum DefaultBusinessExceptionEnum implements AbstractExceptionEnum {

    /**
     * 系统执行出错（一级宏观错误码）
     */
    SYSTEM_RUNTIME_ERROR(BUSINESS_ERROR_TYPE_CODE + FIRST_LEVEL_WIDE_CODE, "系统执行出错，请检查系统运行状况");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DefaultBusinessExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
