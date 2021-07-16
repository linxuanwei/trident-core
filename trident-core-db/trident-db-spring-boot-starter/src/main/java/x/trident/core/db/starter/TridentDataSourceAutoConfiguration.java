
package x.trident.core.db.starter;

import x.trident.core.db.api.factory.DruidDatasourceFactory;
import x.trident.core.db.api.pojo.druid.DruidProperties;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 数据库连接池的配置
 * <p>
 * 如果系统中没有配DataSource，则系统默认加载Druid连接池，并开启Druid的监控
 *
 * @author 林选伟
 * @date 2020/11/30 22:24
 */
@Configuration
@Import(TridentDruidPropertiesAutoConfiguration.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ConditionalOnMissingBean(DataSource.class)
public class TridentDataSourceAutoConfiguration {

    /**
     * druid数据库连接池
     *
     * @author 林选伟
     * @date 2020/11/30 22:37
     */
    @Bean(initMethod = "init")
    public DruidDataSource druidDataSource(DruidProperties druidProperties) {
        return DruidDatasourceFactory.createDruidDataSource(druidProperties);
    }

}
