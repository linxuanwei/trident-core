
package x.trident.core.sms.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.sms.api.SmsSenderApi;

/**
 * 短信发送类快速获取
 *
 * @author 林选伟
 * @date 2020/10/26 16:53
 */
public class SmsContext {
    private SmsContext() {
    }

    /**
     * 获取短信发送接口
     *
     * @author 林选伟
     * @date 2020/10/26 16:54
     */
    public static SmsSenderApi me() {
        return SpringUtil.getBean(SmsSenderApi.class);
    }

}
