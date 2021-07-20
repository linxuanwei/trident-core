
package x.trident.core.system.modular.loginlog.controller;

import x.trident.core.log.api.pojo.loginlog.SysLoginLogRequest;

import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.system.modular.loginlog.service.SysLoginLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登陆日志控制器
 *
 * @author 林选伟
 * @date 2021/1/13 17:51
 */
@RestController
@ApiResource(name = "登录日志")
public class SysLoginLogController {

    @Resource
    private SysLoginLogService sysLoginLogService;

    /**
     * 清空登录日志
     */
    @GetResource(name = "清空登录日志", path = "/loginLog/deleteAll")
    public ResponseData deleteAll() {
        sysLoginLogService.delAll();
        return new SuccessResponseData();
    }

    /**
     * 查询登录日志详情
     */
    @GetResource(name = "查看详情登录日志", path = "/loginLog/detail")
    public ResponseData detail(@Validated(SysLoginLogRequest.detail.class) SysLoginLogRequest sysLoginLogRequest) {
        return new SuccessResponseData(sysLoginLogService.detail(sysLoginLogRequest));
    }

    /**
     * 分页查询登录日志
     */
    @GetResource(name = "分页查询登录日志", path = "/loginLog/page")
    public ResponseData page(SysLoginLogRequest sysLoginLogRequest) {
        return new SuccessResponseData(sysLoginLogService.findPage(sysLoginLogRequest));
    }

}
