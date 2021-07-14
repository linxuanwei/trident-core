package x.trident.core.scanner;

import x.trident.core.listener.ApplicationReadyListener;
import x.trident.core.scanner.api.ResourceCollectorApi;
import x.trident.core.scanner.api.ResourceReportApi;
import x.trident.core.scanner.api.constants.ScannerConstants;
import x.trident.core.scanner.api.holder.InitScanFlagHolder;
import x.trident.core.scanner.api.pojo.resource.ReportResourceParam;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import x.trident.core.scanner.api.pojo.scanner.ScannerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * 监听项目初始化完毕，汇报资源到服务（可为远程服务，可为本服务）
 *
 * @author 林选伟
 * @date 2020/10/19 22:27
 */
@Slf4j
public class ResourceReportListener extends ApplicationReadyListener implements Ordered {

    @Override
    public void eventCallback(ApplicationReadyEvent event) {

        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        // 获取有没有开资源扫描开关
        ScannerProperties scannerProperties = applicationContext.getBean(ScannerProperties.class);
        if (!scannerProperties.getOpen()) {
            return;
        }

        // 如果项目还没进行资源扫描
        if (!InitScanFlagHolder.getFlag()) {

            // 获取当前系统的所有资源
            ResourceCollectorApi resourceCollectorApi = applicationContext.getBean(ResourceCollectorApi.class);
            Map<String, Map<String, ResourceDefinition>> modularResources = resourceCollectorApi.getModularResources();

            // 持久化资源，发送资源到资源服务或本项目（单体项目）
            ResourceReportApi resourceService = applicationContext.getBean(ResourceReportApi.class);
            resourceService.reportResources(new ReportResourceParam(scannerProperties.getAppCode(), modularResources));

            // 设置标识已经扫描过
            InitScanFlagHolder.setFlag();
        }

    }

    @Override
    public int getOrder() {
        return ScannerConstants.REPORT_RESOURCE_LISTENER_SORT;
    }

}
