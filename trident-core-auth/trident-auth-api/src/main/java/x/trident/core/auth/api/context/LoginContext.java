
package x.trident.core.auth.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.auth.api.LoginUserApi;

/**
 * 快速获取当前登陆用户的一系列操作方法，具体实现在Spring容器中查找
 *
 * @author 林选伟
 * @date 2020/10/17 10:30
 */
public class LoginContext {
    private LoginContext() {
    }

    public static LoginUserApi me() {
        return SpringUtil.getBean(LoginUserApi.class);
    }

}
