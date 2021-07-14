package x.trident.core.scanner.api.pojo.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import x.trident.core.pojo.request.BaseRequest;

/**
 * 获取资源通过url请求
 *
 * @author 林选伟
 * @date 2020/10/19 22:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceUrlParam extends BaseRequest {

    private String url;

}
