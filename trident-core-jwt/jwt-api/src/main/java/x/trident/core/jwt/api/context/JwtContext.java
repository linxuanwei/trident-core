
package x.trident.core.jwt.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.jwt.api.JwtApi;

/**
 * Jwt工具的context，获取容器中的jwt工具类
 *
 * @author 林选伟
 * @date 2020/10/21 14:07
 */
public class JwtContext {
    private JwtContext() {
    }

    /**
     * 获取jwt操作接口
     */
    public static JwtApi me() {
        return SpringUtil.getBean(JwtApi.class);
    }

}
