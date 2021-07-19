
package x.trident.core.auth.api.pojo.login;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import x.trident.core.auth.api.enums.DataScopeTypeEnum;
import x.trident.core.auth.api.pojo.login.basic.SimpleRoleInfo;
import x.trident.core.auth.api.pojo.login.basic.SimpleUserInfo;
import x.trident.core.constants.BaseConstants;
import x.trident.core.scanner.api.annotation.ChineseDescription;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 登录用户信息
 *
 * @author 林选伟
 * @date 2020/10/17 9:58
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键id
     */
    @ChineseDescription("用户主键id")
    private Long uid;

    /**
     * 账号
     */
    @ChineseDescription("账号")
    private String account;

    /**
     * 超级管理员标识，true-是超级管理员
     */
    @ChineseDescription("超级管理员标识，true-是超级管理员")
    private Boolean superAdmin;

    /**
     * 用户基本信息
     */
    @ChineseDescription("用户基本信息")
    private SimpleUserInfo simpleUserInfo;

    /**
     * 用户角色信息
     */
    @ChineseDescription("用户角色信息")
    private List<SimpleRoleInfo> simpleRoleInfoList;

    /**
     * 公司/组织id
     */
    @ChineseDescription("公司/组织id")
    private Long organizationId;

    /**
     * 职务信息
     */
    @ChineseDescription("职务信息")
    private Long positionId;

    /**
     * 用户数据范围枚举
     */
    @ChineseDescription("用户数据范围枚举")
    private Set<DataScopeTypeEnum> dataScopeTypeEnums;

    /**
     * 用户数据范围用户信息
     */
    @ChineseDescription("用户数据范围用户信息")
    private Set<Long> dataScopeUserIds;

    /**
     * 用户数据范围组织信息
     */
    @ChineseDescription("用户数据范围组织信息")
    private Set<Long> dataScopeOrganizationIds;

    /**
     * 可用资源集合
     */
    @ChineseDescription("可用资源集合")
    private Set<String> resourceUrls;

    /**
     * 用户拥有的按钮编码集合
     */
    @ChineseDescription("用户拥有的按钮编码集合")
    private Set<String> buttonCodes;

    /**
     * 登录的时间
     */
    @ChineseDescription("登录的时间")
    private Date loginTime;

    /**
     * 用户的token，当返回用户会话信息时候回带token
     */
    @ChineseDescription("用户的token，当返回用户会话信息时候回带token")
    private String token;

    /**
     * 其他信息，Dict为Map的拓展
     */
    @ChineseDescription("其他信息，Dict为Map的拓展")
    private Dict otherInfos;

    /**
     * 用户的ws-url
     */
    @ChineseDescription("用户的ws-url")
    private String wsUrl;

    /**
     * 当前用户语种的标识，例如：chinese，english
     * <p>
     * 这个值是根据字典获取，字典类型编码 languages
     * <p>
     * 默认语种是中文
     */
    @ChineseDescription("当前用户语种的标识")
    private String tranLanguageCode = BaseConstants.CHINES_TRAN_LANGUAGE_CODE;

    /**
     * 租户的编码
     */
    @ChineseDescription("租户的编码")
    private String tenantCode;

    public String getWsUrl() {
        AtomicReference<String> returnUrl = new AtomicReference<>(StrUtil.EMPTY);
        Optional.ofNullable(this.wsUrl).ifPresent(url -> {
            Map<String, Long> user = new HashMap<>(1);
            user.put("userId", this.uid);
            returnUrl.set(StrUtil.format(url, user));
        });
        return returnUrl.get();
    }

}
