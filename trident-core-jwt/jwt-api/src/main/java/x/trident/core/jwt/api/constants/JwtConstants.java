
package x.trident.core.jwt.api.constants;

/**
 * jwt模块常量
 *
 * @author 林选伟
 * @date 2020/10/16 11:05
 */
public class JwtConstants {
    private JwtConstants() {
    }

    /**
     * jwt模块的名称
     */
    public static final String JWT_MODULE_NAME = "trident-core-jwt";

    /**
     * 异常枚举的步进值
     */
    public static final String JWT_EXCEPTION_STEP_CODE = "06";

    /**
     * jwt默认失效时间 1天
     */
    public static final Long DEFAULT_JWT_TIMEOUT_SECONDS = 3600L * 24;

}
