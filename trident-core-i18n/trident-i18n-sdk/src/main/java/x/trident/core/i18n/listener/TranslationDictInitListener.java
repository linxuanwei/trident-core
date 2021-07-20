
package x.trident.core.i18n.listener;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.i18n.api.TranslationApi;
import x.trident.core.i18n.api.TranslationPersistenceApi;
import x.trident.core.i18n.api.pojo.TranslationDict;
import x.trident.core.listener.ApplicationStartedListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;

import java.util.List;

/**
 * 初始化多语言翻译详
 *
 * @author 林选伟
 * @date 2021/1/24 19:36
 */
@Slf4j
public class TranslationDictInitListener extends ApplicationStartedListener {

    @Override
    public void eventCallback(ApplicationStartedEvent event) {

        TranslationPersistenceApi tanTranslationPersistenceApi = SpringUtil.getBean(TranslationPersistenceApi.class);
        TranslationApi translationApi = SpringUtil.getBean(TranslationApi.class);

        // 从数据库读取翻译字典
        List<TranslationDict> allTranslationDict = tanTranslationPersistenceApi.getAllTranslationDict();
        if (allTranslationDict != null) {
            translationApi.init(allTranslationDict);
            log.info("初始化所有的翻译字典" + allTranslationDict.size() + "条！");
        }
    }

}
