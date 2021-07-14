package x.trident.core.scanner.api.pojo.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import x.trident.core.pojo.request.BaseRequest;

/**
 * 获取用户资源请求
 *
 * @author 林选伟
 * @date 2020/10/19 22:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserResourceParam extends BaseRequest {

    /**
     * 用户id
     */
    private String uid;

}
