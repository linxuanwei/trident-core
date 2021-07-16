package x.trident.core.timer.api.exception.enums;


import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.timer.api.constants.TimerConstants;
import lombok.Getter;

/**
 * 定时任务的异常枚举
 *
 * @author 林选伟
 * @date 2020/10/27 13:55
 */
@Getter
public enum TimerExceptionEnum implements AbstractExceptionEnum {

    /**
     * 定时任务参数为空
     */
    PARAM_HAS_NULL(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + TimerConstants.TIMER_EXCEPTION_STEP_CODE + "01", "定时任务参数为空，具体为空参数为：{}"),

    /**
     * 具体定时任务类找不到
     */
    CLASS_NOT_FOUND(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + TimerConstants.TIMER_EXCEPTION_STEP_CODE + "02", "定时任务类获取不到，请检查系统中是否有该类，具体类为：{}"),

    /**
     * 获取不到定时任务详情
     */
    JOB_DETAIL_NOT_FOUND(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + TimerConstants.TIMER_EXCEPTION_STEP_CODE + "03", "定时任务详情获取不到，定时任务id为：{}");


    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    TimerExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
