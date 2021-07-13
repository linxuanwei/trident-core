package x.trident.core.constants;

/**
 * 基本的常量
 *
 * @author 林选伟
 * @date 2021/7/13 8:17 下午
 */
public class BaseConstants {
    private BaseConstants() {
    }

    /**
     * 用户端操作异常的错误码分类编号
     */
    public static final String USER_OPERATION_ERROR_TYPE_CODE = "A";

    /**
     * 业务执行异常的错误码分类编号
     */
    public static final String BUSINESS_ERROR_TYPE_CODE = "B";

    /**
     * 第三方调用异常的错误码分类编号
     */
    public static final String THIRD_ERROR_TYPE_CODE = "C";

    /**
     * 一级宏观码标识，宏观码标识代表一类错误码的统称
     */
    public static final String FIRST_LEVEL_WIDE_CODE = "0001";

    /**
     * 请求成功的返回码
     */
    public static final String SUCCESS_CODE = "00000";

    /**
     * 请求成功的返回信息
     */
    public static final String SUCCESS_MESSAGE = "请求成功";

    /**
     * 规则模块的名称
     */
    public static final String BASE_MODULE_NAME = "trident-core-base";

    /**
     * 异常枚举的步进值
     */
    public static final String RULE_EXCEPTION_STEP_CODE = "01";

    /**
     * 一级公司的父级id
     */
    public static final Long TREE_ROOT_ID = -1L;

    /**
     * 中文的多语言类型编码
     */
    public static final String CHINES_TRAN_LANGUAGE_CODE = "chinese";

    /**
     * 租户数据源标识前缀
     */
    public static final String TENANT_DB_PREFIX = "sys_tenant_db_";
}
