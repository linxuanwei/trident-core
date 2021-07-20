
package x.trident.core.system.modular.organization.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.enums.StatusEnum;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.system.api.UserOrgServiceApi;
import x.trident.core.system.api.exception.SystemModularException;
import x.trident.core.system.api.exception.enums.organization.PositionExceptionEnum;
import x.trident.core.system.api.pojo.organization.HrPositionRequest;
import x.trident.core.system.modular.organization.entity.HrPosition;
import x.trident.core.system.modular.organization.mapper.HrPositionMapper;
import x.trident.core.system.modular.organization.service.HrPositionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统职位表 服务实现类
 *
 * @author 林选伟
 * @date 2020/11/04 11:07
 */
@Service
public class HrPositionServiceImpl extends ServiceImpl<HrPositionMapper, HrPosition> implements HrPositionService {

    @Resource
    private UserOrgServiceApi userOrgServiceApi;

    @Override
    public void add(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = new HrPosition();
        BeanUtil.copyProperties(hrPositionRequest, sysPosition);

        // 设置状态为启用
        sysPosition.setStatusFlag(StatusEnum.ENABLE.getCode());

        this.save(sysPosition);
    }

    @Override
    public void del(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);

        // 该职位下是否有员工，如果有将不能删除
        Boolean userOrgFlag = userOrgServiceApi.getUserOrgFlag(null, sysPosition.getPositionId());
        if (userOrgFlag) {
            throw new SystemModularException(PositionExceptionEnum.CANT_DELETE_POSITION);
        }

        // 逻辑删除
        sysPosition.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysPosition);
    }

    @Override
    public void edit(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);
        BeanUtil.copyProperties(hrPositionRequest, sysPosition);
        this.updateById(sysPosition);
    }

    @Override
    public void changeStatus(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);
        sysPosition.setStatusFlag(hrPositionRequest.getStatusFlag());
        this.updateById(sysPosition);
    }

    @Override
    public HrPosition detail(HrPositionRequest hrPositionRequest) {
        return this.querySysPositionById(hrPositionRequest);
    }

    @Override
    public List<HrPosition> findList(HrPositionRequest hrPositionRequest) {
        return this.list(this.createWrapper(hrPositionRequest));
    }

    @Override
    public PageResult<HrPosition> findPage(HrPositionRequest hrPositionRequest) {
        LambdaQueryWrapper<HrPosition> wrapper = this.createWrapper(hrPositionRequest);

        Page<HrPosition> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public void batchDel(HrPositionRequest hrPositionRequest) {
        List<Long> positionIds = hrPositionRequest.getPositionIds();
        for (Long userId : positionIds) {
            HrPositionRequest tempRequest = new HrPositionRequest();
            tempRequest.setPositionId(userId);
            this.del(tempRequest);
        }
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author chenjinlong
     * @date 2021/2/2 10:16
     */
    private HrPosition querySysPositionById(HrPositionRequest hrPositionRequest) {
        HrPosition hrPosition = this.getById(hrPositionRequest.getPositionId());
        if (ObjectUtil.isEmpty(hrPosition) || YesOrNotEnum.Y.getCode().equals(hrPosition.getDelFlag())) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, hrPositionRequest.getPositionId());
        }
        return hrPosition;
    }


    /**
     * 实体构建 QueryWrapper
     *
     * @author chenjinlong
     * @date 2021/2/2 10:17
     */
    private LambdaQueryWrapper<HrPosition> createWrapper(HrPositionRequest hrPositionRequest) {
        LambdaQueryWrapper<HrPosition> queryWrapper = new LambdaQueryWrapper<>();

        Long positionId = hrPositionRequest.getPositionId();
        String positionName = hrPositionRequest.getPositionName();
        String positionCode = hrPositionRequest.getPositionCode();
        Integer statusFlag = hrPositionRequest.getStatusFlag();

        // SQL条件拼接
        queryWrapper.eq(ObjectUtil.isNotNull(positionId), HrPosition::getPositionId, positionId);
        queryWrapper.like(StrUtil.isNotEmpty(positionName), HrPosition::getPositionName, positionName);
        queryWrapper.eq(StrUtil.isNotEmpty(positionCode), HrPosition::getPositionCode, positionCode);
        queryWrapper.eq(ObjectUtil.isNotNull(statusFlag), HrPosition::getStatusFlag, statusFlag);

        // 查询未删除状态的
        queryWrapper.eq(HrPosition::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列
        queryWrapper.orderByAsc(HrPosition::getPositionSort);

        return queryWrapper;
    }

}
