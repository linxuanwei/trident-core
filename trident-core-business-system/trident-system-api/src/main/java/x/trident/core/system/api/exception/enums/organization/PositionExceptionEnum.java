
package x.trident.core.system.api.exception.enums.organization;

import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 职务异常
 *
 * @author 林选伟
 * @date 2020/11/6 18:08
 */
@Getter
public enum PositionExceptionEnum implements AbstractExceptionEnum {

    /**
     * 找不到职务
     */
    CANT_FIND_POSITION(BaseConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "58", "查询不到组该职务，错误的职务ID：{}"),

    /**
     * 职务删除失败
     */
    CANT_DELETE_POSITION(BaseConstants.BUSINESS_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "59", "职务删除失败，该职务下有关联人员");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    PositionExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
