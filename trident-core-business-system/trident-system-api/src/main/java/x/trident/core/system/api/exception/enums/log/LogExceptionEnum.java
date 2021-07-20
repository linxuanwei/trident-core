
package x.trident.core.system.api.exception.enums.log;

import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 日志相关异常枚举
 *
 * @author 林选伟
 * @date 2021/2/8 17:45
 */
@Getter
public enum LogExceptionEnum implements AbstractExceptionEnum {

    /**
     * 日志不存在
     */
    LOG_NOT_EXIST(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "21", "日志不存在");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    LogExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
