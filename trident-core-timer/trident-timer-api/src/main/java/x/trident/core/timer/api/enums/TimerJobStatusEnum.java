package x.trident.core.timer.api.enums;

import lombok.Getter;

/**
 * 定时任务的状态
 *
 * @author linxuanwei
 * @date 2020/6/30 20:44
 */
@Getter
public enum TimerJobStatusEnum {

    /**
     * 启动状态
     */
    RUNNING(1),

    /**
     * 停止状态
     */
    STOP(2);

    private final Integer code;

    TimerJobStatusEnum(int code) {
        this.code = code;
    }

}
