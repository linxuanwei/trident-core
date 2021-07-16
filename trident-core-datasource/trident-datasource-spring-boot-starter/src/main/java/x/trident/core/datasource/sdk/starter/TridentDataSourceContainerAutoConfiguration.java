package x.trident.core.datasource.sdk.starter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.datasource.sdk.DynamicDataSource;
import x.trident.core.datasource.sdk.aop.MultiSourceExchangeAop;

/**
 * 数据库连接和DAO框架的配置
 * <p>
 * 如果开启此连接池，注意排除 TridentDataSourceAutoConfiguration
 *
 * @author 林选伟
 * @date 2020/11/30 22:24
 */
@Configuration
public class TridentDataSourceContainerAutoConfiguration {

    /**
     * 多数据源连接池，如果开启此连接池，注意排除 TridentDataSourceAutoConfiguration
     */
    @Bean
    public DynamicDataSource dataSource() {
        return new DynamicDataSource();
    }

    /**
     * 数据源切换的AOP
     */
    @Bean
    public MultiSourceExchangeAop multiSourceExchangeAop() {
        return new MultiSourceExchangeAop();
    }

}
