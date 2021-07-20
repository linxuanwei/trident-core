
package x.trident.core.i18n.modular.service;

import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.i18n.api.TranslationPersistenceApi;
import x.trident.core.i18n.api.pojo.request.TranslationRequest;
import x.trident.core.i18n.modular.entity.Translation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 多语言表 服务类
 *
 * @author 林选伟
 * @date 2021/1/24 19:21
 */
public interface TranslationService extends IService<Translation>, TranslationPersistenceApi {

    /**
     * 新增
     *
     * @param translationRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(TranslationRequest translationRequest);

    /**
     * 删除
     *
     * @param translationRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void del(TranslationRequest translationRequest);

    /**
     * 修改
     *
     * @param translationRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(TranslationRequest translationRequest);

    /**
     * 查询-详情-根据主键id
     *
     * @param translationRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    Translation detail(TranslationRequest translationRequest);

    /**
     * 查询-列表-按实体对象
     *
     * @param translationRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    List<Translation> findList(TranslationRequest translationRequest);

    /**
     * 查询-列表-分页-按实体对象
     *
     * @param translationRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    PageResult<Translation> findPage(TranslationRequest translationRequest);

    /**
     * 删除语种
     *
     * @param translationRequest 参数对象
     * @author 林选伟
     * @date 2021/1/30 10:00
     */
    void deleteTranLanguage(TranslationRequest translationRequest);

}
