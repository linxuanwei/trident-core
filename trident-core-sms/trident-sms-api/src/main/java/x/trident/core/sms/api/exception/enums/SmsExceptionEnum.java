
package x.trident.core.sms.api.exception.enums;

import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;

import x.trident.core.sms.api.constants.SmsConstants;
import lombok.Getter;

/**
 * 短信发送的异常枚举
 *
 * @author 林选伟
 * @date 2020/10/26 17:19
 */
@Getter
public enum SmsExceptionEnum implements AbstractExceptionEnum {

    /**
     * 阿里云短信发送异常
     */
    ALIYUN_SMS_ERROR(BaseConstants.THIRD_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "01", "阿里云短信发送异常，错误码：{}，错误信息：{}"),

    /**
     * 阿里云短信发送accesskey错误
     */
    ALIYUN_SMS_KEY_ERROR(BaseConstants.THIRD_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "02", "初始化sms客户端错误，accessKey错误，accessKeyId：{}"),

    /**
     * 短信发送请求参数为空
     */
    SEND_SMS_PARAM_NULL(BaseConstants.BUSINESS_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "03", "短信发送请求参数为空，参数为：{}"),

    /**
     * 腾讯云短信发送异常
     */
    TENCENT_SMS_PARAM_NULL(BaseConstants.THIRD_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "04", "腾讯云短信发送异常，错误码：{}，错误信息：{}"),

    /**
     * 短信验证失败，库中找不到短信发送的记录
     */
    SMS_VALIDATE_ERROR_NOT_EXISTED_RECORD(BaseConstants.BUSINESS_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "05", "验证失败，库中没有该短信发送记录"),

    /**
     * 短信验证失败，验证码失效，可能这个短信验证码用过了
     */
    SMS_VALIDATE_ERROR_INVALIDATE_STATUS(BaseConstants.BUSINESS_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "06", "验证失败，短信验证码失效，请检查是否被使用过"),

    /**
     * 短信验证失败，验证码错误
     */
    SMS_VALIDATE_ERROR_INVALIDATE_CODE(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "07", "验证失败，短信验证码错误"),

    /**
     * 短信验证失败，验证码超时
     */
    SMS_VALIDATE_ERROR_INVALIDATE_TIME(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "08", "验证失败，验证码超时");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SmsExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
