
package x.trident.core.auth.api.pojo.auth;

import x.trident.core.auth.api.pojo.login.LoginUser;
import lombok.Data;
import x.trident.core.scanner.api.annotation.ChineseDescription;

/**
 * 登录操作的响应结果
 *
 * @author 林选伟
 * @date 2020/10/19 14:17
 */
@Data
public class LoginResponse {

    /**
     * 登录人的信息
     */
    @ChineseDescription("登录人的信息")
    private LoginUser loginUser;

    /**
     * 登录人的token
     */
    @ChineseDescription("登录人的token")
    private String token;

    /**
     * 到期时间
     */
    @ChineseDescription("到期时间")
    private Long expireAt;

    /**
     * 使用单点登录
     */
    @ChineseDescription("使用单点登录")
    private Boolean ssoLogin;

    /**
     * 单点登录的loginCode
     */
    @ChineseDescription("单点登录的loginCode")
    private String ssoLoginCode;

    /**
     * 用于普通登录的组装
     *
     * @author 林选伟
     * @date 2021/5/25 22:31
     */
    public LoginResponse(LoginUser loginUser, String token, Long expireAt) {
        this.loginUser = loginUser;
        this.token = token;
        this.expireAt = expireAt;
    }

    /**
     * 用于单点登录，返回用户loginCode
     *
     * @author 林选伟
     * @date 2021/5/25 22:31
     */
    public LoginResponse(String loginCode) {
        this.ssoLogin = true;
        this.ssoLoginCode = loginCode;
    }

}
