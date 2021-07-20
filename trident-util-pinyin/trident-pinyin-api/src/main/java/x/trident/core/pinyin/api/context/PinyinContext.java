
package x.trident.core.pinyin.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.pinyin.api.PinYinApi;

/**
 * 拼音工具类快速获取
 *
 * @author 林选伟
 * @date 2020/12/4 9:31
 */
public class PinyinContext {
    private PinyinContext() {
    }

    /**
     * 获取拼音工具类
     */
    public static PinYinApi me() {
        return SpringUtil.getBean(PinYinApi.class);
    }

}
