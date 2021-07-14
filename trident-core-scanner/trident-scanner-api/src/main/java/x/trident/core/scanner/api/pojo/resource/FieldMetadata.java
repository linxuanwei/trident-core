package x.trident.core.scanner.api.pojo.resource;

import lombok.Data;
import x.trident.core.scanner.api.annotation.ChineseDescription;

import java.util.Map;
import java.util.Set;

/**
 * 字典描述类
 *
 * @author 林选伟
 * @date 2020/12/8 18:25
 */
@Data
public class FieldMetadata {

    /**
     * 生成给前端用
     * <p>
     * uuid，标识一个字段的唯一性
     */
    @ChineseDescription("生成给前端用")
    private String metadataId;

    /**
     * 字段中文名称，例如：创建用户
     */
    @ChineseDescription("字段中文名称")
    private String chineseName;

    /**
     * 字段类型，例如：String
     */
    @ChineseDescription("字段类型")
    private String fieldClassType;

    /**
     * 字段名称，例如：createUser
     */
    @ChineseDescription("字段名称")
    private String fieldName;

    /**
     * 字段的注解，例如：NotBlack，NotNull
     */
    @ChineseDescription("字段的注解")
    private Set<String> annotations;

    /**
     * 按校验组分的注解集合
     * <p>
     * 例如：
     * key = add, value = [不能为空，最大多少位，邮箱类型]
     */
    @ChineseDescription("按校验组分的注解集合")
    private Map<String, Set<String>> groupValidationMessage;

    /**
     * 校验信息的提示信息
     */
    @ChineseDescription("校验信息的提示信息")
    private String validationMessages;

    /**
     * 泛型或object类型的字段的描述
     */
    @ChineseDescription("泛型或object类型的字段的描述")
    private Set<FieldMetadata> genericFieldMetadata;

}
