
package x.trident.core.system.api.pojo.user.request;

import x.trident.core.scanner.api.annotation.ChineseDescription;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 当前的在线用户的信息请求
 *
 * @author 林选伟
 * @date 2021/1/11 22:30
 */
@Data
public class OnlineUserRequest {

    /**
     * 用户的token
     */
    @NotBlank(message = "参数token不能为空")
    @ChineseDescription("用户的token")
    private String token;

    /**
     * 用户账号
     */
    @ChineseDescription("用户账号")
    private String account;

}
