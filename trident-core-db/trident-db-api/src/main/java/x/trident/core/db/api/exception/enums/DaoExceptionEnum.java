package x.trident.core.db.api.exception.enums;

import x.trident.core.db.api.constants.DbConstants;

import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;

/**
 * 数据库相关操作的异常枚举
 *
 * @author 林选伟
 * @date 2020/10/16 10:53
 */
@Getter
public enum DaoExceptionEnum implements AbstractExceptionEnum {

    /**
     * 数据库操作未知异常
     */
    DAO_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "01", "数据库操作未知异常");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DaoExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
