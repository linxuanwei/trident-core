
package x.trident.core.datasource.sdk.persist.sqladapter;

import x.trident.core.db.api.sqladapter.AbstractSql;
import lombok.Getter;

/**
 * 数据源列表sql
 *
 * @author 林选伟
 * @date 2019-07-16-13:06
 */
@Getter
public class DatabaseListSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "select db_name, jdbc_driver, jdbc_url, username, password from sys_database_info where del_flag = 'N'";
    }

    @Override
    protected String sqlServer() {
        return "select db_name,jdbc_driver,jdbc_url,username,password from sys_database_info";
    }

    @Override
    protected String pgSql() {
        return "select db_name,jdbc_driver,jdbc_url,username,password from sys_database_info";
    }

    @Override
    protected String oracle() {
        return "select db_name,jdbc_driver,jdbc_url,username,password from sys_database_info";
    }
}
