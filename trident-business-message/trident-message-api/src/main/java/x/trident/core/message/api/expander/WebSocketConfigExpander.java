
package x.trident.core.message.api.expander;

import x.trident.core.config.api.context.ConfigContext;
import x.trident.core.message.api.constants.MessageConstants;

/**
 * websocket相关配置快速获取
 *
 * @author 林选伟
 * @date 2021/1/25 20:05
 */
public class WebSocketConfigExpander {

    /**
     * 获取websocket的ws-url
     *
     * @author 林选伟
     * @date 2021/1/25 20:34
     */
    public static String getWebSocketWsUrl() {
        return ConfigContext.me().getSysConfigValueWithDefault("WEB_SOCKET_WS_URL", String.class, MessageConstants.DEFAULT_WS_URL);
    }

}
