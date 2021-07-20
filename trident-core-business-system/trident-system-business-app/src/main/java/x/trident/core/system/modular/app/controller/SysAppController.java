
package x.trident.core.system.modular.app.controller;

import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.app.SysAppRequest;
import x.trident.core.system.modular.app.entity.SysApp;
import x.trident.core.system.modular.app.service.SysAppService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统应用控制器
 *
 * @author 林选伟
 * @date 2020/3/20 21:25
 */
@RestController
@ApiResource(name = "系统应用")
public class SysAppController {

    @Resource
    private SysAppService sysAppService;

    /**
     * 添加系统应用
     *
     * @author 林选伟
     * @date 2020/3/25 14:44
     */
    @PostResource(name = "添加系统应用", path = "/sysApp/add")
    public ResponseData add(@RequestBody @Validated(SysAppRequest.add.class) SysAppRequest sysAppParam) {
        sysAppService.add(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统应用
     *
     * @author 林选伟
     * @date 2020/3/25 14:54
     */
    @PostResource(name = "删除系统应用", path = "/sysApp/delete")
    public ResponseData delete(@RequestBody @Validated(SysAppRequest.delete.class) SysAppRequest sysAppParam) {
        sysAppService.del(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统应用
     *
     * @author 林选伟
     * @date 2020/3/25 14:54
     */
    @PostResource(name = "编辑系统应用", path = "/sysApp/edit")
    public ResponseData edit(@RequestBody @Validated(SysAppRequest.edit.class) SysAppRequest sysAppParam) {
        sysAppService.edit(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 修改应用状态
     *
     * @author 林选伟
     * @date 2020/6/29 16:49
     */
    @PostResource(name = "修改应用状态", path = "/sysApp/updateStatus")
    public ResponseData updateStatus(@RequestBody @Validated(SysAppRequest.updateStatus.class) SysAppRequest sysAppParam) {
        sysAppService.editStatus(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统应用
     *
     * @author 林选伟
     * @date 2020/3/26 9:49
     */
    @GetResource(name = "查看系统应用", path = "/sysApp/detail", responseClass = SysApp.class)
    public ResponseData detail(@Validated(SysAppRequest.detail.class) SysAppRequest sysAppParam) {
        return new SuccessResponseData(sysAppService.detail(sysAppParam));
    }

    /**
     * 系统应用列表
     *
     * @author 林选伟
     * @date 2020/4/19 14:55
     */
    @GetResource(name = "系统应用列表", path = "/sysApp/list", responseClass = SysApp.class)
    public ResponseData list(SysAppRequest sysAppParam) {
        return new SuccessResponseData(sysAppService.findList(sysAppParam));
    }

    /**
     * 查询系统应用
     *
     * @author 林选伟
     * @date 2020/3/20 21:23
     */
    @GetResource(name = "查询系统应用", path = "/sysApp/page")
    public ResponseData page(SysAppRequest sysAppParam) {
        return new SuccessResponseData(sysAppService.findPage(sysAppParam));
    }

    /**
     * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
     *
     * @author 林选伟
     * @date 2020/6/29 16:49
     */
    @PostResource(name = "设为默认应用", path = "/sysApp/updateActiveFlag")
    public ResponseData setAsDefault(@RequestBody @Validated(SysAppRequest.updateActiveFlag.class) SysAppRequest sysAppParam) {
        sysAppService.updateActiveFlag(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 获取应用列表，用于顶部应用列表
     *
     * @author 林选伟
     * @date 2021/4/21 15:31
     */
    @GetResource(name = "获取应用列表，用于顶部应用列表", path = "/sysMenu/getTopAppList")
    public ResponseData getTopAppList() {
        List<SysApp> userTopAppList = sysAppService.getUserTopAppList();
        return new SuccessResponseData(userTopAppList);
    }

}
