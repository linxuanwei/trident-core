
package x.trident.core.dict.modular.controller;

import x.trident.core.dict.api.constants.DictConstants;
import x.trident.core.dict.modular.entity.SysDictType;
import x.trident.core.dict.modular.pojo.request.DictTypeRequest;
import x.trident.core.dict.modular.service.DictTypeService;
import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.pojo.response.ResponseData;
import x.trident.core.pojo.response.SuccessResponseData;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 字典类型管理
 *
 * @author 林选伟
 * @date 2020/10/30 21:46
 */
@RestController
@ApiResource(name = "字典类型管理")
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    /**
     * 添加字典类型
     *
     * @author 林选伟
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "添加字典类型", path = "/dictType/add", requiredPermission = false)
    public ResponseData add(@RequestBody @Validated(DictTypeRequest.add.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.add(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除字典类型
     *
     * @author 林选伟
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "删除字典类型", path = "/dictType/delete", requiredPermission = false)
    public ResponseData delete(@RequestBody @Validated(DictTypeRequest.delete.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.del(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改字典类型
     *
     * @author 林选伟
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型", path = "/dictType/edit", requiredPermission = false)
    public ResponseData edit(@RequestBody @Validated(DictTypeRequest.edit.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.edit(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改字典类型状态
     *
     * @author 林选伟
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型状态", path = "/dictType/updateStatus", requiredPermission = false)
    public ResponseData updateStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.editStatus(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取字典类型详情
     *
     * @author 林选伟
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取字典类型详情", path = "/dictType/detail", requiredPermission = false)
    public ResponseData detail(@Validated(BaseRequest.detail.class) DictTypeRequest dictTypeRequest) {
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 获取字典类型列表
     *
     * @author 林选伟
     * @date 2020/10/30 21:46
     */
    @GetResource(name = "获取字典类型列表", path = "/dictType/list", requiredPermission = false)
    public ResponseData list(DictTypeRequest dictTypeRequest) {
        return new SuccessResponseData(dictTypeService.findList(dictTypeRequest));
    }

    /**
     * 获取字典类型列表(分页)
     *
     * @author 林选伟
     * @date 2020/10/30 21:46
     */
    @GetResource(name = "获取字典类型列表(分页)", path = "/dictType/page", requiredPermission = false)
    public ResponseData page(DictTypeRequest dictTypeRequest) {
        return new SuccessResponseData(dictTypeService.findPage(dictTypeRequest));
    }

    /**
     * 获取字典类型详情
     *
     * @author 林选伟
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取系统配置字典类型详情", path = "/dictType/getConfigDictTypeDetail", requiredPermission = false)
    public ResponseData getConfigDictTypeDetail(DictTypeRequest dictTypeRequest) {
        dictTypeRequest.setDictTypeCode(DictConstants.CONFIG_GROUP_DICT_TYPE_CODE);
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 获取字典类型详情
     *
     * @author 林选伟
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取语种字典类型型详情", path = "/dictType/getTranslationDetail", requiredPermission = false)
    public ResponseData getTranslationDetail(DictTypeRequest dictTypeRequest) {
        dictTypeRequest.setDictTypeCode(DictConstants.LANGUAGES_DICT_TYPE_CODE);
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData(detail);
    }

}
