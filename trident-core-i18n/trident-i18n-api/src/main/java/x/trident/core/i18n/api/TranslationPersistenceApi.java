
package x.trident.core.i18n.api;

import x.trident.core.i18n.api.pojo.TranslationDict;

import java.util.List;

/**
 * 多语言字典持久化api
 *
 * @author 林选伟
 * @date 2021/1/24 19:32
 */
public interface TranslationPersistenceApi {

    /**
     * 获取所有的翻译项
     *
     * @author 林选伟
     * @date 2021/1/24 19:33
     */
    List<TranslationDict> getAllTranslationDict();

}
