
package x.trident.core.datasource.business.modular.factory;

import x.trident.core.datasource.business.modular.entity.DatabaseInfo;
import x.trident.core.db.api.pojo.druid.DruidProperties;

/**
 * Druid数据源创建
 *
 * @author 林选伟
 * @date 2020/11/1 21:44
 */
public class DruidPropertiesFactory {
    private DruidPropertiesFactory() {
    }

    /**
     * 创建druid配置
     *
     * @author 林选伟
     * @date 2019-06-15 20:05
     */
    public static DruidProperties createDruidProperties(DatabaseInfo databaseInfo) {
        DruidProperties druidProperties = new DruidProperties();
        druidProperties.setDriverClassName(databaseInfo.getJdbcDriver());
        druidProperties.setUsername(databaseInfo.getUsername());
        druidProperties.setPassword(databaseInfo.getPassword());
        druidProperties.setUrl(databaseInfo.getJdbcUrl());
        return druidProperties;
    }

}
