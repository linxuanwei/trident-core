package x.trident.core.scanner.api.exception.enums;

import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.scanner.api.constants.ScannerConstants;
import lombok.Getter;

/**
 * 资源相关的异常枚举
 *
 * @author 林选伟
 * @date 2020/11/3 13:55
 */
@Getter
public enum ScannerExceptionEnum implements AbstractExceptionEnum {

    /**
     * 获取资源为空
     */
    RESOURCE_DEFINITION_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + ScannerConstants.RESOURCE_MODULE_NAME + "01", "获取资源为空，请检查当前请求url是否存在对应的ResourceDefinition"),

    /**
     * 扫描资源过程中，存在不合法控制器名称，请将控制名称以Controller结尾
     */
    ERROR_CONTROLLER_NAME(BaseConstants.BUSINESS_ERROR_TYPE_CODE + ScannerConstants.RESOURCE_MODULE_NAME + "02", "扫描资源过程中，存在不合法控制器名称，请将控制名称以Controller结尾，控制器名称：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ScannerExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
