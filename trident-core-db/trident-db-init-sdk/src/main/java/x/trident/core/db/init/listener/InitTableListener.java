
package x.trident.core.db.init.listener;

import x.trident.core.db.init.actuator.DbInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.Ordered;
import x.trident.core.listener.ApplicationReadyListener;

import java.util.Map;

/**
 * 初始化 创建字典表
 *
 * @author wangzhongqiang
 * @date 2018/4/23 9:57
 */
@Slf4j
public class InitTableListener extends ApplicationReadyListener implements Ordered {

    @Override
    public void eventCallback(ApplicationReadyEvent event) {
        Map<String, DbInitializer> beansOfType = event.getApplicationContext().getBeansOfType(DbInitializer.class);
        for (Map.Entry<String, DbInitializer> entry : beansOfType.entrySet()) {
            DbInitializer value = entry.getValue();
            value.dbInit();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 200;
    }

}
