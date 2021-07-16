
package x.trident.core.datasource.api.enums;

import lombok.Getter;

/**
 * 数据库连接的状态枚举
 *
 * @author 林选伟
 * @date 2021/4/22 11:02
 */
@Getter
public enum DataSourceStatusEnum {

    /**
     * 正常
     */
    ENABLE(1, "正常"),

    /**
     * 无法连接
     */
    ERROR(2, "无法连接");

    private final Integer code;

    private final String message;

    DataSourceStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
