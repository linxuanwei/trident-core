package x.trident.core.datasource.business.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import x.trident.core.datasource.api.DataSourceApi;
import x.trident.core.datasource.api.pojo.request.DatabaseInfoRequest;
import x.trident.core.datasource.business.modular.entity.DatabaseInfo;
import x.trident.core.db.api.pojo.page.PageResult;

import java.util.List;

/**
 * 数据库信息表 服务类
 *
 * @author 林选伟
 * @date 2020/11/1 21:46
 */
public interface DatabaseInfoService extends IService<DatabaseInfo>, DataSourceApi {

    /**
     * 删除，删除会导致某些用该数据源的service操作失败
     *
     * @param databaseInfoRequest 删除参数
     * @author 林选伟
     * @date 2020/11/1 21:47
     */
    void del(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 编辑数据库信息
     *
     * @param databaseInfoRequest 编辑参数
     * @author 林选伟
     * @date 2020/11/1 21:47
     */
    void edit(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 查询数据库信息详情
     *
     * @param databaseInfoRequest 查询参数
     * @author 林选伟
     * @date 2021/1/23 20:30
     */
    DatabaseInfo detail(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 查询数据库信息
     *
     * @param databaseInfoRequest 查询参数
     * @return 查询分页结果
     * @author 林选伟
     * @date 2020/11/1 21:47
     */
    PageResult<DatabaseInfo> findPage(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 列表查询
     *
     * @param databaseInfoRequest 参数
     * @author liuhanqing
     * @date 2021/2/2 21:21
     */
    List<DatabaseInfo> findList(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 校验数据库连接的正确性
     *
     * @param param 参数
     * @author 林选伟
     * @date 2021/4/22 10:46
     */
    void validateConnection(DatabaseInfoRequest param);

}
