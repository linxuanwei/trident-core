package x.trident.core.auth.api.constants;

/**
 * auth，鉴权模块的常量
 *
 * @author 林选伟
 * @date 2020/10/16 11:05
 */
public class AuthConstants {
    private AuthConstants() {
    }

    /**
     * auth模块的名称
     */
    public static final String AUTH_MODULE_NAME = "trident-core-auth";

    /**
     * 异常枚举的步进值
     */
    public static final String AUTH_EXCEPTION_STEP_CODE = "03";

    /**
     * 登录用户的缓存前缀
     */
    public static final String LOGGED_TOKEN_PREFIX = "LOGGED_TOKEN_";

    /**
     * 登录用户id的缓存前缀
     */
    public static final String LOGGED_USERID_PREFIX = "LOGGED_USERID_";

    /**
     * 默认http请求携带token的header名称
     */
    public static final String DEFAULT_AUTH_HEADER_NAME = "Authorization";

    /**
     * 获取http请求携带token的param的名称
     */
    public static final String DEFAULT_AUTH_PARAM_NAME = "token";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * auth模块，jwt的失效时间，默认7天
     */
    public static final Long DEFAULT_AUTH_JWT_TIMEOUT_SECONDS = 3600L * 24 * 7;

    /**
     * 验证码 session key
     */
    public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    /**
     * 默认解析jwt的秘钥（用于解析sso传过来的token）
     */
    public static final String SYS_AUTH_SSO_JWT_SECRET = "aabbccdd";

    /**
     * 默认解密sso单点中jwt中payload的秘钥
     */
    public static final String SYS_AUTH_SSO_DECRYPT_DATA_SECRET = "EDPpR/BQfEFJiXKgxN8Uno4OnNMGcIJW1F777yySCPA=";

    /**
     * 是否开启sso远程会话校验
     */
    public static final Boolean SYS_AUTH_SSO_SESSION_VALIDATE_SWITCH = false;

    /**
     * 用于远程session校验redis的host
     */
    public static final String SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_HOST = "localhost";

    /**
     * 用于远程session校验redis的端口
     */
    public static final Integer SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PORT = 6379;

    /**
     * 用于远程session校验redis的数据库index
     */
    public static final Integer SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_DB_INDEX = 2;

    /**
     * 用于远程session校验redis的缓存前缀
     */
    public static final String SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_CACHE_PREFIX = "CA:USER:TOKEN:";

    /**
     * SSO的默认地址
     */
    public static final String SYS_AUTH_SSO_HOST = "http://localhost:8888";

    /**
     * sso获取loginCode的url
     */
    public static final String SYS_AUTH_SSO_GET_LOGIN_CODE = "/sso/getLoginCode";

}
