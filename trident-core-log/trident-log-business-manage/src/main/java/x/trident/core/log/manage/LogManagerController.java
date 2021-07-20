
package x.trident.core.log.manage;

import x.trident.core.log.api.LogManagerApi;
import x.trident.core.log.api.pojo.manage.LogManagerRequest;
import x.trident.core.log.db.service.SysLogService;
import x.trident.core.log.manage.wrapper.LogInfoWrapper;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.wrapper.api.annotation.Wrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 日志管理控制器
 *
 * @author 林选伟
 * @date 2020/11/3 12:44
 */
@RestController
@ApiResource(name = "日志管理控制器")
public class LogManagerController {

    /**
     * 日志管理api
     */
    @Resource
    private LogManagerApi logManagerApi;

    /**
     * 日志管理service
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 查询日志列表
     *
     * @author 林选伟
     * @date 2020/11/3 12:58
     */
    @GetResource(name = "查询日志列表", path = "/logManager/list")
    public ResponseData list(@RequestBody LogManagerRequest logManagerRequest) {
        return new SuccessResponseData(logManagerApi.findList(logManagerRequest));
    }

    /**
     * 查询日志
     *
     * @author tengshuqi
     * @date 2021/1/8 17:36
     */
    @GetResource(name = "查询日志列表", path = "/logManager/page")
    @Wrapper(LogInfoWrapper.class)
    public ResponseData page(LogManagerRequest logManagerRequest) {
        return new SuccessResponseData(logManagerApi.findPage(logManagerRequest));
    }

    /**
     * 删除日志
     *
     * @author 林选伟
     * @date 2020/11/3 13:47
     */
    @PostResource(name = "删除日志", path = "/logManager/delete")
    public ResponseData delete(@RequestBody @Validated(LogManagerRequest.delete.class) LogManagerRequest logManagerRequest) {
        sysLogService.delAll(logManagerRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看日志详情
     *
     * @author TSQ
     * @date 2021/1/11 17:36
     */
    @GetResource(name = "查看日志详情", path = "/logManager/detail")
    @Wrapper(LogInfoWrapper.class)
    public ResponseData detail(@Validated(LogManagerRequest.detail.class) LogManagerRequest logManagerRequest) {
        return new SuccessResponseData(logManagerApi.detail(logManagerRequest));
    }

}
