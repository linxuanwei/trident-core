
package x.trident.core.i18n.modular.entity;

import x.trident.core.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 多语言表
 *
 * @author 林选伟
 * @date 2021/1/24 19:12
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_translation")
@Data
public class Translation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "tran_id", type = IdType.ASSIGN_ID)
    private Long tranId;

    /**
     * 编码
     */
    @TableField("tran_code")
    private String tranCode;

    /**
     * 多语言条例名称
     */
    @TableField("tran_name")
    private String tranName;

    /**
     * 语种字典
     */
    @TableField("tran_language_code")
    private String tranLanguageCode;

    /**
     * 翻译的值
     */
    @TableField("tran_value")
    private String tranValue;

}
