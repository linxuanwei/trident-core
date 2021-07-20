
package x.trident.core.system.modular.menu.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.menu.SysMenuButtonRequest;
import x.trident.core.system.modular.menu.entity.SysMenuButton;
import x.trident.core.system.modular.menu.service.SysMenuButtonService;

import javax.annotation.Resource;

/**
 * 系统菜单按钮控制器
 *
 * @author 林选伟
 * @date 2021/1/9 16:08
 */
@RestController
@ApiResource(name = "菜单按钮管理")
public class SysMenuButtonController {

    @Resource
    private SysMenuButtonService sysMenuButtonService;

    /**
     * 添加系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     */
    @PostResource(name = "添加系统菜单按钮", path = "/sysMenuButton/add")
    public ResponseData add(@RequestBody @Validated(SysMenuButtonRequest.add.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.add(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 添加系统菜单按钮-默认按钮
     */
    @PostResource(name = "添加系统默认菜单按钮", path = "/sysMenuButton/addSystemDefaultButton")
    public ResponseData addSystemDefaultButton(@RequestBody @Validated(SysMenuButtonRequest.def.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.addDefaultButtons(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除单个系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮id
     */
    @PostResource(name = "删除单个系统菜单按钮", path = "/sysMenuButton/delete")
    public ResponseData delete(@RequestBody @Validated(SysMenuButtonRequest.delete.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.del(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 批量删除多个系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮id集合
     */
    @PostResource(name = "批量删除多个系统菜单按钮", path = "/sysMenuButton/batchDelete")
    public ResponseData batchDelete(@RequestBody @Validated(SysMenuButtonRequest.batchDelete.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.delBatch(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     */
    @PostResource(name = "编辑系统菜单按钮", path = "/sysMenuButton/edit")
    public ResponseData edit(@RequestBody @Validated(SysMenuButtonRequest.edit.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.edit(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取菜单按钮详情
     *
     * @param sysMenuButtonRequest 菜单按钮id
     */
    @GetResource(name = "获取菜单按钮详情", path = "/sysMenuButton/detail", responseClass = SysMenuButton.class)
    public ResponseData detail(@Validated(SysMenuButtonRequest.detail.class) SysMenuButtonRequest sysMenuButtonRequest) {
        SysMenuButton detail = sysMenuButtonService.detail(sysMenuButtonRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 获取菜单按钮列表
     *
     * @param sysMenuButtonRequest 菜单id
     */
    @GetResource(name = "获取菜单按钮列表", path = "/sysMenuButton/pageList", responseClass = SysMenuButton.class)
    public ResponseData pageList(@Validated(SysMenuButtonRequest.list.class) SysMenuButtonRequest sysMenuButtonRequest) {
        PageResult<SysMenuButton> pageResult = sysMenuButtonService.findPage(sysMenuButtonRequest);
        return new SuccessResponseData(pageResult);
    }

}
