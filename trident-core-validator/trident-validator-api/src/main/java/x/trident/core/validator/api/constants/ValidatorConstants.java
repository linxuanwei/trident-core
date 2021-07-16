
package x.trident.core.validator.api.constants;

/**
 * 校验器模块的常量
 *
 * @author 林选伟
 * @date 2020/10/31 14:24
 */
public class ValidatorConstants {
    private ValidatorConstants() {
    }

    /**
     * 校验器模块的名称
     */
    public static final String VALIDATOR_MODULE_NAME = "trident-core-validator";

    /**
     * 异常枚举的步进值
     */
    public static final String VALIDATOR_EXCEPTION_STEP_CODE = "15";

    /**
     * 默认逻辑删除字段的字段名
     */
    public static final String DEFAULT_LOGIC_DELETE_FIELD_NAME = "del_flag";

    /**
     * 默认逻辑删除字段的值
     */
    public static final String DEFAULT_LOGIC_DELETE_FIELD_VALUE = "Y";

}
