
package x.trident.core.message.api.exception.enums;

import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.message.api.constants.MessageConstants;

/**
 * 消息异常枚举
 *
 * @author 林选伟
 * @date 2021/1/1 21:14
 */
@Getter
public enum MessageExceptionEnum implements AbstractExceptionEnum {

    /**
     * 发送系统消息时，传入的参数中receiveUserIds不合法
     */
    ERROR_RECEIVE_USER_IDS(BaseConstants.BUSINESS_ERROR_TYPE_CODE + MessageConstants.MESSAGE_EXCEPTION_STEP_CODE + "01", "接收用户id字符串不合法！系统无人可接收消息！"),

    /**
     * 消息记录不存在
     */
    NOT_EXISTED(BaseConstants.BUSINESS_ERROR_TYPE_CODE + MessageConstants.MESSAGE_EXCEPTION_STEP_CODE + "01", "消息记录不存在，id为：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    MessageExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
