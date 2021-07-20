
package x.trident.core.system.modular.organization.controller;

import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.system.api.pojo.organization.HrPositionRequest;
import x.trident.core.system.modular.organization.entity.HrPosition;
import x.trident.core.system.modular.organization.service.HrPositionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统职位控制器
 *
 * @author chenjinlong
 * @date 2020/11/18 21:56
 */
@RestController
@ApiResource(name = "系统职位管理")
public class HrPositionController {

    @Resource
    private HrPositionService hrPositionService;

    /**
     * 添加系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "添加系统职位", path = "/hrPosition/add")
    public ResponseData add(@RequestBody @Validated(HrPositionRequest.add.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.add(hrPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "删除系统职位", path = "/hrPosition/delete")
    public ResponseData delete(@RequestBody @Validated(HrPositionRequest.delete.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.del(hrPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 批量删除系统职位
     *
     * @author 林选伟
     * @date 2021/4/8 13:50
     */
    @PostResource(name = "批量删除系统职位", path = "/hrPosition/batchDelete")
    public ResponseData batchDelete(@RequestBody @Validated(HrPositionRequest.batchDelete.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.batchDel(hrPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "编辑系统职位", path = "/hrPosition/edit")
    public ResponseData edit(@RequestBody @Validated(HrPositionRequest.edit.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.edit(hrPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 更新职位状态
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "更新职位状态", path = "/hrPosition/updateStatus")
    public ResponseData updateStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.changeStatus(hrPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看详情系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "查看详情系统职位", path = "/hrPosition/detail", responseClass = HrPosition.class)
    public ResponseData detail(@Validated(HrPositionRequest.detail.class) HrPositionRequest hrPositionRequest) {
        return new SuccessResponseData(hrPositionService.detail(hrPositionRequest));
    }

    /**
     * 分页查询系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "分页查询系统职位", path = "/hrPosition/page", responseClass = HrPosition.class)
    public ResponseData page(HrPositionRequest hrPositionRequest) {
        return new SuccessResponseData(hrPositionService.findPage(hrPositionRequest));
    }

    /**
     * 获取全部系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "获取全部系统职位", path = "/hrPosition/list", responseClass = HrPosition.class)
    public ResponseData list(HrPositionRequest hrPositionRequest) {
        return new SuccessResponseData(hrPositionService.findList(hrPositionRequest));
    }

}
