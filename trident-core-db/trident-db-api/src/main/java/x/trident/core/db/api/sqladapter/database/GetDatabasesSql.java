
package x.trident.core.db.api.sqladapter.database;

import x.trident.core.db.api.sqladapter.AbstractSql;
import lombok.Getter;

/**
 * 创建数据库的sql，可用在租户的创建
 *
 * @author 林选伟
 * @date 2019-07-16-13:06
 */
@Getter
public class GetDatabasesSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "show databases;";
    }

    @Override
    protected String sqlServer() {
        return "";
    }

    @Override
    protected String pgSql() {
        return "";
    }

    @Override
    protected String oracle() {
        return "";
    }
}
