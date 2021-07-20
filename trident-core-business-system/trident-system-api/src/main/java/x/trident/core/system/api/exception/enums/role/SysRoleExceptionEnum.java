
package x.trident.core.system.api.exception.enums.role;

import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 系统角色相关异常枚举
 *
 * @author majianguo
 * @date 2020/11/5 上午11:06
 */
@Getter
public enum SysRoleExceptionEnum implements AbstractExceptionEnum {

    /**
     * 角色不存在
     */
    ROLE_NOT_EXIST(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "61", "角色不存在"),

    /**
     * 角色编码重复
     */
    ROLE_CODE_REPEAT(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "62", "角色编码重复，请检查code参数"),

    /**
     * 角色名称重复
     */
    ROLE_NAME_REPEAT(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "63", "角色名称重复，请检查name参数"),

    /**
     * 超级管理员不能被删除
     */
    SUPER_ADMIN_CANT_DELETE(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "64", "超级管理员不能被删除"),

    /**
     * 超级管理员不能被删除
     */
    SYSTEM_ROLE_CANT_DELETE(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "65", "系统角色不能被删除"),

    /**
     * 必须选择公司范围集合
     */
    PLEASE_FILL_DATA_SCOPE(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "66", "指定部门类型的数据范围必须选择组织机构"),

    /**
     * 超级管理员角色编码不能被修改
     */
    SUPER_ADMIN_ROLE_CODE_ERROR(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "67", "超级管理员角色编码不能被修改");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysRoleExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
