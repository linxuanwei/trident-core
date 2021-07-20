
package x.trident.core.log.api.exception.enums;

import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.log.api.constants.LogConstants;

/**
 * 日志异常枚举
 *
 * @author 林选伟
 * @date 2020/10/27 16:18
 */
@Getter
public enum LogExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询或者删除日志时，传入的参数中没有app名称
     */
    APP_NAME_NOT_EXIST(BaseConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "01", "应用名称不能为空！"),

    /**
     * 查询或者删除日志时，传入的参数中没有查询时间
     */
    BEGIN_DATETIME_NOT_EXIST(BaseConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "02", "开始时间不能为空,请填写精确到日的时间！"),

    /**
     * 查询或者删除日志时，传入的参数中没有查询时间
     */
    END_DATETIME_NOT_EXIST(BaseConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "03", "结束时间不能为空,请填写精确到日的时间！"),

    /**
     * 初始化日志记录表失败，执行查询语句失败
     */
    LOG_SQL_EXE_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "04", "初始化日志记录表失败，执行查询语句失败"),

    /**
     * 被查询日志不存在
     */
    LOG_NOT_EXISTED(BaseConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "05", "被查询日志不存在，日志id：{}");

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
