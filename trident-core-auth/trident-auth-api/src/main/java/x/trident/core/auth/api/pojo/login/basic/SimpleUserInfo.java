
package x.trident.core.auth.api.pojo.login.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基本信息
 *
 * @author 林选伟
 * @date 2020/12/26 18:14
 */
@Data
public class SimpleUserInfo implements Serializable {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 头像
     */
    private Long avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别（M-男，F-女）
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电话
     */
    private String tel;

}
