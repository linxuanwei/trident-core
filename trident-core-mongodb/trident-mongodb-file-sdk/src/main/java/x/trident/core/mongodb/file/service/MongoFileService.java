
package x.trident.core.mongodb.file.service;

import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.mongodb.file.entity.MongoFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Mongodb 文件存储接口
 *
 * @author huziyang
 * @date 2021/03/26 17:28
 */
public interface MongoFileService {

    /**
     * 保存文件
     *
     * @param file 上传的文件对象
     * @return 返回数据结果
     * @author huziyang
     * @date 2021/03/30 11:06
     */
    MongoFileEntity saveFile(MultipartFile file);

    /**
     * 根据id删除文件
     *
     * @param id 集合id
     * @author huziyang
     * @date 2021/03/30 11:06
     */
    void removeFile(String id);

    /**
     * 根据id获取文件
     *
     * @param id 集合id
     * @return 返回查询到数据的Optional
     * @author huziyang
     * @date 2021/03/30 11:06
     */
    Optional<MongoFileEntity> getFileById(String id);

    /**
     * 分页获取文件列表
     *
     * @param fileDocument 查询条件
     * @return 返回查询文件的分页结果
     * @author huziyang
     * @date 2021/03/30 11:06
     */
    PageResult<MongoFileEntity> getFilesByPage(MongoFileEntity fileDocument);

}
