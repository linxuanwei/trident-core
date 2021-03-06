package x.trident.core.system.modular.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.constants.BaseConstants;
import x.trident.core.constants.SymbolConstant;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.dict.api.pojo.dict.request.ParentIdsUpdateRequest;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.system.api.exception.SystemModularException;
import x.trident.core.system.api.exception.enums.resource.ApiGroupExceptionEnum;
import x.trident.core.system.api.pojo.resource.ApiGroupRequest;
import x.trident.core.system.api.pojo.resource.ApiGroupTreeWrapper;
import x.trident.core.system.api.pojo.resource.TreeSortRequest;
import x.trident.core.system.modular.resource.entity.ApiGroup;
import x.trident.core.system.modular.resource.entity.ApiResource;
import x.trident.core.system.modular.resource.entity.ApiResourceField;
import x.trident.core.system.modular.resource.entity.SysResource;
import x.trident.core.system.modular.resource.enums.NodeEnums;
import x.trident.core.system.modular.resource.enums.NodeTypeEnums;
import x.trident.core.system.modular.resource.mapper.ApiGroupMapper;
import x.trident.core.system.modular.resource.service.ApiGroupService;
import x.trident.core.system.modular.resource.service.ApiResourceFieldService;
import x.trident.core.system.modular.resource.service.ApiResourceService;
import x.trident.core.system.modular.resource.service.SysResourceService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ???????????????????????????
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@Service
public class ApiGroupServiceImpl extends ServiceImpl<ApiGroupMapper, ApiGroup> implements ApiGroupService {

    @Autowired
    private ApiResourceService apiResourceService;

