
package x.trident.core.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Mongodb 数据存储集合实体
 *
 * @author huziyang
 * @date 2021/03/20 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "guns_map")
public class GunsMapEntity {

    @Id
    private String _id;

    private Map<String, Object> data = new HashMap<>();

}
