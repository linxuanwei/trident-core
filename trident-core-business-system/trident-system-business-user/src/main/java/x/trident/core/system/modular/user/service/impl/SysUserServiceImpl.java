
package x.trident.core.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import x.trident.core.auth.api.SessionManagerApi;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.auth.api.exception.enums.AuthExceptionEnum;
import x.trident.core.auth.api.password.PasswordStoredEncryptApi;
import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.auth.api.pojo.login.basic.SimpleUserInfo;
import x.trident.core.cache.api.CacheOperatorApi;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.file.api.FileInfoApi;
import x.trident.core.file.api.constants.FileConstants;
import x.trident.core.office.api.OfficeExcelApi;
import x.trident.core.office.api.pojo.report.ExcelExportParam;
import x.trident.core.enums.TreeNodeEnum;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.pojo.dict.SimpleDict;
import x.trident.core.tree.factory.DefaultTreeBuildFactory;
import x.trident.core.system.api.DataScopeApi;
import x.trident.core.system.api.OrganizationServiceApi;
import x.trident.core.system.api.ResourceServiceApi;
import x.trident.core.system.api.RoleServiceApi;
import x.trident.core.system.api.enums.UserStatusEnum;
import x.trident.core.system.api.exception.SystemModularException;
import x.trident.core.system.api.exception.enums.user.SysUserExceptionEnum;
import x.trident.core.system.api.expander.SystemConfigExpander;
import x.trident.core.system.api.pojo.organization.DataScopeDTO;
import x.trident.core.system.api.pojo.organization.HrOrganizationDTO;
import x.trident.core.system.api.pojo.role.dto.SysRoleDTO;
import x.trident.core.system.api.pojo.user.*;
import x.trident.core.system.api.pojo.user.request.OnlineUserRequest;
import x.trident.core.system.api.pojo.user.request.SysUserRequest;
import x.trident.core.system.api.util.DataScopeUtil;
import x.trident.core.system.modular.user.entity.SysUser;
import x.trident.core.system.modular.user.entity.SysUserDataScope;
import x.trident.core.system.modular.user.entity.SysUserRole;
import x.trident.core.system.modular.user.factory.OnlineUserCreateFactory;
import x.trident.core.system.modular.user.factory.SysUserCreateFactory;
import x.trident.core.system.modular.user.factory.UserLoginInfoFactory;
import x.trident.core.system.modular.user.mapper.SysUserMapper;
import x.trident.core.system.modular.user.service.SysUserDataScopeService;
import x.trident.core.system.modular.user.service.SysUserOrgService;
import x.trident.core.system.modular.user.service.SysUserRoleService;
import x.trident.core.system.modular.user.service.SysUserService;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ?????????????????????
 *
 * @author ?????????
 * @date 2020/11/21 15:04
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserOrgService sysUserOrgService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysUserDataScopeService sysUserDataScopeService;

    @Resource
    private OfficeExcelApi officeExcelApi;

    @Resource
    private DataScopeApi dataScopeApi;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource
    private FileInfoApi fileInfoApi;

    @Resource
    private PasswordStoredEncryptApi passwordStoredEncryptApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private OrganizationServiceApi organizationServiceApi;

    @Resource
    private CacheOperatorApi<SysUserDTO> sysUserCacheOperatorApi;

    @Override
    public void register(SysUserRequest sysUserRequest) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserRequest, sysUser);
        sysUser.setRealName(IdUtil.simpleUUID());
        SysUserCreateFactory.fillAddSysUser(sysUser);
        // ????????????
        this.save(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUserRequest sysUserRequest) {

        // ???????????????????????????????????????id
        Long organizationId = sysUserRequest.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        // ??????bean???????????????????????????????????????
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserRequest, sysUser);
        SysUserCreateFactory.fillAddSysUser(sysUser);

        // ????????????????????????
        sysUser.setAvatar(FileConstants.DEFAULT_AVATAR_FILE_ID);

        // ????????????
        this.save(sysUser);

        // ????????????????????????
        if (null == sysUserRequest.getPositionId()) {
            sysUserOrgService.add(sysUser.getUserId(), sysUserRequest.getOrgId());
        } else {
            sysUserOrgService.add(sysUser.getUserId(), sysUserRequest.getOrgId(), sysUserRequest.getPositionId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ???????????????????????????
        if (YesOrNotEnum.Y.getCode().equals(sysUser.getSuperAdminFlag())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
        }

        // ????????????????????????????????????
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(sysUser.getUserId());
        Long organizationId = userOrgInfo.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        // ??????????????????????????????Y
        sysUser.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysUser);

        Long userId = sysUser.getUserId();

        // ???????????????????????????????????????
        sysUserOrgService.delByUserId(userId);

        // ??????????????????????????????-?????????????????????
        sysUserRoleService.delByUserId(userId);

        // ??????????????????????????????-???????????????????????????
        sysUserDataScopeService.delByUserId(userId);

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysUserRequest sysUserRequest) {

        // ???????????????????????????????????????id
        Long organizationId = sysUserRequest.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        // ???????????????
        SysUser sysUser = this.querySysUser(sysUserRequest);
        BeanUtil.copyProperties(sysUserRequest, sysUser);

        // ??????????????????
        SysUserCreateFactory.fillEditSysUser(sysUser);
        this.updateById(sysUser);

        Long sysUserId = sysUser.getUserId();

        // ????????????????????????
        if (null == sysUserRequest.getPositionId()) {
            sysUserOrgService.edit(sysUser.getUserId(), sysUserRequest.getOrgId());
        } else {
            sysUserOrgService.edit(sysUser.getUserId(), sysUserRequest.getOrgId(), sysUserRequest.getPositionId());
        }

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUserId));
    }

    @Override
    public void editInfo(SysUserRequest sysUserRequest) {

        // ???????????????????????????id
        sysUserRequest.setUserId(LoginContext.me().getLoginUser().getUid());
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ???????????????????????????
        SysUserCreateFactory.fillUpdateInfo(sysUserRequest, sysUser);

        this.updateById(sysUser);

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    public void editStatus(SysUserRequest sysUserRequest) {

        // ?????????????????????????????????
        Integer statusFlag = sysUserRequest.getStatusFlag();
        UserStatusEnum.validateUserStatus(statusFlag);

        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ?????????????????????????????????
        if (YesOrNotEnum.Y.getCode().equals(sysUser.getSuperAdminFlag())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_UPDATE_ADMIN);
        }

        Long id = sysUser.getUserId();

        // ???????????????????????????????????????????????????
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUserId, id).and(i -> i.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode())).set(SysUser::getStatusFlag, statusFlag);

        boolean update = this.update(updateWrapper);
        if (!update) {
            log.error(SysUserExceptionEnum.UPDATE_USER_STATUS_ERROR.getUserTip());
            throw new SystemModularException(SysUserExceptionEnum.UPDATE_USER_STATUS_ERROR);
        }

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    public void editPassword(SysUserRequest sysUserRequest) {

        // ?????????????????????userId
        LoginUser loginUser = LoginContext.me().getLoginUser();
        sysUserRequest.setUserId(loginUser.getUid());

        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ???????????????????????????
        if (sysUserRequest.getNewPassword().equals(sysUserRequest.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }

        // ???????????????
        if (!passwordStoredEncryptApi.checkPassword(sysUserRequest.getPassword(), sysUser.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_ERROR);
        }

        sysUser.setPassword(passwordStoredEncryptApi.encrypt(sysUserRequest.getNewPassword()));
        this.updateById(sysUser);

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    public void resetPassword(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ?????????????????????????????????
        String password = SystemConfigExpander.getDefaultPassWord();
        sysUser.setPassword(passwordStoredEncryptApi.encrypt(password));

        this.updateById(sysUser);
    }

    @Override
    public void editAvatar(SysUserRequest sysUserRequest) {

        // ???????????????id
        Long fileId = sysUserRequest.getAvatar();

        // ???????????????????????????id
        LoginUser loginUser = LoginContext.me().getLoginUser();
        sysUserRequest.setUserId(loginUser.getUid());

        // ??????????????????
        SysUser sysUser = this.querySysUser(sysUserRequest);
        sysUser.setAvatar(fileId);
        this.updateById(sysUser);

        // ?????????????????????session??????
        SimpleUserInfo simpleUserInfo = loginUser.getSimpleUserInfo();
        simpleUserInfo.setAvatar(fileId);
        sessionManagerApi.updateSession(LoginContext.me().getToken(), loginUser);

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ?????????????????????????????????????????????
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(sysUser.getUserId());
        Long organizationId = userOrgInfo.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        // ?????????????????????
        sysUserRoleService.assignRoles(sysUserRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantData(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ????????????????????????????????????
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(sysUser.getUserId());
        Long organizationId = userOrgInfo.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        sysUserDataScopeService.assignData(sysUserRequest);
    }

    @Override
    public SysUserDTO detail(SysUserRequest sysUserRequest) {
        SysUserDTO sysUserResponse = new SysUserDTO();

        // ????????????????????????
        SysUser sysUser = this.querySysUser(sysUserRequest);
        BeanUtil.copyProperties(sysUser, sysUserResponse);

        // ??????????????????????????????
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(sysUser.getUserId());
        sysUserResponse.setOrgId(userOrgInfo.getOrgId());
        sysUserResponse.setPositionId(userOrgInfo.getPositionId());

        // ????????????????????????
        sysUserResponse.setGrantRoleIdList(sysUserRoleService.findRoleIdsByUserId(sysUser.getUserId()));

        return sysUserResponse;
    }

    @Override
    public PageResult<SysUserDTO> findPage(SysUserRequest sysUserRequest) {

        Page<SysUserDTO> userPage = this.baseMapper.findUserPage(PageFactory.defaultPage(), sysUserRequest);

        return PageResultFactory.createPageResult(userPage);
    }

    @Override
    public void export(HttpServletResponse response) {
        ExcelExportParam excelExportParam = new ExcelExportParam();
        List<SysUser> sysUserList = this.list();

        excelExportParam.setClazz(SysUser.class);
        excelExportParam.setDataList(sysUserList);
        excelExportParam.setExcelTypeEnum(ExcelTypeEnum.XLS);
        excelExportParam.setFileName("??????????????????");
        excelExportParam.setResponse(response);

        officeExcelApi.easyExportDownload(excelExportParam);
    }

    @Override
    public List<UserSelectTreeNode> userSelectTree(SysUserRequest sysUserRequest) {
        // ??????????????????
        List<UserSelectTreeNode> treeNodeList = CollectionUtil.newArrayList();
        List<HrOrganizationDTO> orgList = organizationServiceApi.orgList();
        UserSelectTreeNode orgTreeNode;
        for (HrOrganizationDTO hrOrganization : orgList) {
            orgTreeNode = new UserSelectTreeNode();
            orgTreeNode.setId(String.valueOf(hrOrganization.getOrgId()));
            orgTreeNode.setPId(String.valueOf(hrOrganization.getOrgParentId()));
            orgTreeNode.setName(hrOrganization.getOrgName());
            orgTreeNode.setNodeType(TreeNodeEnum.ORG.getCode());
            orgTreeNode.setValue(String.valueOf(hrOrganization.getOrgId()));
            orgTreeNode.setSort(hrOrganization.getOrgSort());
            treeNodeList.add(orgTreeNode);
            List<UserSelectTreeNode> userNodeList = this.getUserTreeNodeList(hrOrganization.getOrgId());
            if (userNodeList.size() > 0) {
                treeNodeList.addAll(userNodeList);
            }
        }
        // ??????????????????
        return new DefaultTreeBuildFactory<UserSelectTreeNode>().doTreeBuild(treeNodeList);
    }

    @Override
    public SysUser getUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode());

        List<SysUser> list = this.list(queryWrapper);

        // ???????????????
        if (list.isEmpty()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, account);
        }

        // ??????????????????
        if (list.size() > 1) {
            throw new SystemModularException(SysUserExceptionEnum.ACCOUNT_HAVE_MANY, account);
        }

        return list.get(0);
    }

    @Override
    public String getUserAvatarUrl(Long fileId) {

        // ???????????????????????????
        return fileInfoApi.getFileAuthUrl(fileId);
    }

    @Override
    public String getUserAvatarUrl(Long fileId, String token) {

        // ???????????????????????????
        return fileInfoApi.getFileAuthUrl(fileId, token);
    }

    @Override
    public List<UserSelectTreeNode> getUserTreeNodeList(Long orgId) {
        // ??????????????????
        List<UserSelectTreeNode> treeNodeList = CollectionUtil.newArrayList();
        SysUserRequest userRequest = new SysUserRequest();
        userRequest.setOrgId(orgId);
        List<SysUserDTO> userList = this.baseMapper.findUserList(userRequest);
        UserSelectTreeNode userTreeNode;
        for (SysUserDTO user : userList) {
            userTreeNode = new UserSelectTreeNode();
            userTreeNode.setId(String.valueOf(user.getUserId()));
            userTreeNode.setPId(String.valueOf(user.getOrgId()));
            userTreeNode.setName(user.getRealName());
            userTreeNode.setNodeType(TreeNodeEnum.USER.getCode());
            userTreeNode.setValue(String.valueOf(user.getUserId()));
            treeNodeList.add(userTreeNode);
        }
        return treeNodeList;
    }

    @Override
    public List<SimpleDict> selector(SysUserRequest sysUserRequest) {

        LambdaQueryWrapper<SysUser> wrapper = createWrapper(sysUserRequest);

        // ?????????????????????
        wrapper.ne(SysUser::getSuperAdminFlag, YesOrNotEnum.Y.getCode());

        // ?????????id???name
        wrapper.select(SysUser::getRealName, SysUser::getUserId);
        List<SysUser> list = this.list(wrapper);

        ArrayList<SimpleDict> results = new ArrayList<>();
        for (SysUser sysUser : list) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysUser.getUserId());
            simpleDict.setName(sysUser.getRealName());
            results.add(simpleDict);
        }

        return results;
    }

    @Override
    public void batchDelete(SysUserRequest sysUserRequest) {
        List<Long> userIds = sysUserRequest.getUserIds();
        for (Long userId : userIds) {
            SysUserRequest tempRequest = new SysUserRequest();
            tempRequest.setUserId(userId);
            this.del(tempRequest);
        }
    }

    @Override
    public UserLoginInfoDTO getUserLoginInfo(String account) {

        // 1. ???????????????????????????
        SysUser sysUser = this.getUserByAccount(account);
        Long userId = sysUser.getUserId();

        // 2. ????????????????????????
        List<Long> roleIds = sysUserRoleService.findRoleIdsByUserId(userId);
        if (ObjectUtil.isEmpty(roleIds)) {
            throw new SystemModularException(AuthExceptionEnum.ROLE_IS_EMPTY);
        }
        List<SysRoleDTO> roleResponseList = roleServiceApi.getRolesByIds(roleIds);

        // 3. ???????????????????????????
        DataScopeDTO dataScopeResponse = dataScopeApi.getDataScope(userId, roleResponseList);

        // 4. ??????????????????????????????????????????
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(userId);

        // 5. ???????????????????????????url
        List<String> resourceCodeList = roleServiceApi.getRoleResourceCodeList(roleIds);
        Set<String> resourceUrlsListByCodes = resourceServiceApi.getResourceUrlsListByCodes(resourceCodeList);

        // 6. ???????????????????????????code??????
        Set<String> roleButtonCodes = roleServiceApi.getRoleButtonCodes(roleIds);

        // 7. ??????????????????
        return UserLoginInfoFactory.userLoginInfoDTO(sysUser, roleResponseList, dataScopeResponse, userOrgInfo, resourceUrlsListByCodes, roleButtonCodes);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserLoginInfo(Long userId, Date date, String ip) {

        // ????????????id????????????????????????
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUserId, userId).eq(SysUser::getDelFlag, YesOrNotEnum.N.getCode());
        SysUser sysUser = this.getOne(sysUserLambdaQueryWrapper);

        if (sysUser != null) {
            // ????????????????????????
            SysUser newSysUser = new SysUser();
            newSysUser.setUserId(sysUser.getUserId());
            newSysUser.setLastLoginIp(ip);
            newSysUser.setLastLoginTime(date);
            this.updateById(newSysUser);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserDataScopeListByOrgIdList(Set<Long> organizationIds) {
        if (organizationIds != null && organizationIds.size() > 0) {
            LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysUserDataScope::getOrgId, organizationIds);
            sysUserDataScopeService.remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getUserRoleIdList(Long userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        queryWrapper.select(SysUserRole::getRoleId);

        List<SysUserRole> list = sysUserRoleService.list(queryWrapper);
        return list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserRoleListByRoleId(Long roleId) {
        if (roleId != null) {
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getRoleId, roleId);
            sysUserRoleService.remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getUserBindDataScope(Long userId) {
        return sysUserDataScopeService.findOrgIdsByUserId(userId);
    }

    @Override
    public List<OnlineUserDTO> onlineUserList(OnlineUserRequest onlineUserRequest) {
        List<LoginUser> loginUsers = sessionManagerApi.onlineUserList();

        // ????????????
        List<OnlineUserDTO> result = loginUsers.stream().map(OnlineUserCreateFactory::createOnlineUser).collect(Collectors.toList());

        // ???????????????????????????account????????????
        if (StrUtil.isNotBlank(onlineUserRequest.getAccount())) {
            return result.stream().filter(i -> i.getAccount().equals(onlineUserRequest.getAccount())).collect(Collectors.toList());
        } else {
            return result;
        }
    }

    @Override
    public SysUserDTO getUserInfoByUserId(Long userId) {

        // ?????????????????????
        SysUserDTO sysUserDTO = sysUserCacheOperatorApi.get(String.valueOf(userId));
        if (sysUserDTO != null) {
            return sysUserDTO;
        }

        SysUser sysUser = this.getById(userId);
        if (ObjectUtil.isNotEmpty(sysUser)) {
            SysUserDTO result = BeanUtil.copyProperties(sysUser, SysUserDTO.class);
            sysUserCacheOperatorApi.put(String.valueOf(userId), result);
            return result;
        }
        return null;
    }

    @Override
    public List<Long> queryAllUserIdList(SysUserRequest sysUserRequest) {

        LambdaQueryWrapper<SysUser> wrapper = createWrapper(sysUserRequest);

        // ?????????????????????
        wrapper.ne(SysUser::getSuperAdminFlag, YesOrNotEnum.Y.getCode());

        // ?????????id
        wrapper.select(SysUser::getUserId);

        // ??????????????????ID
        Function<Object, Long> mapper = id -> Long.valueOf(id.toString());

        return this.listObjs(wrapper, mapper);
    }

    @Override
    public Boolean userExist(Long userId) {
        SysUserRequest userRequest = new SysUserRequest();
        userRequest.setUserId(userId);
        LambdaQueryWrapper<SysUser> wrapper = createWrapper(userRequest);

        // ?????????id
        wrapper.select(SysUser::getUserId);

        // ????????????
        SysUser sysUser = this.getOne(wrapper);
        if (sysUser == null || sysUser.getUserId() == null) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * ??????????????????
     *
     * @author ?????????
     * @date 2020/3/26 9:54
     */
    private SysUser querySysUser(SysUserRequest sysUserRequest) {

        // ?????????????????????????????????
        String userIdKey = String.valueOf(sysUserRequest.getUserId());
        SysUserDTO sysUserDTO = sysUserCacheOperatorApi.get(userIdKey);
        if (sysUserDTO != null) {
            SysUser tempUser = new SysUser();
            BeanUtil.copyProperties(sysUserDTO, tempUser);
            return tempUser;
        }

        SysUser sysUser = this.getById(sysUserRequest.getUserId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, sysUserRequest.getUserId());
        }

        // ????????????
        SysUserDTO sysUserDTOCache = new SysUserDTO();
        BeanUtil.copyProperties(sysUser, sysUserDTOCache);
        sysUserCacheOperatorApi.put(userIdKey, sysUserDTOCache);

        return sysUser;
    }

    /**
     * ?????????????????????wrapper
     *
     * @author ?????????
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysUser> createWrapper(SysUserRequest sysUserRequest) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        // ????????????????????????
        queryWrapper.eq(SysUser::getDelFlag, YesOrNotEnum.N.getCode());

        if (ObjectUtil.isEmpty(sysUserRequest)) {
            return queryWrapper;
        }

        // SQL??????
        queryWrapper.eq(ObjectUtil.isNotEmpty(sysUserRequest.getUserId()), SysUser::getUserId, sysUserRequest.getUserId());
        queryWrapper.like(ObjectUtil.isNotEmpty(sysUserRequest.getAccount()), SysUser::getAccount, sysUserRequest.getAccount());
        queryWrapper.eq(ObjectUtil.isNotEmpty(sysUserRequest.getRealName()), SysUser::getRealName, sysUserRequest.getRealName());

        return queryWrapper;
    }

}
