
package x.trident.core.office.api.exception.enums;

import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.office.api.constants.OfficeConstants;


/**
 * Office模块相关异常枚举
 *
 * @author 林选伟
 * @date 2020/11/4 10:19
 */
@Getter
public enum OfficeExceptionEnum implements AbstractExceptionEnum {

    /**
     * Office操作异常
     */
    OFFICE_ERROR(BaseConstants.THIRD_ERROR_TYPE_CODE + OfficeConstants.OFFICE_EXCEPTION_STEP_CODE + "01", "Office操作异常，具体信息为：{}"),

    /**
     * Excel导出 响应为空
     */
    OFFICE_EXCEL_EXPORT_RESPONSE_ISNULL(BaseConstants.THIRD_ERROR_TYPE_CODE + OfficeConstants.OFFICE_EXCEPTION_STEP_CODE + "02", "Excel导出响应为空"),

    /**
     * Excel导出 实体类为空
     */
    OFFICE_EXCEL_EXPORT_ENTITY_CLASS_ISNULL(BaseConstants.THIRD_ERROR_TYPE_CODE + OfficeConstants.OFFICE_EXCEPTION_STEP_CODE + "03", "实体类为空");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    OfficeExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
