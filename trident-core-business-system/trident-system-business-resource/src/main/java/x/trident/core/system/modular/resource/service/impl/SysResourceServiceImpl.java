
package x.trident.core.system.modular.resource.service.impl;

import cn.hutool.core.util.ObjectUtil;
import x.trident.core.auth.api.LoginUserApi;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.auth.api.pojo.login.basic.SimpleRoleInfo;
import x.trident.core.cache.api.CacheOperatorApi;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.constants.BaseConstants;
import x.trident.core.constants.TreeConstants;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.tree.factory.DefaultTreeBuildFactory;
import x.trident.core.scanner.api.ResourceReportApi;
import x.trident.core.scanner.api.pojo.resource.ReportResourceParam;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import x.trident.core.scanner.api.pojo.resource.ResourceUrlParam;
import x.trident.core.system.api.ResourceServiceApi;
import x.trident.core.system.api.RoleServiceApi;
import x.trident.core.system.api.pojo.resource.LayuiApiResourceTreeNode;
import x.trident.core.system.api.pojo.resource.ResourceRequest;
import x.trident.core.system.api.pojo.role.dto.SysRoleResourceDTO;
import x.trident.core.system.modular.resource.entity.SysResource;
import x.trident.core.system.modular.resource.factory.ResourceFactory;
import x.trident.core.system.modular.resource.mapper.SysResourceMapper;
import x.trident.core.system.modular.resource.pojo.ResourceTreeNode;
import x.trident.core.system.modular.resource.service.SysResourceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ????????? ???????????????
 *
 * @author ?????????
 * @date 2020/11/23 22:45
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService, ResourceReportApi, ResourceServiceApi {

    @Resource
    private SysResourceMapper resourceMapper;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource(name = "resourceCache")
    private CacheOperatorApi<ResourceDefinition> resourceCache;

    @Override
    public PageResult<SysResource> findPage(ResourceRequest resourceRequest) {
        LambdaQueryWrapper<SysResource> wrapper = createWrapper(resourceRequest);
        Page<SysResource> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysResource> findList(ResourceRequest resourceRequest) {

        LambdaQueryWrapper<SysResource> wrapper = createWrapper(resourceRequest);

        // ?????????code???name
        wrapper.select(SysResource::getResourceCode, SysResource::getResourceName);

        List<SysResource> menuResourceList = this.list(wrapper);

        // ?????????????????????????????????
        SysResource sysResource = new SysResource();
        sysResource.setResourceCode("");
        sysResource.setResourceName("????????????(???)");
        menuResourceList.add(0, sysResource);

        return menuResourceList;
    }

    @Override
    public List<ResourceTreeNode> getResourceTree(Long roleId, Boolean treeBuildFlag) {

        List<ResourceTreeNode> res = new ArrayList<>();

        // ?????????????????????
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.select(SysResource::getAppCode, SysResource::getModularCode, SysResource::getModularName, SysResource::getResourceCode, SysResource::getUrl, SysResource::getResourceName);

        // ??????????????????????????????
        sysResourceLambdaQueryWrapper.eq(SysResource::getRequiredPermissionFlag, YesOrNotEnum.Y.getCode());

        LoginUserApi loginUserApi = LoginContext.me();
        if (!loginUserApi.getSuperAdminFlag()) {
            // ??????????????????
            List<Long> roleIds = loginUserApi.getLoginUser().getSimpleRoleInfoList().parallelStream().map(SimpleRoleInfo::getRoleId).collect(Collectors.toList());
            List<String> resourceCodeList = roleServiceApi.getRoleResourceCodeList(roleIds);
            if (!resourceCodeList.isEmpty()) {
                sysResourceLambdaQueryWrapper.in(SysResource::getResourceCode, resourceCodeList);
            }
        }

        List<SysResource> allResource = this.list(sysResourceLambdaQueryWrapper);

        // ?????????????????????????????????
        List<SysRoleResourceDTO> resourceList = roleServiceApi.getRoleResourceList(Collections.singletonList(roleId));

        // ????????????????????????
        Map<String, SysRoleResourceDTO> alreadyHave = new HashMap<>(resourceList.size());
        for (SysRoleResourceDTO sysRoleResponse : resourceList) {
            alreadyHave.put(sysRoleResponse.getResourceCode(), sysRoleResponse);
        }

        // ?????????????????????????????????
        Map<String, List<SysResource>> modularMap = new HashMap<>();
        for (SysResource sysResource : allResource) {
            List<SysResource> sysResources = modularMap.get(sysResource.getModularName());

            // ?????????????????????
            if (ObjectUtil.isEmpty(sysResources)) {
                sysResources = new ArrayList<>();
                modularMap.put(sysResource.getModularName(), sysResources);
            }
            // ?????????????????????
            sysResources.add(sysResource);
        }

        // ??????????????????
        for (Map.Entry<String, List<SysResource>> entry : modularMap.entrySet()) {
            ResourceTreeNode item = new ResourceTreeNode();
            item.setResourceFlag(false);
            String id = IdWorker.get32UUID();
            item.setCode(id);
            item.setParentCode(BaseConstants.TREE_ROOT_ID.toString());
            item.setNodeName(entry.getKey());
            item.setChecked(false);
            //??????????????????
            for (SysResource resource : entry.getValue()) {
                ResourceTreeNode subItem = new ResourceTreeNode();
                // ????????????????????????
                SysRoleResourceDTO resourceResponse = alreadyHave.get(resource.getResourceCode());
                if (ObjectUtil.isEmpty(resourceResponse)) {
                    subItem.setChecked(false);
                } else {
                    // ??????????????????
                    item.setChecked(true);
                    subItem.setChecked(true);
                }
                subItem.setResourceFlag(true);
                subItem.setNodeName(resource.getResourceName());
                subItem.setCode(resource.getResourceCode());
                subItem.setParentCode(id);
                res.add(subItem);
            }
            res.add(item);
        }

        // ??????map???????????????
        if (treeBuildFlag) {
            return new DefaultTreeBuildFactory<ResourceTreeNode>().doTreeBuild(res);
        } else {
            return res;
        }
    }

    @Override
    public List<LayuiApiResourceTreeNode> getApiResourceTree() {

        // 1. ?????????????????????
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getViewFlag, YesOrNotEnum.N.getCode());
        sysResourceLambdaQueryWrapper.select(SysResource::getAppCode, SysResource::getModularCode, SysResource::getModularName, SysResource::getResourceCode, SysResource::getUrl, SysResource::getResourceName);
        List<SysResource> allResource = this.list(sysResourceLambdaQueryWrapper);

        // 2. ??????????????????????????????map
        Map<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources = divideResources(allResource);

        // 3. ????????????code?????????name?????????
        Map<String, String> modularCodeName = createModularCodeName(allResource);

        // 4. ??????map???????????????
        return createResourceTree(appModularResources, modularCodeName);
    }

    @Override
    public ResourceDefinition getApiResourceDetail(ResourceRequest resourceRequest) {
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getResourceCode, resourceRequest.getResourceCode());
        SysResource sysResource = this.getOne(sysResourceLambdaQueryWrapper);
        if (sysResource != null) {

            // ???????????????ResourceDefinition
            ResourceDefinition resourceDefinition = ResourceFactory.createResourceDefinition(sysResource);

            // ???????????????????????????
            return ResourceFactory.fillResourceDetail(resourceDefinition);
        } else {
            return null;
        }
    }

    @Override
    public void deleteResourceByProjectCode(String projectCode) {
        LambdaQueryWrapper<SysResource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysResource::getProjectCode, projectCode);
        this.remove(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportResources(@RequestBody ReportResourceParam reportResourceReq) {

        String projectCode = reportResourceReq.getProjectCode();
        Map<String, Map<String, ResourceDefinition>> resourceDefinitions = reportResourceReq.getResourceDefinitions();

        if (ObjectUtil.isEmpty(projectCode) || resourceDefinitions == null) {
            return;
        }

        //??????project?????????????????????????????????
        this.deleteResourceByProjectCode(projectCode);

        //?????????????????????????????????
        ArrayList<SysResource> allResources = new ArrayList<>();
        ArrayList<ResourceDefinition> resourceDefinitionArrayList = new ArrayList<>();
        for (Map.Entry<String, Map<String, ResourceDefinition>> appModularResources : resourceDefinitions.entrySet()) {
            Map<String, ResourceDefinition> value = appModularResources.getValue();
            for (Map.Entry<String, ResourceDefinition> modularResources : value.entrySet()) {
                resourceDefinitionArrayList.add(modularResources.getValue());
                SysResource resource = ResourceFactory.createResource(modularResources.getValue());
                allResources.add(resource);
            }
        }

        //?????????????????????
        this.saveBatch(allResources, allResources.size());

        //???????????????????????????
        Map<String, ResourceDefinition> resourceDefinitionMap = ResourceFactory.orderedResourceDefinition(resourceDefinitionArrayList);
        for (Map.Entry<String, ResourceDefinition> entry : resourceDefinitionMap.entrySet()) {
            resourceCache.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public ResourceDefinition getResourceByUrl(@RequestBody ResourceUrlParam resourceUrlReq) {
        if (ObjectUtil.isEmpty(resourceUrlReq.getUrl())) {
            return null;
        } else {

            List<SysResource> resources = resourceMapper.selectList(new QueryWrapper<SysResource>().eq("url", resourceUrlReq.getUrl()));

            if (resources == null || resources.isEmpty()) {
                return null;
            } else {

                // ????????????????????????
                SysResource resource = resources.get(0);
                ResourceDefinition resourceDefinition = new ResourceDefinition();
                BeanUtils.copyProperties(resource, resourceDefinition);

                // ?????????????????????????????????, ????????????????????????????????????????????????true,?????????false
                String requiredLoginFlag = resource.getRequiredLoginFlag();
                resourceDefinition.setRequiredLoginFlag(YesOrNotEnum.Y.name().equals(requiredLoginFlag));

                // ???????????????????????????????????????????????????????????????????????????true,?????????false
                String requiredPermissionFlag = resource.getRequiredPermissionFlag();
                resourceDefinition.setRequiredPermissionFlag(YesOrNotEnum.Y.name().equals(requiredPermissionFlag));

                return resourceDefinition;
            }
        }
    }

    @Override
    public Set<String> getResourceUrlsListByCodes(List<String> resourceCodes) {

        if (resourceCodes == null || resourceCodes.isEmpty()) {
            return new HashSet<>();
        }

        // ??????in??????
        LambdaQueryWrapper<SysResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysResource::getResourceCode, resourceCodes);
        queryWrapper.select(SysResource::getUrl);

        // ??????????????????
        List<SysResource> list = this.list(queryWrapper);
        return list.stream().map(SysResource::getUrl).collect(Collectors.toSet());
    }

    /**
     * ??????wrapper
     *
     * @author ?????????
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysResource> createWrapper(ResourceRequest resourceRequest) {
        LambdaQueryWrapper<SysResource> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isEmpty(resourceRequest)) {
            return queryWrapper;
        }

        // ????????????????????????
        queryWrapper.eq(ObjectUtil.isNotEmpty(resourceRequest.getAppCode()), SysResource::getAppCode, resourceRequest.getAppCode());

        // ??????????????????
        queryWrapper.like(ObjectUtil.isNotEmpty(resourceRequest.getResourceName()), SysResource::getResourceName, resourceRequest.getResourceName());

        // ????????????url
        queryWrapper.like(ObjectUtil.isNotEmpty(resourceRequest.getUrl()), SysResource::getUrl, resourceRequest.getUrl());

        return queryWrapper;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     *
     * @return ?????????key???????????????????????????key??????????????????????????????????????????????????????????????????
     * @author ?????????
     * @date 2020/12/18 15:34
     */
    private Map<String, Map<String, List<LayuiApiResourceTreeNode>>> divideResources(List<SysResource> sysResources) {
        HashMap<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources = new HashMap<>();
        for (SysResource sysResource : sysResources) {

            // ???????????????????????????
            String appCode = sysResource.getAppCode();
            Map<String, List<LayuiApiResourceTreeNode>> modularResource = appModularResources.get(appCode);

            // ????????????????????????????????????map
            if (modularResource == null) {
                modularResource = new HashMap<>();
            }

            // ??????????????????????????????????????????appModularResources??????
            List<LayuiApiResourceTreeNode> resourceTreeNodes = modularResource.get(sysResource.getModularCode());
            if (resourceTreeNodes == null) {
                resourceTreeNodes = new ArrayList<>();
            }

            // ?????????????????????????????????
            LayuiApiResourceTreeNode resourceTreeNode = new LayuiApiResourceTreeNode();
            resourceTreeNode.setResourceFlag(true);
            resourceTreeNode.setTitle(sysResource.getResourceName());
            resourceTreeNode.setId(sysResource.getResourceCode());
            resourceTreeNode.setParentId(sysResource.getModularCode());
            resourceTreeNode.setUrl(sysResource.getUrl());
            resourceTreeNode.setSpread(false);
            resourceTreeNode.setSlotsValue();
            resourceTreeNodes.add(resourceTreeNode);

            modularResource.put(sysResource.getModularCode(), resourceTreeNodes);
            appModularResources.put(appCode, modularResource);
        }
        return appModularResources;
    }

    /**
     * ????????????code???name?????????
     *
     * @author ?????????
     * @date 2020/12/21 11:23
     */
    private Map<String, String> createModularCodeName(List<SysResource> resources) {
        HashMap<String, String> modularCodeName = new HashMap<>();
        for (SysResource resource : resources) {
            modularCodeName.put(resource.getModularCode(), resource.getModularName());
        }
        return modularCodeName;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @author ?????????
     * @date 2020/12/18 15:45
     */
    private List<LayuiApiResourceTreeNode> createResourceTree(Map<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources, Map<String, String> modularCodeName) {

        List<LayuiApiResourceTreeNode> finalTree = new ArrayList<>();

        // ???????????????????????????????????????
        for (String appName : appModularResources.keySet()) {

            // ????????????????????????
            LayuiApiResourceTreeNode appNode = new LayuiApiResourceTreeNode();
            appNode.setId(appName);
            appNode.setTitle(appName);
            appNode.setSpread(true);
            appNode.setResourceFlag(false);
            appNode.setSlotsValue();
            appNode.setParentId(TreeConstants.DEFAULT_PARENT_ID.toString());

            // ????????????????????????????????????
            Map<String, List<LayuiApiResourceTreeNode>> modularResources = appModularResources.get(appName);

            // ??????????????????
            ArrayList<LayuiApiResourceTreeNode> modularNodes = new ArrayList<>();
            for (String modularCode : modularResources.keySet()) {
                LayuiApiResourceTreeNode modularNode = new LayuiApiResourceTreeNode();
                modularNode.setId(modularCode);
                modularNode.setTitle(modularCodeName.get(modularCode));
                modularNode.setParentId(appName);
                modularNode.setSpread(false);
                modularNode.setResourceFlag(false);
                modularNode.setChildren(modularResources.get(modularCode));
                modularNode.setSlotsValue();
                modularNodes.add(modularNode);
            }

            // ????????????????????????????????????
            appNode.setChildren(modularNodes);

            // ?????????????????????
            finalTree.add(appNode);
        }

        return finalTree;
    }

}
