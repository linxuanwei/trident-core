
package x.trident.core.system.modular.loginlog.service;

import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.log.api.pojo.loginlog.SysLoginLogDto;
import x.trident.core.log.api.pojo.loginlog.SysLoginLogRequest;
import x.trident.core.system.modular.loginlog.entity.SysLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 登录日志service接口
 *
 * @author 林选伟
 * @date 2021/1/13 10:56
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

    /**
     * 删除
     *
     * @param sysLoginLogRequest 参数
     * @author 林选伟
     * @date 2021/1/13 10:55
     */
    void del(SysLoginLogRequest sysLoginLogRequest);

    /**
     * 清空登录日志
     *
     * @author 林选伟
     * @date 2021/1/13 10:55
     */
    void delAll();

    /**
     * 查看相信
     *
     * @param sysLoginLogRequest 参数
     * @author 林选伟
     * @date 2021/1/13 10:56
     */
    SysLoginLog detail(SysLoginLogRequest sysLoginLogRequest);

    /**
     * 分页查询
     *
     * @param sysLoginLogRequest 参数
     * @author 林选伟
     * @date 2021/1/13 10:57
     */
    PageResult<SysLoginLogDto> findPage(SysLoginLogRequest sysLoginLogRequest);

}
