
package x.trident.core.message.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.message.api.MessageApi;

/**
 * 消息操作api的获取
 *
 * @author 林选伟
 * @date 2021/1/1 21:13
 */
public class MessageContext {

    /**
     * 获取消息操作api
     *
     * @author 林选伟
     * @date 2021/1/1 21:13
     */
    public static MessageApi me() {
        return SpringUtil.getBean(MessageApi.class);
    }

}
