
package x.trident.core.log.api.threadpool;

import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 异步记录日志用的线程池
 *
 * @author 林选伟
 * @date 2020/10/28 15:24
 */
public class LogManagerThreadPool {

    /**
     * 异步操作记录日志的线程池
     */
    private final ScheduledThreadPoolExecutor schedule;

    public LogManagerThreadPool() {
        schedule = new ScheduledThreadPoolExecutor(10, new ScheduledExecutorFactoryBean());
    }

    public LogManagerThreadPool(int poolSize) {
        schedule = new ScheduledThreadPoolExecutor(poolSize, new ScheduledExecutorFactoryBean());
    }

    /**
     * 异步执行日志的方法
     */
    public void executeLog(TimerTask task) {
        int operateDelayTime = 10;
        schedule.schedule(task, operateDelayTime, TimeUnit.MILLISECONDS);
    }

}
