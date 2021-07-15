package x.trident.core.scanner;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import x.trident.core.scanner.api.ResourceCollectorApi;
import x.trident.core.scanner.api.annotation.ApiResource;
import x.trident.core.scanner.api.annotation.GetResource;
import x.trident.core.scanner.api.annotation.PostResource;
import x.trident.core.scanner.api.constants.ScannerConstants;
import x.trident.core.scanner.api.exception.ScannerException;
import x.trident.core.scanner.api.holder.IpAddrHolder;
import x.trident.core.scanner.api.pojo.resource.ResourceDefinition;
import x.trident.core.scanner.api.pojo.scanner.ScannerProperties;
import x.trident.core.scanner.api.util.ClassReflectUtil;
import x.trident.core.scanner.api.util.MethodReflectUtil;
import x.trident.core.util.AopTargetUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static x.trident.core.scanner.api.exception.enums.ScannerExceptionEnum.ERROR_CONTROLLER_NAME;

/**
 * 资源扫描器，扫描控制器上的@ApiResource，@GetResource，@PostResource
 *
 * @author 林选伟
 * @date 2020/10/19 22:31
 */
@Slf4j
public class ApiResourceScanner implements BeanPostProcessor {

    /**
     * 权限资源收集接口
     */
    private final ResourceCollectorApi resourceCollectorApi;

    /**
     * 项目的配置信息
     */
    private final ScannerProperties scannerProperties;

    public ApiResourceScanner(ResourceCollectorApi resourceCollectorApi, ScannerProperties scannerProperties) {
        this.resourceCollectorApi = resourceCollectorApi;
        this.scannerProperties = scannerProperties;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 如果controller是代理对象,则需要获取原始类的信息
        Object aopTarget = AopTargetUtils.getTarget(bean);

        if (aopTarget == null) {
            aopTarget = bean;
        }

        Class<?> clazz = aopTarget.getClass();

        // 判断是不是控制器,不是控制器就略过
        boolean controllerFlag = getControllerFlag(clazz);
        if (!controllerFlag) {
            return bean;
        }

        // 扫描控制器的所有带ApiResource注解的方法
        List<ResourceDefinition> apiResources = doScan(clazz);

        // 将扫描到的注解转化为资源实体存储到缓存
        persistApiResources(apiResources);

        return bean;
    }

