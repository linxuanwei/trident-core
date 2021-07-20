
package x.trident.core.log.api.pojo.manage;

import x.trident.core.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 日志管理的查询参数
 *
 * @author 林选伟
 * @date 2020/10/28 11:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogManagerRequest extends BaseRequest {

    /**
     * 单条日志id
     */
    @NotNull(message = "日志id不能为空", groups = {detail.class})
    private Long logId;

    /**
     * 查询的起始时间
     */
    @NotBlank(message = "起始时间不能为空", groups = {delete.class})
    private String beginDate;

    /**
     * 查询日志的结束时间
     */
    @NotBlank(message = "结束时间不能为空", groups = {delete.class})
    private String endDate;

    /**
     * 日志的名称，一般为业务名称
     */
    private String logName;

    /**
     * 服务名称，一般为spring.application.name
     */
    @NotBlank(message = "服务名称不能为空", groups = {delete.class})
    private String appName;

    /**
     * 当前服务器的ip
     */
    private String serverIp;

    /**
     * 客户端请求的用户id
     */
    private Long userId;

    /**
     * 客户端的ip
     */
    private String clientIp;

    /**
     * 当前用户请求的url
     */
    private String requestUrl;

}
