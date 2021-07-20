
package x.trident.core.pinyin.api.exception.enums;

import lombok.Getter;
import x.trident.core.constants.BaseConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.pinyin.api.constants.PinyinConstants;

/**
 * 拼音工具相关异常
 *
 * @author 林选伟
 * @date 2020/12/4 9:32
 */
@Getter
public enum PinyinExceptionEnum implements AbstractExceptionEnum {

    /**
     * 字符不能转成汉语拼音
     */
    PARSE_ERROR(BaseConstants.THIRD_ERROR_TYPE_CODE + PinyinConstants.PINYIN_EXCEPTION_STEP_CODE + "01", "拼音转化异常，具体信息：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    PinyinExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
