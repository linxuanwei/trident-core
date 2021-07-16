package x.trident.core.db.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.db.api.DbOperatorApi;

/**
 * 获取sql操作器
 *
 * @author 林选伟
 * @date 2020/11/4 15:07
 */
public class DbOperatorContext {
    private DbOperatorContext() {
    }

    public static DbOperatorApi me() {
        return SpringUtil.getBean(DbOperatorApi.class);
    }

}
