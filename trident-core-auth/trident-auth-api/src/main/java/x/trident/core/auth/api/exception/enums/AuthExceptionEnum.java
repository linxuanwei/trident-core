package x.trident.core.auth.api.exception.enums;

import x.trident.core.auth.api.constants.AuthConstants;
import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;

/**
 * 认证相关异常
 *
 * @author 林选伟
 * @date 2020/10/16 10:53
 */
@Getter
public enum AuthExceptionEnum implements AbstractExceptionEnum {

    /**
     * 会话过期或超时，token过期，都属于这种异常，提示用户从新登录
     */
    AUTH_EXPIRED_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "01", "当前登录会话过期，请重新登录"),

    /**
     * jwt token解析失败，可能用户写错了token，或者用户随意写的token，导致jwt无法解析
     */
    TOKEN_PARSE_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "01", "TOKEN解析失败，请重新登录"),

    /**
     * 登陆时，账号或密码为空
     */
    PARAM_EMPTY(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "03", "登陆失败，账号或密码参数为空"),

    /**
     * 账号或密码错误
     */
    USERNAME_PASSWORD_ERROR(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "04", "账号或密码错误"),

    /**
     * 用户状态异常，可能被禁用可能被冻结，用StrUtil.format()格式化
     */
    USER_STATUS_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "05", "当前用户被{}，请检查用户状态是否正常"),

    /**
     * 登陆失败，账号参数为空
     */
    ACCOUNT_IS_BLANK(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "06", "登陆失败，账号参数为空"),

    /**
     * 获取token失败
     */
    TOKEN_GET_ERROR(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "07", "获取token失败，请检查header和param中是否传递了用户token"),

    /**
     * 获取资源为空
     */
    RESOURCE_DEFINITION_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "08", "获取资源为空，请检查当前请求url是否存在对应的ResourceDefinition"),

    /**
     * 权限校验失败，请检查用户是否有该资源的权限
     */
    PERMISSION_RES_VALIDATE_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "09", "权限校验失败，请检查用户是否有该资源的权限"),

    /**
     * 数据范围类型转化异常
     */
    DATA_SCOPE_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "10", "数据范围类型转化异常，数据范围类型为：{}"),

    /**
     * 权限校验失败，只有超级管理员可以授权所有数据
     */
    ONLY_SUPER_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "11", "权限校验失败，只有超级管理员可以授权所有数据"),

    /**
     * 用户角色未绑定，登录失败
     */
    ROLE_IS_EMPTY(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "12", "用户角色未绑定，登录失败"),

    /**
     * SSO登录获取loginCode失败
     */
    SSO_LOGIN_CODE_GET_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "13", "登录失败,具体信息为：{}"),

    /**
     * SSO使用token登录时，token解析异常
     */
    SSO_TOKEN_PARSE_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "14", "token解析异常：{}"),

    /**
     * SSO使用token登录时，解析token中的用户信息错误，用户信息为空
     */
    SSO_TOKEN_GET_USER_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "15", "解析token中的用户信息错误，用户信息为空"),

    /**
     * SSO使用token登录时，解密token出错
     */
    SSO_TOKEN_DECRYPT_USER_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "16", "解密token出错:{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    AuthExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
