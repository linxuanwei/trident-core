
package x.trident.core.datasource.sdk.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import x.trident.core.db.api.factory.DruidDatasourceFactory;
import x.trident.core.db.api.pojo.druid.DruidProperties;
import x.trident.core.datasource.api.exception.DatasourceContainerException;
import x.trident.core.datasource.api.exception.enums.DatasourceContainerExceptionEnum;
import x.trident.core.datasource.sdk.context.DataSourceContext;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import x.trident.core.listener.ContextInitializedListener;


/**
 * 多数据源的初始化，加入到数据源Context中的过程
 *
 * @author 林选伟
 * @date 2020/11/1 0:02
 */
@Slf4j
public class DataSourceInitListener extends ContextInitializedListener implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {

        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        // 获取数据库连接配置
        String dataSourceDriver = environment.getProperty("spring.datasource.driver-class-name");
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        String dataSourceUsername = environment.getProperty("spring.datasource.username");
        String dataSourcePassword = environment.getProperty("spring.datasource.password");

        // 如果有为空的配置，终止执行
        if (ObjectUtil.hasEmpty(dataSourceUrl, dataSourceUsername, dataSourcePassword)) {
            String userTip = StrUtil.format(DatasourceContainerExceptionEnum.DB_CONNECTION_INFO_EMPTY_ERROR.getUserTip(), dataSourceUrl, dataSourceUsername);
            throw new DatasourceContainerException(DatasourceContainerExceptionEnum.DB_CONNECTION_INFO_EMPTY_ERROR, userTip);
        }

        // 创建主数据源的properties
        DruidProperties druidProperties = new DruidProperties();
        druidProperties.setDriverClassName(dataSourceDriver);
        druidProperties.setUrl(dataSourceUrl);
        druidProperties.setUsername(dataSourceUsername);
        druidProperties.setPassword(dataSourcePassword);

        // 创建主数据源
        DruidDataSource druidDataSource = DruidDatasourceFactory.createDruidDataSource(druidProperties);

        // 初始化数据源容器
        try {
            DataSourceContext.initDataSource(druidProperties, druidDataSource);
        } catch (Exception exception) {
            log.error("初始化数据源容器错误!", exception);
            String userTip = StrUtil.format(DatasourceContainerExceptionEnum.INIT_DATASOURCE_CONTAINER_ERROR.getUserTip(), exception.getMessage());
            throw new DatasourceContainerException(DatasourceContainerExceptionEnum.INIT_DATASOURCE_CONTAINER_ERROR, userTip);
        }

    }

}