    /**
     * 判断一个类是否是控制器
     */
    private boolean getControllerFlag(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (RestController.class.equals(annotation.annotationType()) || Controller.class.equals(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 扫描整个类中包含的所有@ApiResource资源
     */
    private List<ResourceDefinition> doScan(Class<?> clazz) {
        // 绑定类的code-中文名称映射
        ApiResource classApiAnnotation = clazz.getAnnotation(ApiResource.class);
        if (classApiAnnotation != null) {
            if (StrUtil.isEmpty(classApiAnnotation.code())) {
                String className = clazz.getSimpleName();
                int controllerIndex = className.indexOf("Controller");
                if (controllerIndex == -1) {
                    throw new IllegalArgumentException("controller class name is not illegal, it should end with Controller!");
                }
                String code = className.substring(0, controllerIndex);
                this.resourceCollectorApi.bindResourceName(StrUtil.toUnderlineCase(code), classApiAnnotation.name());
            } else {
                this.resourceCollectorApi.bindResourceName(StrUtil.toUnderlineCase(classApiAnnotation.code()), classApiAnnotation.name());
            }
        }

        ArrayList<ResourceDefinition> apiResources = new ArrayList<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if (declaredMethods.length > 0) {
            for (Method declaredMethod : declaredMethods) {
                ApiResource apiResource = declaredMethod.getAnnotation(ApiResource.class);
                GetResource getResource = declaredMethod.getAnnotation(GetResource.class);
                PostResource postResource = declaredMethod.getAnnotation(PostResource.class);

                Annotation annotation = null;
                if (apiResource != null) {
                    annotation = apiResource;
                }

                if (getResource != null) {
                    annotation = getResource;
                }

                if (postResource != null) {
                    annotation = postResource;
                }

                if (annotation != null) {
                    ResourceDefinition definition = createDefinition(clazz, declaredMethod, annotation);
                    apiResources.add(definition);
                    log.debug("扫描到资源: " + definition);
                }
            }
        }
        return apiResources;
    }

    /**
     * 缓存扫描到的api资源
     */
    private void persistApiResources(List<ResourceDefinition> apiResources) {
        resourceCollectorApi.collectResources(apiResources);
    }

    /**
     * 根据类信息，方法信息，注解信息创建ResourceDefinition对象
     */
    private ResourceDefinition createDefinition(Class<?> controllerClass, Method method, Annotation apiResource) {
        ResourceDefinition resourceDefinition = new ResourceDefinition();

        // 填充控制器类的名称
        resourceDefinition.setClassName(controllerClass.getSimpleName());

        // 填充方法名称
        resourceDefinition.setMethodName(method.getName());

        // 填充模块编码，模块编码就是控制器名称截取Controller关键字前边的字符串
        String className = resourceDefinition.getClassName();
        int controllerIndex = className.indexOf("Controller");
        if (controllerIndex == -1) {
            throw new ScannerException(ERROR_CONTROLLER_NAME, controllerClass.getName());
        }
        String modular = className.substring(0, controllerIndex);
        resourceDefinition.setModularCode(modular);

        // 填充模块的中文名称
        ApiResource classApiAnnotation = controllerClass.getAnnotation(ApiResource.class);
        resourceDefinition.setModularName(classApiAnnotation.name());

        // 如果控制器类上标识了appCode则应用标识上的appCode,如果控制器上没标识则用配置文件中的appCode
        if (StrUtil.isNotBlank(classApiAnnotation.appCode())) {
            resourceDefinition.setAppCode(classApiAnnotation.appCode());
        } else {
            resourceDefinition.setAppCode(scannerProperties.getAppCode());
        }

        // 如果没有填写code则用"模块名称_方法名称"为默认的标识
        String code = invokeAnnotationMethod(apiResource, "code", String.class);
        if (StrUtil.isEmpty(code)) {
            resourceDefinition.setResourceCode(resourceDefinition.getAppCode() + scannerProperties.getLinkSymbol() + StrUtil.toUnderlineCase(modular) + scannerProperties.getLinkSymbol() + StrUtil.toUnderlineCase(method.getName()));
        } else {
            resourceDefinition.setResourceCode(resourceDefinition.getAppCode() + scannerProperties.getLinkSymbol() + StrUtil.toUnderlineCase(modular) + scannerProperties.getLinkSymbol() + StrUtil.toUnderlineCase(code));
        }

        // 填充其他属性
        String name = invokeAnnotationMethod(apiResource, "name", String.class);
        String[] methodPath = invokeAnnotationMethod(apiResource, "path", String[].class);
        RequestMethod[] requestMethods = invokeAnnotationMethod(apiResource, "method", RequestMethod[].class);
        Boolean requiredLogin = invokeAnnotationMethod(apiResource, "requiredLogin", Boolean.class);
        Boolean requiredPermission = invokeAnnotationMethod(apiResource, "requiredPermission", Boolean.class);
        Boolean viewFlag = invokeAnnotationMethod(apiResource, "viewFlag", Boolean.class);

        resourceDefinition.setRequiredLoginFlag(requiredLogin);
        resourceDefinition.setRequiredPermissionFlag(requiredPermission);
        resourceDefinition.setResourceName(name);

        // 根据控制器和控制器方法的path组装最后的url
        String controllerMethodPath = createControllerPath(controllerClass, methodPath[0]);
        resourceDefinition.setUrl(createFinalUrl(controllerMethodPath));

        // 如果注解标识是视图类型，则判断该资源是视图类型（优先级最高）
        if (viewFlag) {
            resourceDefinition.setViewFlag(true);
        }
        // 如果资源url是以/view开头，则是视图类型
        else if (StrUtil.isNotBlank(controllerMethodPath)) {
            resourceDefinition.setViewFlag(controllerMethodPath.toLowerCase().startsWith(ScannerConstants.VIEW_CONTROLLER_PATH_START_WITH));
        }
        // 其他都是非视图类型
        else {
            resourceDefinition.setViewFlag(false);
        }

        StringBuilder methodNames = new StringBuilder();
        for (RequestMethod requestMethod : requestMethods) {
            methodNames.append(requestMethod.name()).append(",");
        }
        resourceDefinition.setHttpMethod(StrUtil.removeSuffix(methodNames.toString(), ","));

        // 填充ip地址
        String localMacAddress = IpAddrHolder.get();
        if (localMacAddress == null) {
            try {
                localMacAddress = NetUtil.getLocalhostStr();
                IpAddrHolder.set(localMacAddress);
            } catch (UtilException e) {
                log.error("获取当前机器ip地址错误！");
            }
        }

        resourceDefinition.setIpAddress(localMacAddress == null ? "" : localMacAddress);
        resourceDefinition.setCreateTime(new Date());

        // 填充项目编码
        resourceDefinition.setProjectCode(scannerProperties.getAppCode());

        // 填充方法的校验分组
        Set<String> methodValidateGroup = MethodReflectUtil.getMethodValidateGroup(method);
        if (methodValidateGroup != null) {
            resourceDefinition.setValidateGroups(methodValidateGroup);
        }

        // 填充方法返回结果字段的详细信息
        // @ApiResource注解上标识了responseClass属性，则用responseClass的值为准，否则按真实方法的返回值class
        Class<?> responseClass = invokeAnnotationMethod(apiResource, "responseClass", Class.class);
        if (!Void.class.equals(responseClass)) {
            resourceDefinition.setResponseFieldDescriptions(ClassReflectUtil.getClassFieldDescription(responseClass));
        } else {
            Class<?> methodReturnClass = MethodReflectUtil.getMethodReturnClass(method);
            resourceDefinition.setResponseFieldDescriptions(ClassReflectUtil.getClassFieldDescription(methodReturnClass));
        }

        // 填充方法的请求参数字段的详细信息
        Class<?> firstParamClass = MethodReflectUtil.getMethodFirstParamClass(method);
        resourceDefinition.setParamFieldDescriptions(ClassReflectUtil.getClassFieldDescription(firstParamClass));

        return resourceDefinition;
    }

    /**
     * 根据控制器类上的RequestMapping注解的映射路径，以及方法上的路径，拼出整个接口的路径
     *
     * @param clazz 控制器的类
     * @param path  控制器方法注解上的路径
     */
    private String createControllerPath(Class<?> clazz, String path) {
        String controllerPath;

        ApiResource controllerRequestMapping = clazz.getDeclaredAnnotation(ApiResource.class);
        if (controllerRequestMapping == null) {
            controllerPath = "";
        } else {
            String[] paths = controllerRequestMapping.path();
            if (paths.length > 0) {
                controllerPath = paths[0];
            } else {
                controllerPath = "";
            }
        }

        // 控制器上的path要以/开头
        if (!controllerPath.startsWith("/")) {
            controllerPath = "/" + controllerPath;
        }

        // 前缀多个左斜杠替换为一个
        return (controllerPath + path).replaceAll("/+", "/");
    }

    /**
     * 根据appCode和contextPath等，拼出整个接口的路径
     *
     * @param controllerMethodPath 控制器和控制器方法的path的组合
     */
    private String createFinalUrl(String controllerMethodPath) {

        // 拼接最终url的时候，依据如下规则拼接：/appCode/contextPath/xxx
        // 第一部分是appCode
        String appCode = "";
        if (scannerProperties.getUrlWithAppCode()) {
            appCode = "/" + StrUtil.removePrefix(scannerProperties.getAppCode(), "/");
        }

        // 第二部分是context-path
        String contextPath = "";
        if (scannerProperties.getUrlWithContextPath()) {
            contextPath = "/" + StrUtil.removePrefix(scannerProperties.getContextPath(), "/");
        }

        // 依据如下规则拼接：/appCode/contextPath/xxx
        String resultPath = appCode + contextPath + controllerMethodPath;

        // 前缀多个左斜杠替换为一个
        resultPath = resultPath.replaceAll("/+", "/");

        return resultPath;
    }

    /**
     * 调用注解上的某个方法，并获取结果
     */
    private <T> T invokeAnnotationMethod(Annotation apiResource, String methodName, Class<T> resultType) {
        try {
            Class<? extends Annotation> annotationType = apiResource.annotationType();
            Method method = annotationType.getMethod(methodName);
            return (T) method.invoke(apiResource);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("扫描api资源时出错!", e);
        }
        throw new RuntimeException("扫描api资源时出错!");
    }

}
