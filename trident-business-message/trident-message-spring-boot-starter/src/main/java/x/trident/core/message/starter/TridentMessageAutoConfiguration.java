
package x.trident.core.message.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 系统消息的自动配置
 *
 * @author 林选伟
 * @date 2020/12/31 18:50
 */
@Configuration
public class TridentMessageAutoConfiguration {

    public static final String WEB_SOCKET_PREFIX = "web-socket";

    /**
     * 开启WebSocket功能
     *
     * @return serverEndpointExporter
     * @author 林选伟
     * @date 2021/01/24 22:09
     */
    @Bean
    @ConditionalOnMissingBean(ServerEndpointExporter.class)
    @ConditionalOnProperty(prefix = WEB_SOCKET_PREFIX, name = "open", havingValue = "true")
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
