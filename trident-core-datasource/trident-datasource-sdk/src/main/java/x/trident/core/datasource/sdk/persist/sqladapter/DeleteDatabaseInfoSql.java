
package x.trident.core.datasource.sdk.persist.sqladapter;

import lombok.Getter;
import x.trident.core.db.api.sqladapter.AbstractSql;

/**
 * 删除数据源sql
 *
 * @author 林选伟
 * @date 2019-07-16-13:06
 */
@Getter
public class DeleteDatabaseInfoSql extends AbstractSql {
    private static final String DELETE_SQL = "DELETE from sys_database_info where db_name = ?";

    @Override
    protected String mysql() {
        return DELETE_SQL;
    }

    @Override
    protected String sqlServer() {
        return DELETE_SQL;
    }

    @Override
    protected String pgSql() {
        return DELETE_SQL;
    }

    @Override
    protected String oracle() {
        return DELETE_SQL;
    }
}
