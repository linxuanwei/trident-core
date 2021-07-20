
package x.trident.core.message.modular;

import cn.hutool.core.util.StrUtil;
import x.trident.core.message.api.MessageApi;
import x.trident.core.message.api.enums.MessageReadFlagEnum;
import x.trident.core.message.api.pojo.request.MessageRequest;
import x.trident.core.message.api.pojo.request.MessageSendRequest;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统消息控制器
 *
 * @author 林选伟
 * @date 2021/1/1 22:30
 */
@RestController
@ApiResource(name = "系统消息控制器")
public class SysMessageController {

    /**
     * 系统消息api
     */
    @Resource
    private MessageApi messageApi;

    /**
     * 发送系统消息
     *
     * @author 林选伟
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "发送系统消息", path = "/sysMessage/sendMessage")
    public ResponseData sendMessage(@RequestBody @Validated(MessageSendRequest.add.class) MessageSendRequest messageSendRequest) {
        messageSendRequest.setMessageSendTime(new Date());
        messageApi.sendMessage(messageSendRequest);
        return new SuccessResponseData();
    }

    /**
     * 批量更新系统消息状态
     *
     * @author 林选伟
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "批量更新系统消息状态", path = "/sysMessage/batchUpdateReadFlag")
    public ResponseData batchUpdateReadFlag(@RequestBody @Validated(MessageRequest.updateReadFlag.class) MessageRequest messageRequest) {
        List<Long> messageIdList = messageRequest.getMessageIdList();
        messageApi.batchReadFlagByMessageIds(StrUtil.join(",", messageIdList), MessageReadFlagEnum.READ);
        return new SuccessResponseData();
    }

    /**
     * 系统消息全部修改已读
     *
     * @author 林选伟
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "系统消息全部修改已读", path = "/sysMessage/allMessageReadFlag")
    public ResponseData allMessageReadFlag() {
        messageApi.allMessageReadFlag();
        return new SuccessResponseData();
    }

    /**
     * 删除系统消息
     *
     * @author 林选伟
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "删除系统消息", path = "/sysMessage/delete")
    public ResponseData delete(@RequestBody @Validated(MessageRequest.delete.class) MessageRequest messageRequest) {
        messageApi.deleteByMessageId(messageRequest.getMessageId());
        return new SuccessResponseData();
    }

    /**
     * 查看系统消息
     *
     * @author 林选伟
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "查看系统消息", path = "/sysMessage/detail")
    public ResponseData detail(@Validated(MessageRequest.detail.class) MessageRequest messageRequest) {
        return new SuccessResponseData(messageApi.messageDetail(messageRequest));
    }

    /**
     * 分页查询系统消息列表
     *
     * @author 林选伟
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "分页查询系统消息列表", path = "/sysMessage/page")
    public ResponseData page(MessageRequest messageRequest) {
        return new SuccessResponseData(messageApi.queryPageCurrentUser(messageRequest));
    }

    /**
     * 系统消息列表
     *
     * @author 林选伟
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "系统消息列表", path = "/sysMessage/list")
    public ResponseData list(MessageRequest messageRequest) {
        return new SuccessResponseData(messageApi.queryListCurrentUser(messageRequest));
    }

    /**
     * 系统消息未读数量
     *
     * @author 林选伟
     * @date 2021/1/11 19:50
     */
    @GetResource(name = "系统消息列表", path = "/sysMessage/unReadCount")
    public ResponseData msgUnRead(MessageRequest messageRequest) {
        messageRequest.setReadFlag(MessageReadFlagEnum.UNREAD.getCode());
        Integer messageCount = messageApi.queryCountCurrentUser(messageRequest);
        Map<String, Object> msgMap = new HashMap<>(1);
        msgMap.put("msgUnReadCount", messageCount);
        return new SuccessResponseData(messageApi.queryListCurrentUser(messageRequest));
    }

}
