
package x.trident.core.security.api.pojo;

import x.trident.core.scanner.api.annotation.ChineseDescription;
import lombok.Builder;
import lombok.Data;

/**
 * EasyCaptcha 图形验证码参数
 *
 * @author 林选伟
 * @date 2020/8/17 21:43
 */
@Data
@Builder
public class EasyCaptcha {

    /**
     * 缓存Key
     */
    @ChineseDescription("缓存Key")
    private String verKey;

    /**
     * Base64 图形验证码
     */
    @ChineseDescription("Base64 图形验证码")
    private String verImage;

}
