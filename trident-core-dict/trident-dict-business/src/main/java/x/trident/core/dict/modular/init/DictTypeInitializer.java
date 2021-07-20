
package x.trident.core.dict.modular.init;

import x.trident.core.db.api.pojo.druid.DruidProperties;
import x.trident.core.db.api.sqladapter.AbstractSql;
import x.trident.core.db.init.actuator.DbInitializer;
import x.trident.core.dict.modular.entity.SysDictType;
import x.trident.core.dict.modular.sqladapter.DictTypeSql;
import org.springframework.stereotype.Component;

/**
 * 字典数据库初始化程序
 *
 * @author majianguo
 * @date 2020/12/9 上午11:02
 * @see AbstractSql
 */
@Component
public class DictTypeInitializer extends DbInitializer {

    @Override
    protected String getTableInitSql() {
        DruidProperties druidProperties = getDruidProperties();
        return new DictTypeSql().getSql(druidProperties.getUrl());
    }

    @Override
    protected String getTableName() {
        return "sys_dict_type";
    }

    @Override
    protected Class<?> getEntityClass() {
        return SysDictType.class;
    }
}
