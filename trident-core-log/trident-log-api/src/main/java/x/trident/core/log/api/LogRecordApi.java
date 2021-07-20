
package x.trident.core.log.api;

import x.trident.core.log.api.pojo.record.LogRecordDTO;

import java.util.List;

/**
 * 日志记录的api，只用于记录日志
 *
 * @author 林选伟
 * @date 2020/10/27 16:19
 */
public interface LogRecordApi {

    /**
     * 同步记录日志
     *
     * @param logRecordDTO 日志记录的参数
     */
    void add(LogRecordDTO logRecordDTO);

    /**
     * 异步同步记录日志
     * 调用本方法直接返回结果之后再异步记录日志
     *
     * @param logRecordDTO 日志记录的参数
     */
    void addAsync(LogRecordDTO logRecordDTO);

    /**
     * 批量同步记录日志
     *
     * @param logRecords 待输出日志列表
     */
    void addBatch(List<LogRecordDTO> logRecords);

}
