package x.trident.core.exception.enums.defaults;

import lombok.Getter;
import x.trident.core.exception.AbstractExceptionEnum;

import static x.trident.core.constants.BaseConstants.FIRST_LEVEL_WIDE_CODE;
import static x.trident.core.constants.BaseConstants.THIRD_ERROR_TYPE_CODE;

/**
 * 表示错误来源于第三方服务，比如 CDN 服务出错，消息投递超时等问题
 *
 * @author 林选伟
 * @date 2020/10/15 17:31
 */
@Getter
public enum DefaultThirdExceptionEnum implements AbstractExceptionEnum {

    /**
     * 调用第三方服务出错（一级宏观错误码）
     */
    THIRD_PARTY_ERROR(THIRD_ERROR_TYPE_CODE + FIRST_LEVEL_WIDE_CODE, "第三方调用出现错误");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DefaultThirdExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
