
package x.trident.core.system.api.pojo.notice;

import x.trident.core.pojo.request.BaseRequest;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统通知参数
 *
 * @author 林选伟
 * @date 2021/1/8 21:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysNoticeRequest extends BaseRequest {

    /**
     * 通知id
     */
    @NotNull(message = "noticeId不能为空", groups = {edit.class, delete.class, detail.class})
    @ChineseDescription("通知id")
    private Long noticeId;

    /**
     * 通知标题
     */
    @NotBlank(message = "通知标题不能为空", groups = {add.class, edit.class})
    @ChineseDescription("通知标题")
    private String noticeTitle;

    /**
     * 通知摘要
     */
    @ChineseDescription("通知摘要")
    private String noticeSummary;

    /**
     * 通知优先级
     */
    @NotBlank(message = "通知优先级不能为空", groups = {add.class, edit.class})
    @ChineseDescription("通知优先级")
    private String priorityLevel;


    /**
     * 通知开始时间
     */
    @NotNull(message = "通知开始时间不能为空", groups = {add.class, edit.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ChineseDescription("通知开始时间")
    private Date noticeBeginTime;


    /**
     * 通知结束时间
     */
    @NotNull(message = "通知开始时间不能为空", groups = {add.class, edit.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ChineseDescription("通知结束时间")
    private Date noticeEndTime;

    /**
     * 通知内容
     */
    @ChineseDescription("通知内容")
    private String noticeContent;

    /**
     * 通知范围
     */
    @ChineseDescription("通知范围")
    private String noticeScope;

}
