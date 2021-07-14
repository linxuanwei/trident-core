package x.trident.core.util;

import cn.hutool.core.util.StrUtil;
import x.trident.core.enums.DbTypeEnum;

/**
 * 判断数据库类型的工具
 *
 * @author 林选伟
 * @date 2021/3/27 21:24
 */
public class DatabaseTypeUtil {

    /**
     * 判断数据库类型
     *
     * @param jdbcUrl 数据库链接
     */
    public static DbTypeEnum getDbType(String jdbcUrl) {
        if (StrUtil.isEmpty(jdbcUrl)) {
            return DbTypeEnum.MYSQL;
        }

        if (jdbcUrl.contains(DbTypeEnum.ORACLE.getName())) {
            return DbTypeEnum.ORACLE;
        } else if (jdbcUrl.contains(DbTypeEnum.PG_SQL.getName())) {
            return DbTypeEnum.PG_SQL;
        } else if (jdbcUrl.contains(DbTypeEnum.MS_SQL.getName())) {
            return DbTypeEnum.MS_SQL;
        } else {
            return DbTypeEnum.MYSQL;
        }
    }

}
