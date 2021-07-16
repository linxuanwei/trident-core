package x.trident.core.timer.api.exception;

import cn.hutool.core.util.StrUtil;

import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.timer.api.constants.TimerConstants;

/**
 * 定时器任务的异常
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class TimerException extends ServiceException {

    public TimerException(AbstractExceptionEnum exception) {
        super(TimerConstants.TIMER_MODULE_NAME, exception);
    }

    public TimerException(AbstractExceptionEnum exception, Object... params) {
        super(TimerConstants.TIMER_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
