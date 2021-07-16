
package x.trident.core.db.api.exception.enums;

import x.trident.core.db.api.constants.DbConstants;

import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;

/**
 * Flyway相关异常枚举
 *
 * @author 林选伟
 * @date 2021/1/18 22:59
 */
@Getter
public enum FlywayExceptionEnum implements AbstractExceptionEnum {

    /**
     * 获取不到application.yml中的数据库配置
     */
    DB_CONFIG_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "01", "获取不到application.yml中的数据库配置，无法为flyway创建数据库链接，请检查spring.datasource配置"),

    /**
     * flyway执行迁移异常
     */
    FLYWAY_MIGRATE_ERROR(BaseConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "02", "脚本错误，flyway执行迁移异常，具体原因：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlywayExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
