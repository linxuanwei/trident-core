
package x.trident.core.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import x.trident.core.system.api.exception.SystemModularException;
import x.trident.core.system.api.exception.enums.user.SysUserOrgExceptionEnum;
import x.trident.core.system.api.pojo.user.SysUserOrgDTO;
import x.trident.core.system.api.pojo.user.request.UserOrgRequest;
import x.trident.core.system.modular.user.entity.SysUserOrg;
import x.trident.core.system.modular.user.mapper.SysUserOrgMapper;
import x.trident.core.system.modular.user.service.SysUserOrgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 用户组织机构关联信息
 *
 * @author 林选伟
 * @date 2020/12/19 22:17
 */
@Service
public class SysUserOrgServiceServiceImpl extends ServiceImpl<SysUserOrgMapper, SysUserOrg> implements SysUserOrgService {

    @Override
    public SysUserOrgDTO getUserOrgByUserId(Long userId) {

        UserOrgRequest userOrgRequest = new UserOrgRequest();
        userOrgRequest.setUserId(userId);
        SysUserOrg sysUserOrg = this.detail(userOrgRequest);
        if (ObjectUtil.isEmpty(sysUserOrg)) {
            throw new SystemModularException(SysUserOrgExceptionEnum.EMPLOYEE_MANY_MAIN_NOT_FOUND);
        }
        SysUserOrgDTO sysUserOrgDTO = new SysUserOrgDTO();
        BeanUtil.copyProperties(sysUserOrg, sysUserOrgDTO);
        return sysUserOrgDTO;
    }


    @Override
    public void add(UserOrgRequest userOrgResponse) {
        SysUserOrg sysUserOrg = new SysUserOrg();
        BeanUtil.copyProperties(userOrgResponse, sysUserOrg);
        this.save(sysUserOrg);
    }

    @Override
    public void add(Long userId, Long orgId) {
        SysUserOrg sysUserOrg = new SysUserOrg();
        sysUserOrg.setUserId(userId);
        sysUserOrg.setOrgId(orgId);
        this.save(sysUserOrg);
    }

    @Override
    public void add(Long userId, Long orgId, Long positionId) {
        SysUserOrg sysUserOrg = new SysUserOrg();
        sysUserOrg.setUserId(userId);
        sysUserOrg.setOrgId(orgId);
        sysUserOrg.setPositionId(positionId);
        this.save(sysUserOrg);
    }

    @Override
    public void del(UserOrgRequest userOrgResponse) {
        SysUserOrg sysUserOrg = this.querySysUserOrgById(userOrgResponse);
        this.removeById(sysUserOrg.getUserOrgId());
    }

    @Override
    public void delByUserId(Long userId) {
        LambdaQueryWrapper<SysUserOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserOrg::getUserId, userId);
        this.remove(queryWrapper);
    }

    @Override
    public void edit(UserOrgRequest userOrgResponse) {
        SysUserOrg sysUserOrg = this.querySysUserOrgById(userOrgResponse);
        BeanUtil.copyProperties(userOrgResponse, sysUserOrg);
        this.updateById(sysUserOrg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Long userId, Long orgId) {

        // 删除已有绑定的组织机构
        this.delByUserId(userId);

        // 新增组织机构绑定
        this.add(userId, orgId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Long userId, Long orgId, Long positionId) {

        // 删除已有绑定的组织机构
        this.delByUserId(userId);

        // 新增组织机构绑定
        this.add(userId, orgId, positionId);
    }


    @Override
    public SysUserOrg detail(UserOrgRequest userOrgResponse) {
        return this.getOne(this.createWrapper(userOrgResponse), false);
    }

    @Override
    public List<SysUserOrg> findList(UserOrgRequest userOrgResponse) {
        LambdaQueryWrapper<SysUserOrg> queryWrapper = this.createWrapper(userOrgResponse);
        return this.list(queryWrapper);
    }


    @Override
    public Boolean getUserOrgFlag(Long orgId, Long positionId) {
        LambdaQueryWrapper<SysUserOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(orgId), SysUserOrg::getOrgId, orgId);
        queryWrapper.eq(ObjectUtil.isNotNull(positionId), SysUserOrg::getPositionId, positionId);
        return this.list(queryWrapper).size() > 0;
    }


    /**
     * 根据主键id获取对象
     *
     * @author chenjinlong
     * @date 2021/1/26 13:28
     */
    private SysUserOrg querySysUserOrgById(UserOrgRequest userOrgResponse) {
        SysUserOrg sysUserOrg = this.getById(userOrgResponse.getUserOrgId());
        if (ObjectUtil.isEmpty(sysUserOrg)) {
            throw new SystemModularException(SysUserOrgExceptionEnum.USER_ORG_NOT_EXIST, sysUserOrg.getOrgId());
        }
        return sysUserOrg;
    }

    /**
     * 实体构建queryWrapper
     *
     * @author 林选伟
     * @date 2021/1/24 22:03
     */
    private LambdaQueryWrapper<SysUserOrg> createWrapper(UserOrgRequest userOrgResponse) {
        LambdaQueryWrapper<SysUserOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(userOrgResponse.getUserOrgId()), SysUserOrg::getUserOrgId, userOrgResponse.getUserOrgId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(userOrgResponse.getUserId()), SysUserOrg::getUserId, userOrgResponse.getUserId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(userOrgResponse.getOrgId()), SysUserOrg::getOrgId, userOrgResponse.getOrgId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(userOrgResponse.getPositionId()), SysUserOrg::getPositionId, userOrgResponse.getPositionId());
        return queryWrapper;
    }

}
