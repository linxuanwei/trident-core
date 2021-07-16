package x.trident.core.datasource.business.modular.controller;

import x.trident.core.db.api.pojo.page.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import x.trident.core.datasource.api.pojo.request.DatabaseInfoRequest;
import x.trident.core.datasource.business.modular.entity.DatabaseInfo;
import x.trident.core.datasource.business.modular.service.DatabaseInfoService;
import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;

import javax.annotation.Resource;
import java.util.List;


/**
 * 数据库信息表控制器
 *
 * @author 林选伟
 * @date 2020/11/1 22:15
 */
@RestController
@ApiResource(name = "数据源信息管理")
public class DatabaseInfoController {

    @Resource
    private DatabaseInfoService databaseInfoService;

    /**
     * 新增数据源
     *
     * @author 林选伟
     * @date 2020/11/1 22:16
     */
    @PostResource(name = "新增数据源", path = "/databaseInfo/add")
    public ResponseData add(@RequestBody @Validated(BaseRequest.add.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.add(databaseInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除数据源
     *
     * @author 林选伟
     * @date 2020/11/1 22:18
     */
    @PostResource(name = "删除数据源", path = "/databaseInfo/delete")
    public ResponseData del(@RequestBody @Validated(DatabaseInfoRequest.delete.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.del(databaseInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑数据源
     *
     * @author 林选伟
     * @date 2020/11/1 22:16
     */
    @PostResource(name = "编辑数据源", path = "/databaseInfo/edit")
    public ResponseData edit(@RequestBody @Validated(DatabaseInfoRequest.edit.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.edit(databaseInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 查询数据源列表（带分页）
     *
     * @author 林选伟
     * @date 2020/11/1 22:18
     */
    @GetResource(name = "查询数据源列表（带分页）", path = "/databaseInfo/page")
    public ResponseData findPage(DatabaseInfoRequest databaseInfoRequest) {
        PageResult<DatabaseInfo> pageResult = databaseInfoService.findPage(databaseInfoRequest);
        return new SuccessResponseData(pageResult);
    }

    /**
     * 查询所有数据源列表
     *
     * @author 林选伟
     * @date 2020/11/1 22:18
     */
    @GetResource(name = "查询所有数据源列表", path = "/databaseInfo/list")
    public ResponseData findList(DatabaseInfoRequest databaseInfoRequest) {
        List<DatabaseInfo> databaseInfos = databaseInfoService.findList(databaseInfoRequest);
        return new SuccessResponseData(databaseInfos);
    }

    /**
     * 查询数据源详情
     *
     * @author 林选伟
     * @date 2021/1/23 20:29
     */
    @GetResource(name = "查询数据源详情", path = "/databaseInfo/detail")
    public ResponseData detail(@Validated(BaseRequest.detail.class) DatabaseInfoRequest databaseInfoRequest) {
        DatabaseInfo databaseInfo = databaseInfoService.detail(databaseInfoRequest);
        return new SuccessResponseData(databaseInfo);
    }

}


