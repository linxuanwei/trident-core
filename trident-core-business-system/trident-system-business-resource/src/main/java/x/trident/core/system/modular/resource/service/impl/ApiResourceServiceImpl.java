package x.trident.core.system.modular.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.constants.SymbolConstant;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.scanner.api.pojo.resource.FieldMetadata;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import x.trident.core.system.api.exception.SystemModularException;
import x.trident.core.system.api.exception.enums.resource.ApiResourceExceptionEnum;
import x.trident.core.system.api.pojo.resource.ApiResourceFieldRequest;
import x.trident.core.system.api.pojo.resource.ApiResourceRequest;
import x.trident.core.system.modular.resource.entity.ApiGroup;
import x.trident.core.system.modular.resource.entity.ApiResource;
import x.trident.core.system.modular.resource.entity.ApiResourceField;
import x.trident.core.system.modular.resource.entity.SysResource;
import x.trident.core.system.modular.resource.factory.ResourceFactory;
import x.trident.core.system.modular.resource.mapper.ApiResourceMapper;
import x.trident.core.system.modular.resource.service.ApiGroupService;
import x.trident.core.system.modular.resource.service.ApiResourceFieldService;
import x.trident.core.system.modular.resource.service.ApiResourceService;
import x.trident.core.system.modular.resource.service.SysResourceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ???????????????????????????
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@Service
public class ApiResourceServiceImpl extends ServiceImpl<ApiResourceMapper, ApiResource> implements ApiResourceService {

