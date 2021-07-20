
package x.trident.core.system.modular.notice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.message.api.MessageApi;
import x.trident.core.message.api.enums.MessageBusinessTypeEnum;
import x.trident.core.message.api.pojo.request.MessageSendRequest;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.system.api.exception.SystemModularException;
import x.trident.core.system.api.exception.enums.notice.NoticeExceptionEnum;
import x.trident.core.system.api.pojo.notice.SysNoticeRequest;
import x.trident.core.system.modular.notice.entity.SysNotice;
import x.trident.core.system.modular.notice.mapper.SysNoticeMapper;
import x.trident.core.system.modular.notice.service.SysNoticeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 通知表 服务实现类
 *
 * @author 林选伟
 * @date 2021/1/8 22:45
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    private static final String NOTICE_SCOPE_ALL = "all";

    @Resource
    private MessageApi messageApi;

    @Override
    public void add(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = new SysNotice();
        BeanUtil.copyProperties(sysNoticeRequest, sysNotice);
        if (StrUtil.isBlank(sysNotice.getNoticeScope())) {
            sysNotice.setNoticeScope(NOTICE_SCOPE_ALL);
        }

        // 如果保存成功调用发送消息
        if (this.save(sysNotice)) {
            sendMessage(sysNotice);
        }
    }

    @Override
    public void del(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.querySysNoticeById(sysNoticeRequest);
        // 逻辑删除
        sysNotice.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysNotice);
    }

    @Override
    public void edit(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.querySysNoticeById(sysNoticeRequest);

        // 通知范围不允许修改， 如果通知范围不同抛出异常
        if (!sysNoticeRequest.getNoticeScope().equals(sysNotice.getNoticeScope())) {
            throw new SystemModularException(NoticeExceptionEnum.NOTICE_SCOPE_NOT_EDIT);
        }

        // 获取通知范围，如果为空则设置为all
        String noticeScope = sysNotice.getNoticeScope();
        if (StrUtil.isBlank(noticeScope)) {
            sysNoticeRequest.setNoticeScope(NOTICE_SCOPE_ALL);
        }

        // 更新属性
        BeanUtil.copyProperties(sysNoticeRequest, sysNotice);

        // 修改成功后发送信息
        if (this.updateById(sysNotice)) {
            sendMessage(sysNotice);
        }
    }

    @Override
    public SysNotice detail(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> queryWrapper = this.createWrapper(sysNoticeRequest);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageResult<SysNotice> findPage(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> wrapper = createWrapper(sysNoticeRequest);
        Page<SysNotice> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysNotice> findList(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> wrapper = createWrapper(sysNoticeRequest);
        return this.list(wrapper);
    }

    /**
     * 获取通知管理
     *
     * @author 林选伟
     * @date 2021/1/9 16:56
     */
    private SysNotice querySysNoticeById(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.getById(sysNoticeRequest.getNoticeId());
        if (ObjectUtil.isNull(sysNotice)) {
            throw new SystemModularException(NoticeExceptionEnum.NOTICE_NOT_EXIST, sysNoticeRequest.getNoticeId());
        }
        return sysNotice;
    }

    /**
     * 创建wrapper
     *
     * @author 林选伟
     * @date 2020/1/9 16:16
     */
    private LambdaQueryWrapper<SysNotice> createWrapper(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();

        // 查询未删除状态的
        queryWrapper.eq(SysNotice::getDelFlag, YesOrNotEnum.N.getCode());

        if (ObjectUtil.isEmpty(sysNoticeRequest)) {
            return queryWrapper;
        }

        // 通知id
        Long noticeId = sysNoticeRequest.getNoticeId();

        // 通知标题
        String noticeTitle = sysNoticeRequest.getNoticeTitle();

        // 拼接sql 条件
        queryWrapper.like(ObjectUtil.isNotEmpty(noticeTitle), SysNotice::getNoticeTitle, noticeTitle);
        queryWrapper.eq(ObjectUtil.isNotEmpty(noticeId), SysNotice::getNoticeId, noticeId);

        return queryWrapper;
    }

    /**
     * 发送消息
     *
     * @author 林选伟
     * @date 2021/2/8 19:30
     */
    private void sendMessage(SysNotice sysNotice) {
        MessageSendRequest message = new MessageSendRequest();

        // 消息标题
        message.setMessageTitle(sysNotice.getNoticeTitle());

        // 消息内容
        message.setMessageContent(sysNotice.getNoticeSummary());

        // 消息优先级
        message.setPriorityLevel(sysNotice.getPriorityLevel());

        // 消息发送范围
        message.setReceiveUserIds(sysNotice.getNoticeScope());

        // 消息业务类型
        message.setBusinessType(MessageBusinessTypeEnum.SYS_NOTICE.getCode());

        message.setBusinessId(sysNotice.getNoticeId());
        message.setMessageSendTime(new Date());

        try {
            messageApi.sendMessage(message);
        } catch (Exception exception) {
            // 发送失败打印异常
            log.error("发送消息失败:", exception);
        }
    }

}
