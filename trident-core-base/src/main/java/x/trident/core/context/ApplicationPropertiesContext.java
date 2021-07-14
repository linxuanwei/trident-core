package x.trident.core.context;

import lombok.Getter;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * application.yml或application.properties配置的快速获取
 * 此类的使用必须激活 ConfigInitListener
 *
 * @author 林选伟
 * @date 2021/2/26 18:27
 */
@Getter
public class ApplicationPropertiesContext {

    private static final ApplicationPropertiesContext APPLICATION_PROPERTIES_CONTEXT = new ApplicationPropertiesContext();

    private String applicationName = null;
    private String contextPath = null;
    private String env = null;

    private ApplicationPropertiesContext() {
    }

    /**
     * 初始化yml参数
     *
     * @param configurableEnvironment yml配置
     */
    public void initConfigs(ConfigurableEnvironment configurableEnvironment) {
        applicationName = configurableEnvironment.getProperty("spring.application.name");
        contextPath = configurableEnvironment.getProperty("server.servlet.context-path");
        env = configurableEnvironment.getProperty("spring.profiles.active");
    }

    /**
     * 单例入口
     *
     * @return instance
     */
    public static ApplicationPropertiesContext getInstance() {
        return APPLICATION_PROPERTIES_CONTEXT;
    }

}
