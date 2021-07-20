
package x.trident.core.pinyin.api.constants;

/**
 * 拼音模块常量
 *
 * @author 林选伟
 * @date 2020/12/3 19:24
 */
public class PinyinConstants {
    private PinyinConstants() {
    }

    /**
     * 邮件模块的名称
     */
    public static final String PINYIN_MODULE_NAME = "trident-util-pinyin";

    /**
     * 异常枚举的步进值
     */
    public static final String PINYIN_EXCEPTION_STEP_CODE = "22";

    /**
     * 中文字符的正则表达式
     */
    public static final String CHINESE_WORDS_REGEX = "[\u4E00-\u9FA5]+";

}
