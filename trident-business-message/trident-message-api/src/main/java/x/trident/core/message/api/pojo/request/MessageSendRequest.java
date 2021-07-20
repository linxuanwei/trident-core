
package x.trident.core.message.api.pojo.request;

import x.trident.core.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 发送系统消息的参数
 *
 * @author 林选伟
 * @date 2021/1/1 20:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageSendRequest extends BaseRequest {

    /**
     * 接收用户id字符串，多个以,分割
     */
    @NotBlank(message = "接收用户ID字符串不能为空", groups = {add.class, edit.class})
    private String receiveUserIds;

    /**
     * 消息标题
     */
    @NotBlank(message = "消息标题不能为空", groups = {add.class, edit.class})
    private String messageTitle;

    /**
     * 消息的内容
     */
    private String messageContent;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息优先级
     */
    private String priorityLevel;

    /**
     * 业务id
     */
    @NotNull(message = "业务id不能为空", groups = {add.class, edit.class})
    private Long businessId;

    /**
     * 业务类型
     */
    @NotBlank(message = "业务类型不能为空", groups = {add.class, edit.class})
    private String businessType;

    /**
     * 消息发送时间
     */
    private Date messageSendTime;

}
