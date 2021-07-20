
package x.trident.core.system.modular.resource.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import x.trident.core.enums.YesOrNotEnum;
import x.trident.core.scanner.api.pojo.resource.FieldMetadata;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import x.trident.core.system.modular.resource.entity.SysResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ResourceDefinition和SysResource实体互转
 *
 * @author 林选伟
 * @date 2019-05-29-14:37
 */
public class ResourceFactory {

    /**
     * ResourceDefinition转化为SysResource实体
     *
     * @author 林选伟
     * @date 2020/12/9 14:14
     */
    public static SysResource createResource(ResourceDefinition resourceDefinition) {
        SysResource resource = new SysResource();
        BeanUtil.copyProperties(resourceDefinition, resource, CopyOptions.create().ignoreError());
        resource.setResourceCode(resourceDefinition.getResourceCode());

        if (resourceDefinition.getRequiredLoginFlag()) {
            resource.setRequiredLoginFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setRequiredLoginFlag(YesOrNotEnum.N.name());
        }

        if (resourceDefinition.getRequiredPermissionFlag()) {
            resource.setRequiredPermissionFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setRequiredPermissionFlag(YesOrNotEnum.N.name());
        }

        if (resourceDefinition.getViewFlag()) {
            resource.setViewFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setViewFlag(YesOrNotEnum.N.name());
        }

        // 转化校验组
        if (ObjectUtil.isNotEmpty(resourceDefinition.getValidateGroups())) {
            resource.setValidateGroups(JSON.toJSONString(resourceDefinition.getValidateGroups(), SerializerFeature.WriteClassName));
        }

        // 转化接口参数的字段描述
        if (ObjectUtil.isNotEmpty(resourceDefinition.getParamFieldDescriptions())) {
            resource.setParamFieldDescriptions(JSON.toJSONString(resourceDefinition.getParamFieldDescriptions(), SerializerFeature.WriteClassName));
        }

        // 转化接口返回结果的字段描述
        if (ObjectUtil.isNotEmpty(resourceDefinition.getResponseFieldDescriptions())) {
            resource.setResponseFieldDescriptions(JSON.toJSONString(resourceDefinition.getResponseFieldDescriptions(), SerializerFeature.WriteClassName));
        }

        return resource;
    }

    /**
     * SysResource实体转化为ResourceDefinition对象
     *
     * @author 林选伟
     * @date 2020/12/9 14:15
     */
    public static ResourceDefinition createResourceDefinition(SysResource sysResource) {

        ResourceDefinition resourceDefinition = new ResourceDefinition();

        // 拷贝公共属性
        BeanUtil.copyProperties(sysResource, resourceDefinition, CopyOptions.create().ignoreError());

        // 设置是否需要登录标识，Y为需要登录
        resourceDefinition.setRequiredLoginFlag(YesOrNotEnum.Y.name().equals(sysResource.getRequiredLoginFlag()));

        // 设置是否需要权限认证标识，Y为需要权限认证
        resourceDefinition.setRequiredPermissionFlag(YesOrNotEnum.Y.name().equals(sysResource.getRequiredPermissionFlag()));

        // 设置是否是视图类型
        resourceDefinition.setViewFlag(YesOrNotEnum.Y.name().equals(sysResource.getViewFlag()));

        // 转化校验组
        if (ObjectUtil.isNotEmpty(sysResource.getValidateGroups())) {
            resourceDefinition.setValidateGroups(JSON.parseObject(sysResource.getValidateGroups(), Set.class, Feature.SupportAutoType));
        }

        // 转化接口参数的字段描述
        if (ObjectUtil.isNotEmpty(sysResource.getParamFieldDescriptions())) {
            resourceDefinition.setParamFieldDescriptions(JSON.parseObject(sysResource.getParamFieldDescriptions(), Set.class, Feature.SupportAutoType));
        }

        // 转化接口返回结果的字段描述
        if (ObjectUtil.isNotEmpty(sysResource.getResponseFieldDescriptions())) {
            resourceDefinition.setResponseFieldDescriptions(JSON.parseObject(sysResource.getResponseFieldDescriptions(), Set.class, Feature.SupportAutoType));
        }

        return resourceDefinition;
    }

    /**
     * ResourceDefinition转化为api界面的详情信息
     *
     * @author 林选伟
     * @date 2021/1/16 16:09
     */
    public static ResourceDefinition fillResourceDetail(ResourceDefinition resourceDefinition) {

        // 这个接口的校验组信息
        Set<String> validateGroups = resourceDefinition.getValidateGroups();

        // 接口的请求参数信息
        Set<FieldMetadata> paramFieldDescriptions = resourceDefinition.getParamFieldDescriptions();
        fillDetailMessage(validateGroups, paramFieldDescriptions);

        // 接口的响应参数信息
        Set<FieldMetadata> responseFieldDescriptions = resourceDefinition.getResponseFieldDescriptions();
        fillDetailMessage(validateGroups, responseFieldDescriptions);

        return resourceDefinition;
    }

    /**
     * 填充字段里详细的提示信息
     *
     * @author 林选伟
     * @date 2021/1/16 18:00
     */
    public static Set<FieldMetadata> fillDetailMessage(Set<String> validateGroups, Set<FieldMetadata> fieldMetadataSet) {
        if (validateGroups == null || validateGroups.isEmpty()) {
            return fieldMetadataSet;
        }

        if (fieldMetadataSet == null || fieldMetadataSet.isEmpty()) {
            return fieldMetadataSet;
        }
        for (FieldMetadata fieldMetadata : fieldMetadataSet) {
            StringBuilder finalValidateMessages = new StringBuilder();
            Map<String, Set<String>> groupAnnotations = fieldMetadata.getGroupValidationMessage();
            if (groupAnnotations != null) {
                for (String validateGroup : validateGroups) {
                    Set<String> validateMessage = groupAnnotations.get(validateGroup);
                    if (validateMessage != null && !validateMessage.isEmpty()) {
                        finalValidateMessages.append(StrUtil.join("，", validateMessage));
                    }
                }
            }
            fieldMetadata.setValidationMessages(finalValidateMessages.toString());

            // 递归填充子类型的详细提示信息
            if (fieldMetadata.getGenericFieldMetadata() != null && !fieldMetadata.getGenericFieldMetadata().isEmpty()) {
                fillDetailMessage(validateGroups, fieldMetadata.getGenericFieldMetadata());
            }
        }
        return fieldMetadataSet;
    }

    /**
     * 将资源的集合整理成一个map，key是url，value是ResourceDefinition
     *
     * @author 林选伟
     * @date 2021/5/17 16:21
     */
    public static Map<String, ResourceDefinition> orderedResourceDefinition(List<ResourceDefinition> sysResourceList) {

        HashMap<String, ResourceDefinition> result = new HashMap<>();

        if (ObjectUtil.isEmpty(sysResourceList)) {
            return result;
        }

        for (ResourceDefinition sysResource : sysResourceList) {
            String url = sysResource.getUrl();
            result.put(url,sysResource);
        }

        return result;
    }

}