    @Autowired
    private ApiResourceFieldService apiResourceFieldService;

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiGroup add(ApiGroupRequest apiGroupRequest) {
        ApiGroup apiGroup = new ApiGroup();
        BeanUtil.copyProperties(apiGroupRequest, apiGroup);
        this.setPids(apiGroup);
        this.save(apiGroup);
        return apiGroup;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(ApiGroupRequest apiGroupRequest) {

        // ????????????????????????
        if (NodeEnums.ROOT_NODE.getId().equals(apiGroupRequest.getGroupId())) {
            throw new SystemModularException(ApiGroupExceptionEnum.ROOT_PROHIBIT_OPERATION);
        }

        ApiGroup apiGroup = this.queryApiGroup(apiGroupRequest);
        this.removeById(apiGroup.getGroupId());

        // ???????????????????????????
        LambdaQueryWrapper<ApiGroup> apiGroupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiGroupLambdaQueryWrapper.like(ApiGroup::getGroupPids, SymbolConstant.LEFT_SQUARE_BRACKETS + apiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
        List<ApiGroup> apiGroups = this.list(apiGroupLambdaQueryWrapper);

        // ???????????????????????????
        apiGroups.add(apiGroup);

        // ??????????????????ID
        Set<Long> apiGroupIdSet = null;
        if (ObjectUtil.isNotEmpty(apiGroups)) {
            apiGroupIdSet = apiGroups.stream().map(ApiGroup::getGroupId).collect(Collectors.toSet());
        }

        if (ObjectUtil.isNotEmpty(apiGroupIdSet)) {
            // ??????????????????????????????
            LambdaQueryWrapper<ApiResource> apiResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apiResourceLambdaQueryWrapper.in(ApiResource::getGroupId, apiGroupIdSet);
            List<ApiResource> apiResourceList = this.apiResourceService.list(apiResourceLambdaQueryWrapper);

            if (ObjectUtil.isNotEmpty(apiResourceList)) {
                Set<Long> apiResourceSet = apiResourceList.stream().map(ApiResource::getApiResourceId).collect(Collectors.toSet());
                // ???????????????????????????
                LambdaQueryWrapper<ApiResourceField> apiResourceFieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
                apiResourceFieldLambdaQueryWrapper.in(ApiResourceField::getApiResourceId, apiResourceSet);
                this.apiResourceFieldService.remove(apiResourceFieldLambdaQueryWrapper);
            }

            // ????????????????????????????????????
            this.apiResourceService.remove(apiResourceLambdaQueryWrapper);
        }

        // ???????????????????????????
        this.remove(apiGroupLambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(ApiGroupRequest apiGroupRequest) {

        // ???????????????????????????????????????
        ApiGroup oldApiGroup = this.queryApiGroup(apiGroupRequest);

        // ????????????????????????
        if (apiGroupRequest.getGroupPid().equals(oldApiGroup.getGroupId())) {
            throw new SystemModularException(ApiGroupExceptionEnum.PARENT_NODE_ITSELF);
        }

        // ????????????????????????????????????
        LambdaQueryWrapper<ApiGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(ApiGroup::getGroupPids, SymbolConstant.LEFT_SQUARE_BRACKETS + oldApiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
        List<ApiGroup> apiGroups = this.list(lambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(apiGroups)) {
            if (apiGroups.stream().anyMatch(item -> item.getGroupId().equals(apiGroupRequest.getGroupPid()))) {
                throw new SystemModularException(ApiGroupExceptionEnum.PARENT_NODE_ITSELF);
            }
        }

        // ??????????????????
        ApiGroup newApiGroup = BeanUtil.toBean(apiGroupRequest, ApiGroup.class);

        // ????????????????????????ID
        if (!newApiGroup.getGroupPid().equals(oldApiGroup.getGroupPid())) {
            // ??????pids
            this.setPids(newApiGroup);

            // ????????????????????????pids
            this.updatePids(newApiGroup, oldApiGroup);
        }

        this.updateById(newApiGroup);
    }

    @Override
    public ApiGroup detail(ApiGroupRequest apiGroupRequest) {
        return this.queryApiGroup(apiGroupRequest);
    }

    @Override
    public PageResult<ApiGroup> findPage(ApiGroupRequest apiGroupRequest) {
        LambdaQueryWrapper<ApiGroup> wrapper = createWrapper(apiGroupRequest);
        Page<ApiGroup> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public List<ApiGroupTreeWrapper> tree(ApiGroupRequest apiGroupRequest) {
        return this.createTree(this.peersTree(apiGroupRequest));
    }

    @Override
    public List<ApiGroupTreeWrapper> peersTree(ApiGroupRequest apiGroupRequest) {
        // ??????
        List<ApiGroupTreeWrapper> allApiGroupTreeWrapperList = new ArrayList<>();

        //???????????????????????????
        Map<String, SysResource> stringSysResourceMap = new HashMap<>();
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getViewFlag, YesOrNotEnum.N.getCode());
        List<SysResource> sysResources = this.sysResourceService.list(sysResourceLambdaQueryWrapper);
        for (SysResource sysResource : sysResources) {
            stringSysResourceMap.put(sysResource.getResourceCode(), sysResource);
        }

        // ??????????????????
        LambdaQueryWrapper<ApiGroup> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(apiGroupRequest.getGroupId())) {
            wrapper.notLike(ApiGroup::getGroupPids, SymbolConstant.LEFT_SQUARE_BRACKETS + apiGroupRequest.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
            wrapper.ne(ApiGroup::getGroupId, apiGroupRequest.getGroupId());
        }
        List<ApiGroup> apiGroups = this.list(wrapper);

        if (ObjectUtil.isNotEmpty(apiGroups)) {
            for (ApiGroup apiGroup : apiGroups) {
                ApiGroupTreeWrapper item = new ApiGroupTreeWrapper();
                item.setId(apiGroup.getGroupId());
                item.setPid(apiGroup.getGroupPid());
                item.setName(apiGroup.getGroupName());
                item.setType(NodeTypeEnums.LEAF_NODE.getType());
                item.setSort(apiGroup.getGroupSort());
                item.setData(apiGroup);
                item.setSlotsValue();
                allApiGroupTreeWrapperList.add(item);
            }
        }

        // ??????????????????
        List<ApiResource> apiResourceList = this.apiResourceService.list();
        if (ObjectUtil.isNotEmpty(apiResourceList)) {
            for (ApiResource apiResource : apiResourceList) {
                ApiGroupTreeWrapper item = new ApiGroupTreeWrapper();
                item.setId(apiResource.getApiResourceId());
                item.setPid(apiResource.getGroupId());
                item.setName(apiResource.getApiAlias());
                item.setType(NodeTypeEnums.DATA_NODE.getType());
                item.setSort(apiResource.getResourceSort());
                item.setData(apiResource);
                item.setSlotsValue();
                SysResource sysResource = stringSysResourceMap.get(apiResource.getResourceCode());
                if (ObjectUtil.isNotEmpty(sysResource)) {
                    item.setUrl(sysResource.getUrl());
                }
                allApiGroupTreeWrapperList.add(item);
            }
        }
        return allApiGroupTreeWrapperList;
    }

    @Override
    public List<ApiGroupTreeWrapper> groupTree(ApiGroupRequest apiGroupRequest) {
        // ??????
        List<ApiGroupTreeWrapper> apiGroupTreeWrapperList = new ArrayList<>();

        // ??????????????????
        LambdaQueryWrapper<ApiGroup> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(apiGroupRequest.getGroupId())) {
            wrapper.notLike(ApiGroup::getGroupPids, SymbolConstant.LEFT_SQUARE_BRACKETS + apiGroupRequest.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
            wrapper.ne(ApiGroup::getGroupId, apiGroupRequest.getGroupId());
        }
        List<ApiGroup> apiGroups = this.list(wrapper);

        if (ObjectUtil.isNotEmpty(apiGroups)) {
            for (ApiGroup apiGroup : apiGroups) {
                ApiGroupTreeWrapper item = new ApiGroupTreeWrapper();
                item.setId(apiGroup.getGroupId());
                item.setPid(apiGroup.getGroupPid());
                item.setName(apiGroup.getGroupName());
                item.setType(NodeTypeEnums.LEAF_NODE.getType());
                item.setSort(apiGroup.getGroupSort());
                item.setData(apiGroup);
                item.setSlotsValue();
                apiGroupTreeWrapperList.add(item);
            }
        }

        return this.createTree(apiGroupTreeWrapperList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTreeSort(List<TreeSortRequest> treeSortRequestList) {

        // ??????????????????
        List<ApiGroup> apiGroupList = new ArrayList<>();

        // ??????????????????
        List<ApiResource> apiResourceList = new ArrayList<>();

        // ????????????
        for (TreeSortRequest treeSortRequest : treeSortRequestList) {
            if (NodeTypeEnums.LEAF_NODE.getType().equals(treeSortRequest.getNodeType())) {
                ApiGroup item = new ApiGroup();
                item.setGroupId(treeSortRequest.getNodeId());
                item.setGroupPid(treeSortRequest.getNodePid());
                item.setGroupSort(treeSortRequest.getNodeSort());
                apiGroupList.add(item);
            } else {
                ApiResource item = new ApiResource();
                item.setApiResourceId(treeSortRequest.getNodeId());
                item.setGroupId(treeSortRequest.getNodePid());
                item.setResourceSort(treeSortRequest.getNodeSort());
                apiResourceList.add(item);
            }
        }

        // ????????????????????????
        if (ObjectUtil.isNotEmpty(apiGroupList)) {
            for (ApiGroup apiGroup : apiGroupList) {
                ApiGroup oldApiGroup = this.getById(apiGroup.getGroupId());
                this.setPids(apiGroup);
                this.updatePids(apiGroup, oldApiGroup);
            }
            this.updateBatchById(apiGroupList);
        }

        // ????????????????????????
        if (ObjectUtil.isNotEmpty(apiResourceList)) {
            this.apiResourceService.updateBatchById(apiResourceList);
        }
    }

    @Override
    public List<ApiGroup> findList(ApiGroupRequest apiGroupRequest) {
        LambdaQueryWrapper<ApiGroup> wrapper = this.createWrapper(apiGroupRequest);
        List<ApiGroup> apiGroupList = this.list(wrapper);
        return apiGroupList;
    }

    /**
     * ????????????
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private ApiGroup queryApiGroup(ApiGroupRequest apiGroupRequest) {
        ApiGroup apiGroup = this.getById(apiGroupRequest.getGroupId());
        if (ObjectUtil.isEmpty(apiGroup)) {
            throw new SystemModularException(ApiGroupExceptionEnum.APIGROUP_NOT_EXISTED);
        }
        return apiGroup;
    }

    /**
     * ????????????wrapper
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private LambdaQueryWrapper<ApiGroup> createWrapper(ApiGroupRequest apiGroupRequest) {
        LambdaQueryWrapper<ApiGroup> queryWrapper = new LambdaQueryWrapper<>();

        Long groupId = apiGroupRequest.getGroupId();
        String groupName = apiGroupRequest.getGroupName();
        Long groupPid = apiGroupRequest.getGroupPid();
        String groupPids = apiGroupRequest.getGroupPids();

        queryWrapper.eq(ObjectUtil.isNotNull(groupId), ApiGroup::getGroupId, groupId);
        queryWrapper.like(ObjectUtil.isNotEmpty(groupName), ApiGroup::getGroupName, groupName);
        queryWrapper.eq(ObjectUtil.isNotNull(groupPid), ApiGroup::getGroupPid, groupPid);
        queryWrapper.like(ObjectUtil.isNotEmpty(groupPids), ApiGroup::getGroupPids, groupPids);

        return queryWrapper;
    }

    /**
     * ?????????ID??????
     *
     * @author majianguo
     * @date 2021/5/22 ??????9:59
     **/
    private void setPids(ApiGroup apiGroup) {
        // ??????????????????pids
        if (BaseConstants.TREE_ROOT_ID.equals(apiGroup.getGroupPid())) {
            // ????????????
            apiGroup.setGroupPids(SymbolConstant.LEFT_SQUARE_BRACKETS + BaseConstants.TREE_ROOT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS);
        } else {
            ApiGroup pApiGroup = this.getById(apiGroup.getGroupPid());
            apiGroup.setGroupPids(pApiGroup.getGroupPids() + SymbolConstant.COMMA + SymbolConstant.LEFT_SQUARE_BRACKETS + pApiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
        }
    }

    /**
     * ??????pids
     *
     * @author majianguo
     * @date 2021/5/22 ??????9:59
     **/
    private void updatePids(ApiGroup apiGroup, ApiGroup oldApiGroup) {
        String oldPids = oldApiGroup.getGroupPids();
        oldPids = oldPids + SymbolConstant.COMMA + SymbolConstant.LEFT_SQUARE_BRACKETS + oldApiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS;
        ParentIdsUpdateRequest parentIdsUpdateRequest = createParenIdsUpdateRequest(apiGroup.getGroupPids() + SymbolConstant.COMMA + SymbolConstant.LEFT_SQUARE_BRACKETS + apiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS, oldPids);
        this.baseMapper.updateSubPids(parentIdsUpdateRequest);
    }

    /**
     * ????????????pids?????????
     *
     * @author ?????????
     * @date 2020/12/26 12:19
     */
    private static ParentIdsUpdateRequest createParenIdsUpdateRequest(String newParentIds, String oldParentIds) {
        ParentIdsUpdateRequest parentIdsUpdateRequest = new ParentIdsUpdateRequest();
        parentIdsUpdateRequest.setNewParentIds(newParentIds);
        parentIdsUpdateRequest.setOldParentIds(oldParentIds);
        parentIdsUpdateRequest.setUpdateTime(new Date());
        parentIdsUpdateRequest.setUpdateUser(LoginContext.me().getLoginUser().getUid());
        return parentIdsUpdateRequest;
    }

    /**
     * ?????????????????????
     *
     * @author majianguo
     * @date 2021/5/22 ??????11:08
     **/
    private List<ApiGroupTreeWrapper> createTree(List<ApiGroupTreeWrapper> orgTrees) {
        // ?????????
        ApiGroupTreeWrapper root = null;

        // ??????MAP
        Map<String, ApiGroupTreeWrapper> dataMap = new HashMap<>(orgTrees.size());
        for (ApiGroupTreeWrapper orgTree : orgTrees) {
            if (orgTree.getNodeParentId().equals(BaseConstants.TREE_ROOT_ID.toString())) {
                root = orgTree;
            }
            dataMap.put(orgTree.getNodeId(), orgTree);
        }

        // ??????????????????
        orgTrees.parallelStream().forEach(item -> {
            // ???root???????????????
            if (!BaseConstants.TREE_ROOT_ID.toString().equals(item.getNodeParentId())) {
                // ??????????????????????????????????????????
                if (ObjectUtil.isEmpty(item.getSort())) {
                    item.setSort(BigDecimal.valueOf(9999));
                }
                ApiGroupTreeWrapper orgTreeWrapper = dataMap.get(item.getNodeParentId());
                // ????????????????????????????????????????????????????????????????????????
                if (ObjectUtil.isNotEmpty(orgTreeWrapper)) {
                    dataMap.get(item.getNodeParentId()).getChildren().add(item);
                }
            }
        });

        // ?????????????????????
        root.sortChildren();
        return Collections.singletonList(root);
    }
}