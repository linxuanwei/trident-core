
package x.trident.core.db.api.sqladapter;

import x.trident.core.db.api.enums.DbTypeEnum;

/**
 * 异构sql获取基类，通过继承此类，编写使用不同数据库的sql
 *
 * @author 林选伟
 * @date 2020/10/31 23:44
 */
public abstract class AbstractSql {

    /**
     * 获取异构sql
     *
     * @param jdbcUrl 数据连接的url
     * @return 具体的sql
     */
    public String getSql(String jdbcUrl) {
        if (jdbcUrl.contains(DbTypeEnum.ORACLE.getCode())) {
            return oracle();
        }
        if (jdbcUrl.contains(DbTypeEnum.MS_SQL.getCode())) {
            return sqlServer();
        }
        if (jdbcUrl.contains(DbTypeEnum.PG_SQL.getCode())) {
            return pgSql();
        }
        return mysql();
    }

    /**
     * 获取mysql的sql语句
     *
     * @return 具体的sql
     */
    protected abstract String mysql();

    /**
     * 获取sqlServer的sql语句
     *
     * @return 具体的sql
     */
    protected abstract String sqlServer();

    /**
     * 获取pgSql的sql语句
     *
     * @return 具体的sql
     **/
    protected abstract String pgSql();

    /**
     * 获取oracle的sql语句
     *
     * @return 具体的sql
     */
    protected abstract String oracle();

}
