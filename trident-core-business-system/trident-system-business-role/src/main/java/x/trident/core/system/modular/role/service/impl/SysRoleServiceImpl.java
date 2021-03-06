
package x.trident.core.system.modular.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.auth.api.enums.DataScopeTypeEnum;
import x.trident.core.auth.api.exception.AuthException;
import x.trident.core.auth.api.exception.enums.AuthExceptionEnum;
import x.trident.core.auth.api.pojo.login.basic.SimpleRoleInfo;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.constants.SymbolConstant;
import x.trident.core.enums.StatusEnum;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.exception.base.ServiceException;
import x.trident.core.pojo.dict.SimpleDict;
import x.trident.core.system.api.UserServiceApi;
import x.trident.core.system.api.constants.SystemConstants;
import x.trident.core.system.api.exception.SystemModularException;
import x.trident.core.system.api.exception.enums.role.SysRoleExceptionEnum;
import x.trident.core.system.modular.role.entity.*;
import x.trident.core.system.modular.role.mapper.SysRoleMapper;
import x.trident.core.system.modular.role.service.*;
import x.trident.core.system.api.pojo.role.dto.SysRoleDTO;
import x.trident.core.system.api.pojo.role.dto.SysRoleMenuButtonDTO;
import x.trident.core.system.api.pojo.role.dto.SysRoleMenuDTO;
import x.trident.core.system.api.pojo.role.dto.SysRoleResourceDTO;
import x.trident.core.system.api.pojo.role.request.SysRoleMenuButtonRequest;
import x.trident.core.system.api.pojo.role.request.SysRoleRequest;
import x.trident.core.system.api.util.DataScopeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * ????????????service???????????????
 *
 * @author majianguo
 * @date 2020/11/5 ??????11:33
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private SysRoleResourceService sysRoleResourceService;

    @Resource
    private SysRoleDataScopeService sysRoleDataScopeService;

    @Resource
    private SysRoleMenuService roleMenuService;

    @Resource
    private SysRoleMenuButtonService sysRoleMenuButtonService;

    @Override
    public void add(SysRoleRequest sysRoleRequest) {

        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleRequest, sysRole);

        // ?????????????????????
        sysRole.setStatusFlag(StatusEnum.ENABLE.getCode());

        // ???????????????????????????
        sysRole.setRoleSystemFlag(YesOrNotEnum.N.getCode());

        //??????????????????
        sysRole.setDataScopeType(DataScopeTypeEnum.SELF.getCode());

        this.save(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);

        // ???????????????????????????
        if (YesOrNotEnum.Y.getCode().equals(sysRole.getRoleSystemFlag())) {
            throw new ServiceException(SysRoleExceptionEnum.SYSTEM_ROLE_CANT_DELETE);
        }

        // ?????????????????????????????????
        sysRole.setDelFlag(YesOrNotEnum.Y.getCode());

        this.updateById(sysRole);

        Long roleId = sysRole.getRoleId();

        // ????????????????????????????????????-????????????????????????
        sysRoleDataScopeService.delByRoleId(roleId);

        // ????????????????????????????????????-?????????????????????
        userServiceApi.deleteUserRoleListByRoleId(roleId);

        // ????????????????????????????????????-?????????????????????
        sysRoleResourceService.deleteRoleResourceListByRoleId(roleId);
    }

    @Override
    public void edit(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);

        // ?????????????????????????????????
        if (SystemConstants.SUPER_ADMIN_ROLE_CODE.equals(sysRole.getRoleCode())) {
            if (!sysRole.getRoleCode().equals(sysRoleRequest.getRoleCode())) {
                throw new SystemModularException(SysRoleExceptionEnum.SUPER_ADMIN_ROLE_CODE_ERROR);
            }
        }

        // ????????????
        BeanUtil.copyProperties(sysRoleRequest, sysRole);

        // ??????????????????????????????????????????????????????
        sysRole.setStatusFlag(null);

        this.updateById(sysRole);
    }

    @Override
    public SysRoleDTO detail(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);
        SysRoleDTO roleResponse = new SysRoleDTO();
        BeanUtil.copyProperties(sysRole, roleResponse);

        // ??????????????????????????????
        roleResponse.setDataScopeTypeEnum(DataScopeTypeEnum.codeToEnum(sysRole.getDataScopeType()));

        return roleResponse;
    }

    @Override
    public PageResult<SysRole> findPage(SysRoleRequest sysRoleRequest) {
        LambdaQueryWrapper<SysRole> wrapper = createWrapper(sysRoleRequest);
        Page<SysRole> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public List<SimpleDict> findList(SysRoleRequest sysRoleParam) {
        List<SimpleDict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysRoleParam)) {

            // ???????????????????????????????????????
            if (ObjectUtil.isNotEmpty(sysRoleParam.getRoleName())) {
                queryWrapper.and(i -> i.like(SysRole::getRoleName, sysRoleParam.getRoleName()).or().like(SysRole::getRoleCode, sysRoleParam.getRoleName()));
            }
        }

        // ?????????????????????
        queryWrapper.eq(SysRole::getStatusFlag, StatusEnum.ENABLE.getCode());

        // ????????????????????????????????????????????????
        queryWrapper.orderByAsc(SysRole::getRoleSort);
        this.list(queryWrapper).forEach(sysRole -> {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysRole.getRoleId());
            simpleDict.setName(sysRole.getRoleName() + SymbolConstant.LEFT_SQUARE_BRACKETS + sysRole.getRoleCode() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
            dictList.add(simpleDict);
        });
        return dictList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantMenuAndButton(SysRoleRequest sysRoleMenuButtonRequest) {

        // ??????????????????????????????
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLqw = new LambdaQueryWrapper<>();
        sysRoleMenuLqw.eq(SysRoleMenu::getRoleId, sysRoleMenuButtonRequest.getRoleId());
        roleMenuService.remove(sysRoleMenuLqw);

        // ????????????????????????????????????
        LambdaQueryWrapper<SysRoleMenuButton> menuButtonLqw = new LambdaQueryWrapper<>();
        menuButtonLqw.eq(SysRoleMenuButton::getRoleId, sysRoleMenuButtonRequest.getRoleId());
        sysRoleMenuButtonService.remove(menuButtonLqw);

        // ????????????
        List<Long> menuIdList = sysRoleMenuButtonRequest.getGrantMenuIdList();
        if (ObjectUtil.isNotEmpty(menuIdList)) {
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            for (Long menuId : menuIdList) {
                SysRoleMenu item = new SysRoleMenu();
                item.setRoleId(sysRoleMenuButtonRequest.getRoleId());
                item.setMenuId(menuId);
                sysRoleMenus.add(item);
            }
            roleMenuService.saveBatch(sysRoleMenus);
        }

        // ????????????
        List<SysRoleMenuButtonRequest> menuButtonList = sysRoleMenuButtonRequest.getGrantMenuButtonIdList();
        if (ObjectUtil.isNotEmpty(menuButtonList)) {
            List<SysRoleMenuButton> sysRoleMenuButtons = new ArrayList<>();
            for (SysRoleMenuButtonRequest menuButton : menuButtonList) {
                SysRoleMenuButton item = new SysRoleMenuButton();
                item.setRoleId(sysRoleMenuButtonRequest.getRoleId());
                item.setButtonId(menuButton.getButtonId());
                item.setButtonCode(menuButton.getButtonCode());
                sysRoleMenuButtons.add(item);
            }
            sysRoleMenuButtonService.saveBatch(sysRoleMenuButtons);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantDataScope(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);

        // ??????????????????????????????????????????
        boolean superAdmin = LoginContext.me().getSuperAdminFlag();

        // ???????????????????????????????????????
        Integer dataScopeType = sysRoleRequest.getDataScopeType();
        DataScopeTypeEnum dataScopeTypeEnum = DataScopeTypeEnum.codeToEnum(dataScopeType);

        // ?????????????????????????????????????????????????????????????????????
        if (!superAdmin) {

            // ?????????????????????????????????????????????
            if (DataScopeTypeEnum.ALL.equals(dataScopeTypeEnum)) {
                throw new AuthException(AuthExceptionEnum.ONLY_SUPER_ERROR);
            }

            // ?????????????????????????????????????????????????????????????????????????????????
            if (DataScopeTypeEnum.DEFINE.getCode().equals(dataScopeType)) {
                if (ObjectUtil.isEmpty(sysRoleRequest.getGrantOrgIdList())) {
                    throw new SystemModularException(SysRoleExceptionEnum.PLEASE_FILL_DATA_SCOPE);
                }
                for (Long orgId : sysRoleRequest.getGrantOrgIdList()) {
                    DataScopeUtil.quickValidateDataScope(orgId);
                }
            }
        }

        sysRole.setDataScopeType(sysRoleRequest.getDataScopeType());
        this.updateById(sysRole);

        // ??????????????????????????????
        sysRoleDataScopeService.grantDataScope(sysRoleRequest);
    }

    @Override
    public List<SimpleDict> dropDown() {
        List<SimpleDict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();

        // ??????????????????????????????????????????????????????????????????????????????
        if (!LoginContext.me().getSuperAdminFlag()) {

            // ?????????????????????
            List<SimpleRoleInfo> roles = LoginContext.me().getLoginUser().getSimpleRoleInfoList();

            // ??????????????????id
            Set<Long> loginUserRoleIds = roles.stream().map(SimpleRoleInfo::getRoleId).collect(Collectors.toSet());
            if (ObjectUtil.isEmpty(loginUserRoleIds)) {
                return dictList;
            }
            queryWrapper.in(SysRole::getRoleId, loginUserRoleIds);
        }

        // ?????????????????????
        queryWrapper.eq(SysRole::getStatusFlag, StatusEnum.ENABLE.getCode()).eq(SysRole::getDelFlag, YesOrNotEnum.N.getCode());

        this.list(queryWrapper).forEach(sysRole -> {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysRole.getRoleId());
            simpleDict.setCode(sysRole.getRoleCode());
            simpleDict.setName(sysRole.getRoleName());
            dictList.add(simpleDict);
        });
        return dictList;
    }

    @Override
    public List<Long> getRoleDataScope(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);
        return sysRoleDataScopeService.getRoleDataScopeIdList(CollectionUtil.newArrayList(sysRole.getRoleId()));
    }

    @Override
    public List<SysRoleDTO> getRolesByIds(List<Long> roleIds) {

        ArrayList<SysRoleDTO> sysRoleResponses = new ArrayList<>();

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRole::getRoleId, roleIds);
        List<SysRole> sysRoles = this.list(queryWrapper);

        // ?????????????????????????????????????????????SysRoleResponse
        if (!sysRoles.isEmpty()) {
            for (SysRole sysRole : sysRoles) {
                SysRoleDTO sysRoleResponse = new SysRoleDTO();
                BeanUtil.copyProperties(sysRole, sysRoleResponse);
                // ??????????????????????????????
                sysRoleResponse.setDataScopeTypeEnum(DataScopeTypeEnum.codeToEnum(sysRole.getDataScopeType()));
                sysRoleResponses.add(sysRoleResponse);
            }
        }

        return sysRoleResponses;
    }

    @Override
    public List<Long> getRoleDataScopes(List<Long> roleIds) {
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleDataScope::getRoleId, roleIds);

        List<SysRoleDataScope> list = this.sysRoleDataScopeService.list(queryWrapper);
        if (!list.isEmpty()) {
            return list.stream().map(SysRoleDataScope::getOrganizationId).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Long> getMenuIdsByRoleIds(List<Long> roleIds) {

        if (roleIds == null || roleIds.isEmpty()) {
            return new ArrayList<>();
        }

        // ???????????????????????????
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleMenu::getRoleId, roleIds);
        queryWrapper.select(SysRoleMenu::getMenuId);

        List<SysRoleMenu> roleMenus = this.roleMenuService.list(queryWrapper);
        if (roleMenus == null || roleMenus.isEmpty()) {
            return new ArrayList<>();
        }

        return roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleResourceCodeList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysRoleResource::getResourceCode);
        queryWrapper.in(SysRoleResource::getRoleId, roleIdList);
        List<SysRoleResource> sysRoleResources = sysRoleResourceService.list(queryWrapper);
        return sysRoleResources.parallelStream().map(SysRoleResource::getResourceCode).collect(Collectors.toList());
    }

    @Override
    public List<SysRoleResourceDTO> getRoleResourceList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleResource::getRoleId, roleIdList);
        List<SysRoleResource> sysRoleResources = sysRoleResourceService.list(queryWrapper);
        return sysRoleResources.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleResourceDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Set<String> getRoleButtonCodes(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleMenuButton> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleMenuButton::getRoleId, roleIdList);
        queryWrapper.select(SysRoleMenuButton::getButtonCode);
        List<SysRoleMenuButton> list = sysRoleMenuButtonService.list(queryWrapper);
        return list.stream().map(SysRoleMenuButton::getButtonCode).collect(Collectors.toSet());
    }

    @Override
    public List<SysRoleMenuDTO> getRoleMenuList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuLambdaQueryWrapper.in(SysRoleMenu::getRoleId, roleIdList);
        List<SysRoleMenu> roleMenus = roleMenuService.list(sysRoleMenuLambdaQueryWrapper);
        return roleMenus.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleMenuDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SysRoleMenuButtonDTO> getRoleMenuButtonList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleMenuButton> sysRoleMenuButtonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuButtonLambdaQueryWrapper.in(SysRoleMenuButton::getRoleId, roleIdList);
        List<SysRoleMenuButton> sysRoleMenuButtons = sysRoleMenuButtonService.list(sysRoleMenuButtonLambdaQueryWrapper);
        return sysRoleMenuButtons.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleMenuButtonDTO.class)).collect(Collectors.toList());
    }

    /**
     * ??????????????????
     *
     * @param sysRoleRequest ????????????
     * @author majianguo
     * @date 2020/11/5 ??????4:12
     */
    private SysRole querySysRole(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.getById(sysRoleRequest.getRoleId());
        if (ObjectUtil.isNull(sysRole)) {
            throw new SystemModularException(SysRoleExceptionEnum.ROLE_NOT_EXIST);
        }
        return sysRole;
    }

    /**
     * ????????????wrapper
     *
     * @author ?????????
     * @date 2020/11/22 15:14
     */
    private LambdaQueryWrapper<SysRole> createWrapper(SysRoleRequest sysRoleRequest) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();

        // ??????????????????
        queryWrapper.eq(SysRole::getDelFlag, YesOrNotEnum.N.getCode());

        // ????????????????????????????????????????????????
        queryWrapper.orderByAsc(SysRole::getRoleSort);

        if (ObjectUtil.isEmpty(sysRoleRequest)) {
            return queryWrapper;
        }

        // ????????????????????????
        queryWrapper.like(ObjectUtil.isNotEmpty(sysRoleRequest.getRoleName()), SysRole::getRoleName, sysRoleRequest.getRoleName());

        // ????????????????????????
        queryWrapper.like(ObjectUtil.isNotEmpty(sysRoleRequest.getRoleCode()), SysRole::getRoleCode, sysRoleRequest.getRoleCode());


        return queryWrapper;
    }

}
