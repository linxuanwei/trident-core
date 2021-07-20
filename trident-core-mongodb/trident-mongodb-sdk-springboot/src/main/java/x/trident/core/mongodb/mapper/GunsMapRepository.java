
package x.trident.core.mongodb.mapper;

import x.trident.core.mongodb.entity.GunsMapEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongodb 数据存储mapper
 *
 * @author huziyang
 * @date 2021/03/20 16:24
 */
@Configuration
public interface GunsMapRepository extends MongoRepository<GunsMapEntity,String> {
}