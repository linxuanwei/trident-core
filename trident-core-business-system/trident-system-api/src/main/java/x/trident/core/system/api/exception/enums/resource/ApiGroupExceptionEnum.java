package x.trident.core.system.api.exception.enums.resource;

import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 接口分组异常相关枚举
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@Getter
public enum ApiGroupExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询结果不存在
     */
    APIGROUP_NOT_EXISTED(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "01", "查询结果不存在"),

    /**
     * 根节点不允许操作
     */
    ROOT_PROHIBIT_OPERATION(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "02", "根节点不允许操作"),

    /**
     * 父节点不能选择自己
     */
    PARENT_NODE_ITSELF(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "03", "父节点不能选择自己"),

    /**
     * 父节点不能选择自己的子节点
     */
    NODE_NOT_ALLOWED_SUB_NODE(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "04", "父节点不能选择自己的子节点");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ApiGroupExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}