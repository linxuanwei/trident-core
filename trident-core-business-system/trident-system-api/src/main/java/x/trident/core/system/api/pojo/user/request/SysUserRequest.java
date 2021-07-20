
package x.trident.core.system.api.pojo.user.request;

import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import x.trident.core.validator.api.validators.date.DateValue;
import x.trident.core.validator.api.validators.phone.PhoneValue;
import x.trident.core.validator.api.validators.status.StatusValue;
import x.trident.core.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统用户参数
 *
 * @author luojie
 * @date 2020/11/6 15:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "userId不能为空", groups = {edit.class, delete.class, detail.class, grantRole.class, grantData.class, resetPwd.class, changeStatus.class})
    @ChineseDescription("主键")
    private Long userId;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空", groups = {add.class, edit.class, reg.class})
    @TableUniqueValue(
            message = "账号存在重复",
            groups = {add.class, edit.class, reg.class},
            tableName = "sys_user",
            columnName = "account",
            idFieldName = "user_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("账号")
    private String account;

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空", groups = {updatePwd.class, reg.class})
    @ChineseDescription("原密码")
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空", groups = {updatePwd.class})
    @ChineseDescription("新密码")
    private String newPassword;

    /**
     * 昵称
     */
    @ChineseDescription("昵称")
    private String nickName;

    /**
     * 姓名
     */
    @ChineseDescription("姓名")
    private String realName;

    /**
     * 头像
     */
    @NotNull(message = "头像不能为空", groups = {updateAvatar.class})
    @ChineseDescription("头像")
    private Long avatar;

    /**
     * 生日
     */
    @DateValue(required = false, message = "生日格式不正确", groups = {add.class, edit.class})
    @ChineseDescription("生日")
    private String birthday;

    /**
     * 性别（M-男，F-女）
     */
    @NotNull(message = "性别不能为空", groups = {updateInfo.class})
    @ChineseDescription("性别（M-男，F-女）")
    private String sex;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式错误", groups = {updateInfo.class, reg.class})
    @ChineseDescription("邮箱")
    private String email;

    /**
     * 手机
     */
    @PhoneValue(required = false, message = "手机号码格式错误", groups = {add.class, edit.class, reg.class})
    @ChineseDescription("手机")
    private String phone;

    /**
     * 电话
     */
    @ChineseDescription("电话")
    private String tel;

    /**
     * 授权角色，角色id集合
     */
    @NotNull(message = "授权角色不能为空", groups = {grantRole.class})
    @ChineseDescription("授权角色，角色id集合")
    private List<Long> grantRoleIdList;

    /**
     * 授权数据范围，组织机构id集合
     */
    @NotNull(message = "授权数据不能为空", groups = {grantData.class})
    @ChineseDescription("授权数据范围，组织机构id集合")
    private List<Long> grantOrgIdList;

    /**
     * 用户所属机构
     */
    @NotNull(message = "用户所属机构不能为空", groups = {add.class, edit.class})
    @ChineseDescription("用户所属机构")
    private Long orgId;

    /**
     * 用户所属机构的职务
     */
    @ChineseDescription("用户所属机构的职务")
    private Long positionId;

    /**
     * 状态（字典 1正常 2冻结）
     */
    @NotNull(message = "状态不能为空", groups = updateStatus.class)
    @StatusValue(message = "状态不正确", groups = updateStatus.class)
    @ChineseDescription("状态（字典 1正常 2冻结）")
    private Integer statusFlag;

    /**
     * 用户id集合(用在批量删除)
     */
    @NotEmpty(message = "用户id集合不能为空", groups = batchDelete.class)
    @ChineseDescription("用户id集合(用在批量删除)")
    private List<Long> userIds;

    /**
     * 参数校验分组：修改密码
     */
    public @interface updatePwd {
    }

    /**
     * 参数校验分组：重置密码
     */
    public @interface resetPwd {
    }

    /**
     * 参数校验分组：修改头像
     */
    public @interface updateAvatar {
    }

    /**
     * 参数校验分组：停用
     */
    public @interface stop {
    }

    /**
     * 参数校验分组：启用
     */
    public @interface start {
    }

    /**
     * 参数校验分组：更新信息
     */
    public @interface updateInfo {
    }

    /**
     * 参数校验分组：授权角色
     */
    public @interface grantRole {
    }

    /**
     * 参数校验分组：授权数据
     */
    public @interface grantData {
    }

    /**
     * 参数校验分组：修改状态
     */
    public @interface changeStatus {
    }

    /**
     * 参数校验分组：注册用户
     */
    public @interface reg {
    }

}


