
package x.trident.core.system.starter.listener;

import x.trident.core.listener.ApplicationReadyListener;
import x.trident.core.system.api.constants.SystemConstants;
import x.trident.core.system.starter.init.InitAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 项目启动后初始化超级管理员
 *
 * @author 林选伟
 * @date 2020/12/17 21:44
 */
@Component
@Slf4j
public class SuperAdminInitListener extends ApplicationReadyListener implements Ordered {

    @Resource
    private InitAdminService initAdminService;

    @Override
    public void eventCallback(ApplicationReadyEvent event) {
        initAdminService.initSuperAdmin();
    }

    @Override
    public int getOrder() {
        return SystemConstants.SUPER_ADMIN_INIT_LISTENER_SORT;
    }

}
