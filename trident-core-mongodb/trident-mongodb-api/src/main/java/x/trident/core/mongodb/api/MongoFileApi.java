
package x.trident.core.mongodb.api;

import x.trident.core.db.api.pojo.page.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Mongodb文件管理API
 *
 * @author huziyang
 * @date 2021/03/30 11:06
 */
public interface MongoFileApi<T, ID> {

    /**
     * 保存文件
     *
     * @param file 上传的文件对象
     * @return 返回数据结果
     * @author huziyang
     * @date 2021/03/30 11:06
     */
    T saveFile(MultipartFile file);

    /**
     * 根据id删除文件
     *
     * @param id 集合id
     * @author huziyang
     * @date 2021/03/30 11:06
     */
    void removeFile(ID id);

    /**
     * 根据id获取文件
     *
     * @param id 集合id
     * @return 返回查询到数据的Optional
     * @author huziyang
     * @date 2021/03/30 11:06
     */
    Optional<T> getFileById(ID id);

    /**
     * 分页获取文件列表
     *
     * @param fileDocument 查询条件
     * @return 返回查询文件的分页结果
     * @author huziyang
     * @date 2021/03/30 11:06
     */
    PageResult<T> getFilesByPage(T fileDocument);

}
