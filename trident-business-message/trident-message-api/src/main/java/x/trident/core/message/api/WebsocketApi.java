
package x.trident.core.message.api;

import x.trident.core.message.api.pojo.request.MessageSendRequest;

import java.util.List;

/**
 * 系统消息websocket相关接口
 *
 * @author 林选伟
 * @date 2021/1/26 18:14
 */
public interface WebsocketApi {

    /**
     * 发送websocket系统消息
     *
     * @param userIdList userId 集合
     * @param messageSendRequest 系统消息参数
     * @author 林选伟
     * @date 2021/1/26 18:17
     */
    void sendWebSocketMessage(List<Long> userIdList, MessageSendRequest messageSendRequest);

}
