
package x.trident.core.validator.api.validators.status;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 状态校验，校验参数的状态是否是 StatusEnum 中的值
 *
 * @author 林选伟
 * @date 2020/10/31 13:56
 */
@Documented
@Constraint(validatedBy = StatusValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusValue {

    String message() default "不正确的状态标识";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 是否必填
     * <p>
     * 如果必填，在校验的时候本字段没值就会报错
     */
    boolean required() default true;

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        StatusValue[] value();
    }

}
