package x.trident.core.config.api;

import cn.hutool.db.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 系统配置元数据获取的api
 *
 * @author 林选伟
 * @date 2021/3/27 21:15
 */
public interface SysConfigDataApi {

    /**
     * 获取系统配置表中的所有数据
     *
     * @param conn 原始数据库连接
     * @return 所有记录的list
     * @throws SQLException sql异常
     */
    List<Entity> getConfigs(Connection conn) throws SQLException;

    /**
     * 获取所有配置list的sql
     *
     * @return string
     */
    String getConfigListSql();

}
