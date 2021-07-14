package x.trident.core.scanner.starter;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.scanner.ApiResourceScanner;
import x.trident.core.scanner.DefaultResourceCollector;
import x.trident.core.scanner.api.ResourceCollectorApi;
import x.trident.core.scanner.api.pojo.scanner.ScannerProperties;

/**
 * 资源的自动配置
 *
 * @author 林选伟
 * @date 2020/12/1 17:24
 */
@Configuration
public class TridentResourceAutoConfiguration {

    public static final String SCANNER_PREFIX = "scanner";

    @Value("${spring.application.name:}")
    private String springApplicationName;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    /**
     * 资源扫描器的配置
     */
    @Bean
    @ConfigurationProperties(prefix = SCANNER_PREFIX)
    public ScannerProperties scannerProperties() {
        return new ScannerProperties();
    }

    /**
     * 资源扫描器
     */
    @Bean
    @ConditionalOnMissingBean(ApiResourceScanner.class)
    @ConditionalOnProperty(prefix = TridentResourceAutoConfiguration.SCANNER_PREFIX, name = "open", havingValue = "true")
    public ApiResourceScanner apiResourceScanner(ResourceCollectorApi resourceCollectorApi, ScannerProperties scannerProperties) {
        if (StrUtil.isBlank(scannerProperties.getAppCode())) {
            scannerProperties.setAppCode(springApplicationName);
        }
        if (StrUtil.isBlank(scannerProperties.getContextPath())) {
            scannerProperties.setContextPath(contextPath);
        }
        return new ApiResourceScanner(resourceCollectorApi, scannerProperties);
    }

    /**
     * 资源搜集器
     */
    @Bean
    @ConditionalOnMissingBean(ResourceCollectorApi.class)
    @ConditionalOnProperty(prefix = TridentResourceAutoConfiguration.SCANNER_PREFIX, name = "open", havingValue = "true")
    public ResourceCollectorApi resourceCollectorApi() {
        return new DefaultResourceCollector();
    }

}
