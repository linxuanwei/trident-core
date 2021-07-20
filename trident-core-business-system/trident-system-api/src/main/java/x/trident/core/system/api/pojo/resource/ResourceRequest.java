
package x.trident.core.system.api.pojo.resource;

import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 资源请求封装
 *
 * @author 林选伟
 * @since 2019-09-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编码
     */
    @ChineseDescription("应用编码")
    private String appCode;

    /**
     * 资源名称
     */
    @ChineseDescription("资源名称")
    private String resourceName;

    /**
     * 资源编码
     */
    @ChineseDescription("资源编码")
    @NotBlank(message = "资源编码为空", groups = detail.class)
    private String resourceCode;

    /**
     * 资源地址
     */
    @ChineseDescription("资源地址")
    private String url;

    /**
     * 是否是菜单（Y-是，N-否）
     */
    @ChineseDescription("是否是菜单（Y-是，N-否）")
    private String menuFlag;

}
