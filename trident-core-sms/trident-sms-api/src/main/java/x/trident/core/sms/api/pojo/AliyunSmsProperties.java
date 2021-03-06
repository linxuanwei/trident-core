
package x.trident.core.sms.api.pojo;

import lombok.Data;

/**
 * 阿里云oss相关配置
 *
 * @author 林选伟
 * @date 2018-06-27-下午1:20
 */
@Data
public class AliyunSmsProperties {

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 签名名称
     */
    private String signName;

    /**
     * 地域id（阿里云sdk默认的，一般不用修改）
     */
    private String regionId = "cn-hangzhou";

    /**
     * domain（阿里云sdk默认的，一般不用修改）
     */
    private String smsDomain = "dysmsapi.aliyuncs.com";

    /**
     * version（阿里云sdk默认的，一般不用修改）
     */
    private String smsVersion = "2017-05-25";

    /**
     * sms发送（阿里云sdk默认的，一般不用修改）
     */
    private String smsSendAction = "SendSms";

}
