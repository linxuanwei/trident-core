
package x.trident.core.mongodb.file.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.mongodb.api.MongoFileApi;
import x.trident.core.mongodb.file.entity.MongoFileEntity;
import x.trident.core.mongodb.file.mapper.MongoFileMapper;
import x.trident.core.mongodb.file.service.MongoFileService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * Mongodb 文件存储实现类
 *
 * @author huziyang
 * @date 2021/03/26 17:29
 */
@Slf4j
@Service
public class MongoFileServiceImpl implements MongoFileService, MongoFileApi<MongoFileEntity, String> {

    @Resource
    private MongoFileMapper mongoFileMapper;

    @Resource
    private GridFsTemplate gridFsTemplate;

    @Resource
    private GridFSBucket gridFSBucket;

    @Override
    public MongoFileEntity saveFile(MultipartFile file) {
        MongoFileEntity fileDocument = new MongoFileEntity();
        fileDocument.setName(file.getOriginalFilename());
        fileDocument.setUploadDate(new Date());
        try {
            // 填充登录用户的userId
            LoginUser loginUser = LoginContext.me().getLoginUser();
            fileDocument.setUploadUserId(loginUser.getUid());
        } catch (Exception e) {
            // 获取不到用户登录信息，就不填充
        }
        fileDocument.setSuffix(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        try {
            ObjectId store = gridFsTemplate.store(file.getInputStream(), IdUtil.simpleUUID(), file.getContentType());
            fileDocument.setGridfsId(String.valueOf(store));
            return mongoFileMapper.save(fileDocument);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return fileDocument;
    }

    @Override
    public void removeFile(String id) {
        Optional<MongoFileEntity> fileDocumentOptional = mongoFileMapper.findById(id);
        if (fileDocumentOptional.isPresent()) {
            mongoFileMapper.deleteById(id);
            gridFsTemplate.delete(new Query().addCriteria(Criteria.where("_id").is(fileDocumentOptional.get().getGridfsId())));
        }
    }

    @Override
    public Optional<MongoFileEntity> getFileById(String id) {
        Optional<MongoFileEntity> fileDocumentOptional = mongoFileMapper.findById(id);
        if (fileDocumentOptional.isPresent()) {
            MongoFileEntity fileDocument = fileDocumentOptional.get();
            Query gridQuery = new Query().addCriteria(Criteria.where("_id").is(fileDocument.getGridfsId()));
            GridFSFile fsFile = gridFsTemplate.findOne(gridQuery);
            GridFSDownloadStream in = gridFSBucket.openDownloadStream(fsFile.getObjectId());
            try {
                if (in.getGridFSFile().getLength() > 0) {
                    GridFsResource resource = new GridFsResource(fsFile, in);
                    fileDocument.setContent(IoUtil.readBytes(resource.getInputStream()));
                    return Optional.of(fileDocument);
                } else {
                    return Optional.empty();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public PageResult<MongoFileEntity> getFilesByPage(MongoFileEntity fileDocument) {
        Integer pageIndex = fileDocument.getPageNo();
        Integer pageSize = fileDocument.getPageSize();
        Sort sort = Sort.by(Sort.Direction.DESC, "uploadDate");
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize, sort);
        Example<MongoFileEntity> example = Example.of(fileDocument, ExampleMatcher.matching()
                .withIgnoreCase(true)
                .withIgnorePaths("_class", "pageSize", "pageNo", "content")
        );
        Page<MongoFileEntity> all = mongoFileMapper.findAll(example, pageRequest);
        return PageResultFactory.createPageResult(all.getContent(), mongoFileMapper.count(example), pageSize, pageIndex);
    }

}
