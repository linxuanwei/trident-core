
package x.trident.core.log.api.factory.appender;

import com.alibaba.fastjson.JSON;
import x.trident.core.log.api.pojo.record.LogRecordDTO;

import java.util.Map;

/**
 * 日志信息追加，用来追加方法的参数信息
 *
 * @author 林选伟
 * @date 2020/10/28 17:48
 */
public class ParamsLogAppender {
    private ParamsLogAppender() {
    }

    /**
     * 参数信息追加
     */
    public static void appendAuthedHttpLog(LogRecordDTO logRecordDTO, Map<String, Object> requestParam, Object response) {

        // 追加请求参数信息
        logRecordDTO.setRequestParams(JSON.toJSONString(requestParam));

        // 追加相应参数信息
        logRecordDTO.setRequestResult(JSON.toJSONString(response));
    }

}
