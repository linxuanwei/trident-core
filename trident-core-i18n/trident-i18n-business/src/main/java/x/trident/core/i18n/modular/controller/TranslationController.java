
package x.trident.core.i18n.modular.controller;

import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.i18n.api.pojo.request.TranslationRequest;
import x.trident.core.i18n.modular.entity.Translation;
import x.trident.core.i18n.modular.service.TranslationService;
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
 * 多语言接口控制器
 *
 * @author 林选伟
 * @date 2021/1/24 19:18
 */
@RestController
@ApiResource(name = "多语言接口控制器")
public class TranslationController {

    @Resource
    private TranslationService translationService;

    /**
     * 新增多语言翻译记录
     *
     * @author 林选伟
     * @date 2021/1/24 19:17
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/add")
    public ResponseData add(@RequestBody @Validated(TranslationRequest.add.class) TranslationRequest translationRequest) {
        this.translationService.add(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑多语言翻译记录
     *
     * @author 林选伟
     * @date 2021/1/24 19:17
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/edit")
    public ResponseData edit(@RequestBody @Validated(BaseRequest.edit.class) TranslationRequest translationRequest) {
        this.translationService.edit(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除多语言配置
     *
     * @author 林选伟
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/delete")
    public ResponseData delete(@RequestBody @Validated(BaseRequest.delete.class) TranslationRequest translationRequest) {
        this.translationService.del(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除某个语种
     *
     * @author 林选伟
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "删除某个语种", path = "/i18n/deleteTranLanguage")
    public ResponseData deleteTranLanguage(@RequestBody @Validated(TranslationRequest.deleteTranLanguage.class) TranslationRequest translationRequest) {
        this.translationService.deleteTranLanguage(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看多语言详情
     *
     * @author 林选伟
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "新增多语言配置", path = "/i18n/detail")
    public ResponseData detail(@Validated(BaseRequest.detail.class) TranslationRequest translationRequest) {
        Translation detail = this.translationService.detail(translationRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 查看多语言配置列表
     *
     * @author 林选伟
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "新增多语言配置", path = "/i18n/page")
    public ResponseData page(TranslationRequest translationRequest) {
        PageResult<Translation> page = this.translationService.findPage(translationRequest);
        return new SuccessResponseData(page);
    }

}