    @Autowired
    private ApiGroupService apiGroupService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private ApiResourceFieldService apiResourceFieldService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ApiResourceRequest apiResourceRequest) {
        // ????????????????????????????????????????????????
        ApiGroup apiGroup = this.apiGroupService.getById(apiResourceRequest.getGroupId());
        if (ObjectUtil.isEmpty(apiGroup)) {
            throw new SystemModularException(ApiResourceExceptionEnum.OPERATIONS_RESOURCE_NODESNOT_ALLOWED);
        }

        List<SysResource> resourceList = new ArrayList<>();

        // ??????????????????
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();

        // ????????????????????????????????????????????????????????????
        if (!apiResourceRequest.getResourceCode().contains("$")) {
            sysResourceLambdaQueryWrapper.eq(SysResource::getModularCode, apiResourceRequest.getResourceCode());
            sysResourceLambdaQueryWrapper.eq(SysResource::getViewFlag, YesOrNotEnum.N.getCode());
            List<SysResource> sysResourceList = sysResourceService.list(sysResourceLambdaQueryWrapper);
            resourceList.addAll(sysResourceList);
        } else {

            // ????????????CODE??????????????????
            sysResourceLambdaQueryWrapper.eq(SysResource::getResourceCode, apiResourceRequest.getResourceCode());
            SysResource sysResources = sysResourceService.getOne(sysResourceLambdaQueryWrapper);

            // ????????????????????????
            if (YesOrNotEnum.Y.getCode().equals(sysResources.getViewFlag())) {
                throw new SystemModularException(ApiResourceExceptionEnum.ADDING_VIEW_RESOURCES_NOT_ALLOWED);
            }

            resourceList.add(sysResources);
        }

        // ??????????????????
        BigDecimal index = BigDecimal.ZERO;

        // ??????????????????????????????????????????
        LambdaQueryWrapper<ApiResource> apiResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiResourceLambdaQueryWrapper.eq(ApiResource::getGroupId, apiResourceRequest.getGroupId());
        apiResourceLambdaQueryWrapper.orderByDesc(ApiResource::getResourceSort);
        apiResourceLambdaQueryWrapper.last("LIMIT 1");
        ApiResource resource = this.getOne(apiResourceLambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(resource)) {
            index = resource.getResourceSort();
        }

        // ??????????????????
        for (SysResource sysResource : resourceList) {
            // ????????????
            ApiResource apiResource = BeanUtil.toBean(apiResourceRequest, ApiResource.class);

            // ??????????????????
            apiResource.setRequestMethod(sysResource.getHttpMethod());

            // ??????????????????
            apiResource.setApiAlias(sysResource.getResourceName());

            // ????????????code
            apiResource.setResourceCode(sysResource.getResourceCode());

            // ????????????
            if (ObjectUtil.isEmpty(apiResourceRequest.getResourceSort())) {
                index = index.add(BigDecimal.ONE);
                apiResource.setResourceSort(index);
            }

            // ????????????
            this.save(apiResource);

            // ????????????
            ResourceDefinition fillResourceDetail = ResourceFactory.fillResourceDetail(ResourceFactory.createResourceDefinition(sysResource));

            // ??????????????????
            List<ApiResourceField> apiResourceFieldList = new ArrayList<>();

            // ????????????????????????
            if (ObjectUtil.isNotEmpty(fillResourceDetail.getParamFieldDescriptions())) {
                for (FieldMetadata fieldMetadata : fillResourceDetail.getParamFieldDescriptions()) {
                    ApiResourceField conversion = this.conversion(sysResource, apiResource.getApiResourceId(), fieldMetadata);
                    conversion.setFieldLocation("request");
                    apiResourceFieldList.add(conversion);
                }
            }

            // ????????????????????????
            if (ObjectUtil.isNotEmpty(fillResourceDetail.getResponseFieldDescriptions())) {
                for (FieldMetadata fieldDescription : fillResourceDetail.getResponseFieldDescriptions()) {
                    ApiResourceField conversion = this.conversion(sysResource, apiResource.getApiResourceId(), fieldDescription);
                    conversion.setFieldLocation("response");
                    apiResourceFieldList.add(conversion);
                }
            }

            // ??????
            if (ObjectUtil.isNotEmpty(apiResourceFieldList)) {
                this.apiResourceFieldService.saveBatch(apiResourceFieldList);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(ApiResourceRequest apiResourceRequest) {
        ApiResource apiResource = this.queryApiResource(apiResourceRequest);
        this.removeById(apiResource.getApiResourceId());

        // ????????????????????????
        LambdaQueryWrapper<ApiResourceField> apiResourceFieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiResourceFieldLambdaQueryWrapper.eq(ApiResourceField::getApiResourceId, apiResource.getApiResourceId());
        this.apiResourceFieldService.remove(apiResourceFieldLambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(ApiResourceRequest apiResourceRequest) {

        // ???????????????
        ApiResource apiResource = this.queryApiResource(apiResourceRequest);

        // ????????????????????????????????????????????????
        ApiGroup apiGroup = this.apiGroupService.getById(apiResource.getGroupId());
        if (ObjectUtil.isEmpty(apiGroup)) {
            throw new SystemModularException(ApiResourceExceptionEnum.OPERATIONS_RESOURCE_NODESNOT_ALLOWED);
        }

        // ????????????????????????
        BeanUtil.copyProperties(apiResourceRequest, apiResource);

        // ??????????????????
        this.updateById(apiResource);

        // ???????????????????????????
        LambdaQueryWrapper<ApiResourceField> fieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fieldLambdaQueryWrapper.eq(ApiResourceField::getApiResourceId, apiResource.getApiResourceId());
        this.apiResourceFieldService.remove(fieldLambdaQueryWrapper);

        // ????????????????????????
        List<ApiResourceFieldRequest> apiResourceFieldRequestList = apiResourceRequest.getApiResourceFieldRequestList();
        if (ObjectUtil.isNotEmpty(apiResourceFieldRequestList)) {
            List<ApiResourceField> apiResourceFields = apiResourceFieldRequestList.stream().map(item -> {
                ApiResourceField apiResourceField = BeanUtil.toBean(item, ApiResourceField.class);
                apiResourceField.setApiResourceId(apiResource.getApiResourceId());
                return apiResourceField;
            }).collect(Collectors.toList());
            this.apiResourceFieldService.saveBatch(apiResourceFields);
        }
    }

    @Override
    public ApiResource detail(ApiResourceRequest apiResourceRequest) {
        ApiResource apiResource = this.queryApiResource(apiResourceRequest);

        // ????????????????????????
        LambdaQueryWrapper<ApiResourceField> apiResourceFieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiResourceFieldLambdaQueryWrapper.eq(ApiResourceField::getApiResourceId, apiResource.getApiResourceId());
        List<ApiResourceField> apiResourceFields = this.apiResourceFieldService.list(apiResourceFieldLambdaQueryWrapper);
        // ??????????????????????????????
        apiResourceFields.removeIf(resourceField -> "createTime".equalsIgnoreCase(resourceField.getFieldCode()) || "createUser".equalsIgnoreCase(resourceField.getFieldCode()) || "updateTime".equalsIgnoreCase(resourceField.getFieldCode()) || "updateUser".equalsIgnoreCase(resourceField.getFieldCode()));
        apiResource.setApiResourceFieldList(apiResourceFields);

        return apiResource;
    }

    @Override
    public PageResult<ApiResource> findPage(ApiResourceRequest apiResourceRequest) {
        LambdaQueryWrapper<ApiResource> wrapper = createWrapper(apiResourceRequest);
        Page<ApiResource> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public ApiResource record(ApiResourceRequest apiResourceRequest) {
        ApiResource apiResource = BeanUtil.toBean(apiResourceRequest, ApiResource.class);

        String resultBody = null;

        try {
            // ????????????
            HttpRequest httpRequest;

            // ??????????????????
            if (RequestMethod.GET.toString().equalsIgnoreCase(apiResourceRequest.getRequestMethod())) {
                httpRequest = HttpRequest.get(apiResourceRequest.getRequestUrl());
                // ????????????
                JSONObject jsonObject = JSONObject.parseObject(apiResourceRequest.getLastRequestContent());
                for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
                    httpRequest.form(stringObjectEntry.getKey(), stringObjectEntry.getValue());
                }
            } else {
                httpRequest = HttpRequest.post(apiResourceRequest.getRequestUrl()).body(apiResourceRequest.getLastRequestContent());
            }

            // ???????????????
            if (ObjectUtil.isNotEmpty(apiResourceRequest.getLastRequestHeader())) {
                for (Map.Entry<String, String> stringStringEntry : apiResourceRequest.getLastRequestHeader().entrySet()) {
                    String[] values = stringStringEntry.getValue().split(SymbolConstant.COMMA);
                    for (String value : values) {
                        httpRequest.header(stringStringEntry.getKey(), value, false);
                    }
                }
            }

            // ????????????
            resultBody = httpRequest.timeout(15000).execute().body();

        } catch (Exception e) {
            resultBody = ExceptionUtil.stacktraceToString(e, 10000);
        } finally {
            // ???????????????
            if (ObjectUtil.isNotEmpty(apiResourceRequest.getLastRequestHeader())) {
                apiResource.setLastRequestHeader(JSON.toJSONString(apiResourceRequest.getLastRequestHeader()));
            }
            apiResource.setLastResponseContent(resultBody);
            this.updateById(apiResource);
        }
        return apiResource;
    }

    @Override
    public List<ApiResourceField> allField(ApiResourceRequest apiResourceRequest) {
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getResourceCode, apiResourceRequest.getResourceCode());
        SysResource sysResources = sysResourceService.getOne(sysResourceLambdaQueryWrapper);

        // ????????????
        ResourceDefinition fillResourceDetail = ResourceFactory.fillResourceDetail(ResourceFactory.createResourceDefinition(sysResources));

        // ????????????????????????
        List<ApiResourceField> apiResourceFieldList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(fillResourceDetail.getParamFieldDescriptions())) {
            // ????????????????????????
            for (FieldMetadata fieldMetadata : fillResourceDetail.getParamFieldDescriptions()) {
                ApiResourceField conversion = this.conversion(sysResources, null, fieldMetadata);
                conversion.setFieldLocation("request");
                apiResourceFieldList.add(conversion);
            }

            // ????????????????????????
            for (FieldMetadata fieldDescription : fillResourceDetail.getResponseFieldDescriptions()) {
                ApiResourceField conversion = this.conversion(sysResources, null, fieldDescription);
                conversion.setFieldLocation("response");
                apiResourceFieldList.add(conversion);
            }
        }
        return apiResourceFieldList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResource reset(ApiResourceRequest apiResourceRequest) {

        ApiResource oldApiResource = this.getById(apiResourceRequest.getApiResourceId());

        // ???????????????
        this.del(apiResourceRequest);


        // ??????????????????
        apiResourceRequest.setResourceSort(oldApiResource.getResourceSort());
        this.add(apiResourceRequest);

        // ?????????????????????
        return this.getById(apiResourceRequest.getApiResourceId());
    }

    @Override
    public List<ApiResource> findList(ApiResourceRequest apiResourceRequest) {
        LambdaQueryWrapper<ApiResource> wrapper = this.createWrapper(apiResourceRequest);
        return this.list(wrapper);
    }

    /**
     * ????????????
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private ApiResource queryApiResource(ApiResourceRequest apiResourceRequest) {
        ApiResource apiResource = this.getById(apiResourceRequest.getApiResourceId());
        if (ObjectUtil.isEmpty(apiResource)) {
            throw new SystemModularException(ApiResourceExceptionEnum.APIRESOURCE_NOT_EXISTED);
        }
        return apiResource;
    }

    /**
     * ????????????wrapper
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private LambdaQueryWrapper<ApiResource> createWrapper(ApiResourceRequest apiResourceRequest) {
        LambdaQueryWrapper<ApiResource> queryWrapper = new LambdaQueryWrapper<>();

        Long apiResourceId = apiResourceRequest.getApiResourceId();
        Long groupId = apiResourceRequest.getGroupId();
        String apiAlias = apiResourceRequest.getApiAlias();
        String resourceCode = apiResourceRequest.getResourceCode();
        String lastRequestContent = apiResourceRequest.getLastRequestContent();
        String lastResponseContent = apiResourceRequest.getLastResponseContent();
        BigDecimal resourceSort = apiResourceRequest.getResourceSort();
        String createTime = apiResourceRequest.getCreateTime();
        Long createUser = apiResourceRequest.getCreateUser();
        String updateTime = apiResourceRequest.getUpdateTime();
        Long updateUser = apiResourceRequest.getUpdateUser();

        queryWrapper.eq(ObjectUtil.isNotNull(apiResourceId), ApiResource::getApiResourceId, apiResourceId);
        queryWrapper.eq(ObjectUtil.isNotNull(groupId), ApiResource::getGroupId, groupId);
        queryWrapper.like(ObjectUtil.isNotEmpty(apiAlias), ApiResource::getApiAlias, apiAlias);
        queryWrapper.like(ObjectUtil.isNotEmpty(resourceCode), ApiResource::getResourceCode, resourceCode);
        queryWrapper.like(ObjectUtil.isNotEmpty(lastRequestContent), ApiResource::getLastRequestContent, lastRequestContent);
        queryWrapper.like(ObjectUtil.isNotEmpty(lastResponseContent), ApiResource::getLastResponseContent, lastResponseContent);
        queryWrapper.eq(ObjectUtil.isNotNull(resourceSort), ApiResource::getResourceSort, resourceSort);
        queryWrapper.eq(ObjectUtil.isNotNull(createTime), ApiResource::getCreateTime, createTime);
        queryWrapper.eq(ObjectUtil.isNotNull(createUser), ApiResource::getCreateUser, createUser);
        queryWrapper.eq(ObjectUtil.isNotNull(updateTime), ApiResource::getUpdateTime, updateTime);
        queryWrapper.eq(ObjectUtil.isNotNull(updateUser), ApiResource::getUpdateUser, updateUser);

        return queryWrapper;
    }

    /**
     * ??????????????????
     *
     * @return {@link ApiResourceField}
     * @author majianguo
     * @date 2021/5/22 ??????2:44
     **/
    private ApiResourceField conversion(SysResource sysResource, Long apiResourceId, FieldMetadata fieldMetadata) {
        ApiResourceField item = new ApiResourceField();
        item.setApiResourceId(apiResourceId);
        item.setFieldCode(fieldMetadata.getFieldName());
        item.setFieldName(fieldMetadata.getChineseName());
        if ("file".equalsIgnoreCase(fieldMetadata.getFieldClassType())) {
            item.setFieldType("file");
        } else {
            item.setFieldType("string");
        }

        // ????????????
        item.setFieldRequired(this.isRequired(sysResource, fieldMetadata) ? YesOrNotEnum.Y.getCode() : YesOrNotEnum.N.getCode());

        // ??????????????????
        item.setFieldValidationMsg(fieldMetadata.getValidationMessages());

        return item;
    }

    /**
     * ????????????
     *
     * @author majianguo
     * @date 2021/5/27 ??????3:15
     **/
    private boolean isRequired(SysResource sysResource, FieldMetadata fieldMetadata) {
        Set<String> annotations = fieldMetadata.getAnnotations();
        if (ObjectUtil.isNotEmpty(annotations)) {
            // ??????????????????
            Set<String> validateGroups = JSON.parseObject(sysResource.getValidateGroups(), Set.class, Feature.SupportAutoType);
            if (ObjectUtil.isNotEmpty(validateGroups)) {
                // ????????????
                Map<String, Set<String>> groupValidationMessage = fieldMetadata.getGroupValidationMessage();
                for (String annotationName : annotations) {
                    if ("NotBlank".equalsIgnoreCase(annotationName) || "NotNull".equalsIgnoreCase(annotationName)) {
                        // ????????????????????????????????????????????????????????????
                        Set<String> annotationGroups = groupValidationMessage.keySet();
                        for (String group : annotationGroups) {
                            // ??????????????????????????????????????????????????????
                            if (validateGroups.contains(group)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}