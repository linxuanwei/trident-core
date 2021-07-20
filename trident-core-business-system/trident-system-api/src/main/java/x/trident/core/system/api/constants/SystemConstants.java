
package x.trident.core.system.api.constants;

/**
 * 系统管理模块的常量
 *
 * @author 林选伟
 * @date 2020/11/4 15:48
 */
public interface SystemConstants {

    /**
     * 系统管理模块的名称
     */
    String SYSTEM_MODULE_NAME = "trident-core-business-system";

    /**
     * 异常枚举的步进值
     */
    String SYSTEM_EXCEPTION_STEP_CODE = "18";

    /**
     * 默认的系统版本号
     */
    String DEFAULT_SYSTEM_VERSION = "20210101";

    /**
     * 默认多租户的开关：关闭
     */
    Boolean DEFAULT_TENANT_OPEN = false;

    /**
     * 默认验证码的开关：关闭
     */
    Boolean DEFAULT_CAPTCHA_OPEN = false;

    /**
     * 默认的系统的名称
     */
    String DEFAULT_SYSTEM_NAME = "Guns快速开发平台";

    /**
     * 用户缓存的前缀
     */
    String USER_CACHE_PREFIX = "user:";

    /**
     * 用户缓存过期时间(1小时)
     */
    Long USER_CACHE_TIMEOUT_SECONDS = 3600L;

    /**
     * 超级管理员的角色编码
     */
    String SUPER_ADMIN_ROLE_CODE = "superAdmin";

    /**
     * 初始化超级管理员的监听器顺序
     */
    Integer SUPER_ADMIN_INIT_LISTENER_SORT = 400;

}
