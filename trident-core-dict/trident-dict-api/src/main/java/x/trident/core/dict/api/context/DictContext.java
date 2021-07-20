
package x.trident.core.dict.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.dict.api.DictApi;

/**
 * 字典模块，对外的api
 *
 * @author 林选伟
 * @date 2020/10/29 11:39
 */
public class DictContext {

    /**
     * 获取字典相关操作接口
     *
     * @author 林选伟
     * @date 2020/10/29 11:55
     */
    public static DictApi me() {
        return SpringUtil.getBean(DictApi.class);
    }

}
