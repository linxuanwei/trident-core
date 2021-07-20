
package x.trident.core.mongodb.file.mapper;

import x.trident.core.mongodb.file.entity.MongoFileEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongodb 文件存储Mapper
 *
 * @author huziyang
 * @date 2021/03/26 17:27
 */
@Configuration
public interface MongoFileMapper extends MongoRepository<MongoFileEntity,String> {
}
