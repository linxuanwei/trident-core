
package x.trident.core.log.api.pojo.loginlog;

import lombok.Data;
import lombok.EqualsAndHashCode;
import x.trident.core.pojo.request.BaseRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 登录日志表
 *
 * @author 林选伟
 * @date 2021/1/13 11:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLoginLogRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "llgId不能为空", groups = {detail.class})
    private Long llgId;

    /**
     * 日志名称
     */
    private String llgName;

    /**
     * 是否执行成功
     */
    private String llgSucceed;

    /**
     * 具体消息
     */
    private String llgMessage;

    /**
     * 登录ip
     */
    private String llgIpAddress;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 开始时间
     */
    private Date createTime;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

}
