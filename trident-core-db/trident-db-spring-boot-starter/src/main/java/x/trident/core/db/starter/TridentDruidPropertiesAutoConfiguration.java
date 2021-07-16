package x.trident.core.db.starter;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.db.api.pojo.druid.DruidProperties;

/**
 * 数据库连接和DAO框架的配置
 *
 * @author 林选伟
 * @date 2020/11/30 22:24
 */
@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class TridentDruidPropertiesAutoConfiguration {

    /**
     * druid属性配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnMissingBean(DruidProperties.class)
    public DruidProperties druidProperties() {
        return new DruidProperties();
    }

}
