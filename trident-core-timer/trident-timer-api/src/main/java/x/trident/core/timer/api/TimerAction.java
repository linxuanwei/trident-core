package x.trident.core.timer.api;

/**
 * 定时器执行内容的一个基类，任务内容都要实现这个接口
 * <p>
 * Guns中定时器都要实现本接口，并需要把实现类加入到spring容器中
 *
 * @author 林选伟
 * @date 2020/10/27 11:53
 */
public interface TimerAction {

    /**
     * 任务执行的具体内容
     *
     * @param params 任务参数
     */
    void action(String params);

}
