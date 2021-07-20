
package x.trident.core.mongodb.service.impl;

import x.trident.core.mongodb.api.MongodbApi;
import x.trident.core.mongodb.entity.GunsMapEntity;
import x.trident.core.mongodb.mapper.GunsMapRepository;
import x.trident.core.mongodb.service.GunsMapService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Mongodb 数据存储实现类
 *
 * @author huziyang
 * @date 2021/03/20 16:24
 */
@Service
public class GunsMapServiceImpl implements GunsMapService, MongodbApi<GunsMapEntity, String> {

    @Resource
    private GunsMapRepository gunsMapRepository;

    @Override
    public GunsMapEntity insert(GunsMapEntity gunsMapEntity) {
        return gunsMapRepository.insert(gunsMapEntity);
    }

    @Override
    public GunsMapEntity update(GunsMapEntity gunsMapEntity) {
        return gunsMapRepository.save(gunsMapEntity);
    }

    @Override
    public void deleteById(String id) {
        gunsMapRepository.deleteById(id);
    }

    @Override
    public Optional<GunsMapEntity> findById(String id) {
        return gunsMapRepository.findById(id);
    }

    @Override
    public List<GunsMapEntity> findAll() {
        return gunsMapRepository.findAll();
    }

}
