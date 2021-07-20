
package x.trident.core.system.modular.loginlog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.log.api.LoginLogServiceApi;
import x.trident.core.log.api.pojo.loginlog.SysLoginLogDto;
import x.trident.core.log.api.pojo.loginlog.SysLoginLogRequest;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.util.HttpServletUtil;
import x.trident.core.system.api.UserServiceApi;
import x.trident.core.system.api.exception.enums.log.LogExceptionEnum;
import x.trident.core.system.api.pojo.user.SysUserDTO;
import x.trident.core.system.modular.loginlog.constants.LoginLogConstant;
import x.trident.core.system.modular.loginlog.entity.SysLoginLog;
import x.trident.core.system.modular.loginlog.mapper.SysLoginLogMapper;
import x.trident.core.system.modular.loginlog.service.SysLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * 系统应用service接口实现类
 *
 * @author 林选伟
 * @date 2020/3/13 16:15
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService, LoginLogServiceApi {

    @Resource
    private UserServiceApi userServiceApi;

    @Override
    public void del(SysLoginLogRequest sysLoginLogRequest) {
        SysLoginLog sysLoginLog = this.querySysLoginLogById(sysLoginLogRequest);
        this.removeById(sysLoginLog.getLlgId());
    }

    @Override
    public SysLoginLog detail(SysLoginLogRequest sysLoginLogRequest) {
        LambdaQueryWrapper<SysLoginLog> queryWrapper = this.createWrapper(sysLoginLogRequest);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageResult<SysLoginLogDto> findPage(SysLoginLogRequest sysLoginLogRequest) {
        LambdaQueryWrapper<SysLoginLog> wrapper = this.createWrapper(sysLoginLogRequest);
        Page<SysLoginLog> page = this.page(PageFactory.defaultPage(), wrapper);

        ArrayList<SysLoginLogDto> sysLoginLogDtos = new ArrayList<>();
        for (SysLoginLog record : page.getRecords()) {
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            BeanUtil.copyProperties(record, sysLoginLogDto);

            // 填充用户姓名
            SysUserDTO userInfoByUserId = userServiceApi.getUserInfoByUserId(sysLoginLogDto.getUserId());
            if (userInfoByUserId != null) {
                sysLoginLogDto.setUserName(userInfoByUserId.getRealName());
            }
            sysLoginLogDtos.add(sysLoginLogDto);
        }

        return PageResultFactory.createPageResult(sysLoginLogDtos, page.getTotal(), Convert.toInt(page.getSize()), Convert.toInt(page.getCurrent()));
    }

    @Override
    public void add(SysLoginLogRequest sysLoginLogRequest) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        BeanUtil.copyProperties(sysLoginLogRequest, sysLoginLog);
        this.save(sysLoginLog);
    }

    @Override
    public void loginSuccess(Long userId) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLlgName(LoginLogConstant.LOGIN_IN_LOGINNAME);
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLlgIpAddress(HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()));
        sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_SUCCESS);
        sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_IN_SUCCESS_MESSAGE);
        this.save(sysLoginLog);
    }

    @Override
    public void loginFail(Long userId, String llgMessage) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLlgName(LoginLogConstant.LOGIN_IN_LOGINNAME);
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLlgIpAddress(HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()));
        sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_FAIL);
        sysLoginLog.setLlgMessage(llgMessage);
        this.save(sysLoginLog);
    }

    @Override
    public void loginOutSuccess(Long userId) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLlgName(LoginLogConstant.LOGIN_OUT_LOGINNAME);
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLlgIpAddress(HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()));
        sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_SUCCESS);
        sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_OUT_SUCCESS_MESSAGE);
        this.save(sysLoginLog);
    }

    @Override
    public void loginOutFail(Long userId) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLlgName(LoginLogConstant.LOGIN_OUT_LOGINNAME);
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLlgIpAddress(HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()));
        sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_FAIL);
        sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_OUT_SUCCESS_FAIL);
        this.save(sysLoginLog);
    }

    @Override
    public void delAll() {
        this.remove(null);
    }

    /**
     * 获取详细信息
     *
     * @author 林选伟
     * @date 2021/1/13 10:50
     */
    private SysLoginLog querySysLoginLogById(SysLoginLogRequest sysLoginLogRequest) {
        SysLoginLog sysLoginLog = this.getById(sysLoginLogRequest.getLlgId());
        if (ObjectUtil.isNull(sysLoginLog)) {
            throw new ServiceException(LogExceptionEnum.LOG_NOT_EXIST);
        }
        return sysLoginLog;
    }

    /**
     * 构建wrapper
     *
     * @author 林选伟
     * @date 2021/1/13 10:50
     */
    private LambdaQueryWrapper<SysLoginLog> createWrapper(SysLoginLogRequest sysLoginLogRequest) {
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isEmpty(sysLoginLogRequest)) {
            return queryWrapper;
        }

        Date beginDate = null;
        if (StrUtil.isNotBlank(sysLoginLogRequest.getBeginTime())) {
            beginDate = DateUtil.parseDate(sysLoginLogRequest.getBeginTime()).toJdkDate();
        }
        Date endDate = null;
        if (StrUtil.isNotBlank(sysLoginLogRequest.getEndTime())) {
            endDate = DateUtil.parseDate(sysLoginLogRequest.getEndTime()).toJdkDate();
        }

        // SQL条件拼接
        queryWrapper.eq(StrUtil.isNotBlank(sysLoginLogRequest.getLlgName()), SysLoginLog::getLlgName, sysLoginLogRequest.getLlgName());
        queryWrapper.ge(StrUtil.isNotBlank(sysLoginLogRequest.getBeginTime()), SysLoginLog::getCreateTime, beginDate);
        queryWrapper.le(StrUtil.isNotBlank(sysLoginLogRequest.getEndTime()), SysLoginLog::getCreateTime, endDate);

        return queryWrapper;
    }

}
