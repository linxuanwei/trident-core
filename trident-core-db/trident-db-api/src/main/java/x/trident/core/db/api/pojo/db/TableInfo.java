package x.trident.core.db.api.pojo.db;

import lombok.Data;

/**
 * 表的基本信息
 *
 * @author 林选伟
 * @date 2021/5/19 10:47
 */
@Data
public class TableInfo {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表的注释
     */
    private String tableComment;

}
