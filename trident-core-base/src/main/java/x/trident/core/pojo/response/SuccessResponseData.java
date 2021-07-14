package x.trident.core.pojo.response;

import static x.trident.core.constants.BaseConstants.SUCCESS_CODE;
import static x.trident.core.constants.BaseConstants.SUCCESS_MESSAGE;


/**
 * 响应成功的封装类
 *
 * @author 林选伟
 * @date 2020/10/16 16:23
 */
public class SuccessResponseData extends ResponseData {

    public SuccessResponseData() {
        super(Boolean.TRUE, SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public SuccessResponseData(Object object) {
        super(Boolean.TRUE, SUCCESS_CODE, SUCCESS_MESSAGE, object);
    }

    public SuccessResponseData(String code, String message, Object object) {
        super(Boolean.TRUE, code, message, object);
    }
}
