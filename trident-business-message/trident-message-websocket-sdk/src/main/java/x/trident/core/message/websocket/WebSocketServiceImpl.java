
package x.trident.core.message.websocket;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.message.api.WebsocketApi;
import x.trident.core.message.api.enums.MessageReadFlagEnum;
import x.trident.core.message.api.pojo.request.MessageSendRequest;
import x.trident.core.message.api.pojo.response.MessageResponse;
import x.trident.core.message.websocket.manager.WebSocketManager;

import java.util.List;

/**
 * 系统消息websocket
 *
 * @author 林选伟
 * @date 2021/1/2 22:00
 */
@Slf4j
@Service
public class WebSocketServiceImpl implements WebsocketApi {


    public final static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void sendWebSocketMessage(List<Long> userIdList, MessageSendRequest messageSendRequest) {
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        try {
            MessageResponse sysMessage = new MessageResponse();
            BeanUtil.copyProperties(messageSendRequest, sysMessage);
            sysMessage.setReadFlag(MessageReadFlagEnum.UNREAD.getCode());
            sysMessage.setSendUserId(loginUser.getUid());
            String msgInfo = MAPPER.writeValueAsString(sysMessage);

            for (Long userId : userIdList) {
                WebSocketManager.sendMessage(userId, msgInfo);
            }
        } catch (JsonProcessingException e) {
            log.error("发送websocket异常", e);
        }
    }

}
