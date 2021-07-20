
package x.trident.core.log.api.enums;

import lombok.Getter;

/**
 * 日志存储的方式，数据库还是文件
 *
 * @author 林选伟
 * @date 2020/12/24 14:08
 */
@Getter
public enum LogSaveTypeEnum {

    /**
     * 存储到数据库
     */
    DB("db"),

    /**
     * 存储到文件
     */
    FILE("file");

    LogSaveTypeEnum(String code) {
        this.code = code;
    }

    private final String code;

}
