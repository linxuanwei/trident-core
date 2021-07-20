
package x.trident.core.mongodb.starter;

import x.trident.core.mongodb.api.MongoFileApi;
import x.trident.core.mongodb.api.MongodbApi;
import x.trident.core.mongodb.entity.GunsMapEntity;
import x.trident.core.mongodb.file.entity.MongoFileEntity;
import x.trident.core.mongodb.file.service.impl.MongoFileServiceImpl;
import x.trident.core.mongodb.service.impl.GunsMapServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mongodb模块自动配置
 *
 * @author huziyang
 * @date 2021/03/20 16:24
 */
@Configuration
public class TridentMongodbAutoConfiguration {

    /**
     * Mongodb 数据存储
     *
     * @author huziyang
     * @date 2021/03/20 16:24
     */
    @Bean
    public MongodbApi<GunsMapEntity, String> mongodbApi() {
        return new GunsMapServiceImpl();
    }

    /**
     * Mongodb 文件管理
     *
     * @author huziyang
     * @date 2021/03/20 16:24
     */
    @Bean
    public MongoFileApi<MongoFileEntity, String> mongoFileApi() {
        return new MongoFileServiceImpl();
    }

}

