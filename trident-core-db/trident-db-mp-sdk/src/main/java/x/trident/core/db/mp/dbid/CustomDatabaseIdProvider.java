
package x.trident.core.db.mp.dbid;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import x.trident.core.db.api.enums.DbTypeEnum;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库id选择器，用在一个mapper.xml中包含多种数据库的sql时候
 * <p>
 * 提供给mybatis能识别不同数据库的标识
 *
 * @author 林选伟
 * @date 2020/10/16 17:02
 */
@Slf4j
public class CustomDatabaseIdProvider implements DatabaseIdProvider {

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();

            if (url.contains(DbTypeEnum.ORACLE.getCode())) {
                return DbTypeEnum.ORACLE.getCode();
            }
            if (url.contains(DbTypeEnum.MS_SQL.getCode())) {
                return DbTypeEnum.MS_SQL.getCode();
            }
            if (url.contains(DbTypeEnum.PG_SQL.getCode())) {
                return DbTypeEnum.PG_SQL.getCode();
            }

            return DbTypeEnum.MYSQL.getCode();
        }
    }

}
