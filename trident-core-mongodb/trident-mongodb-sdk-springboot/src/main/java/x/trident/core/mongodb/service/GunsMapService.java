
package x.trident.core.mongodb.service;

import x.trident.core.mongodb.entity.GunsMapEntity;

import java.util.List;
import java.util.Optional;

/**
 * Mongodb 数据存储接口
 *
 * @author huziyang
 * @date 2021/03/20 16:24
 */
public interface GunsMapService {

    /**
     * 新增数据
     *
     * @param gunsMapEntity 数据参数
     * @return 返回新增数据结果
     * @author huziyang
     * @date 2021/03/20 16:24
     */
    GunsMapEntity insert(GunsMapEntity gunsMapEntity);

    /**
     * 修改数据
     *
     * @param gunsMapEntity 数据参数
     * @return 返回修改数据结果
     * @author huziyang
     * @date 2021/03/20 16:24
     */
    GunsMapEntity update(GunsMapEntity gunsMapEntity);

    /**
     * 根据id删除
     *
     * @param id 集合id
     * @author huziyang
     * @date 2021/03/20 16:24
     */
    void deleteById(String id);

    /**
     * 根据id查询
     *
     * @param id 集合id
     * @return 返回查询到数据的Optional
     * @author huziyang
     * @date 2021/03/20 16:24
     */
    Optional<GunsMapEntity> findById(String id);

    /**
     * 查询所有集合中数据
     *
     * @return 返回所有数据集合
     * @author huziyang
     * @date 2021/03/20 16:24
     */
    List<GunsMapEntity> findAll();

}