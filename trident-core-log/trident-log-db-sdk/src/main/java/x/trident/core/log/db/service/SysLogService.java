
package x.trident.core.log.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.log.api.pojo.manage.LogManagerRequest;
import x.trident.core.log.db.entity.SysLog;

import java.util.List;

/**
 * 日志记录 service接口
 *
 * @author 林选伟
 * @date 2020/11/2 17:44
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 新增
     *
     * @param logManagerRequest 参数对象
     */
    void add(LogManagerRequest logManagerRequest);

    /**
     * 删除
     *
     * @param logManagerRequest 参数对象
     */
    void del(LogManagerRequest logManagerRequest);

    /**
     * 删除所有数据
     *
     * @param logManagerRequest 参数对象
     */
    void delAll(LogManagerRequest logManagerRequest);

    /**
     * 查看日志详情
     */
    SysLog detail(LogManagerRequest logManagerParam);

    /**
     * 查询-列表-按实体对象
     *
     * @param logManagerParam 参数对象
     */
    List<SysLog> findList(LogManagerRequest logManagerParam);

    /**
     * 查询-列表-分页-按实体对象
     *
     * @param logManagerParam 参数对象
     */
    PageResult<SysLog> findPage(LogManagerRequest logManagerParam);

}
