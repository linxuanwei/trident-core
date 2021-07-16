
package x.trident.core.datasource.sdk.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import x.trident.core.datasource.api.annotation.DataSource;
import x.trident.core.datasource.api.constants.DatasourceContainerConstants;
import x.trident.core.datasource.api.context.CurrentDataSourceContext;

import java.lang.reflect.Method;


/**
 * 多数据源切换的AOP，切的是 DataSource 注解
 *
 * @author 林选伟
 * @date 2020/10/31 22:55
 */
@Aspect
@Slf4j
public class MultiSourceExchangeAop implements Ordered {

    @Pointcut(value = "@annotation(x.trident.core.datasource.api.annotation.DataSource)")
    private void cutService() {
        // unused
    }

    @Around("cutService()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        // 获取被aop拦截的方法
        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        // 获取方法上的DataSource注解
        DataSource datasource = currentMethod.getAnnotation(DataSource.class);

        // 如果有DataSource注解，则设置DataSourceContextHolder为注解上的名称
        if (datasource != null) {
            CurrentDataSourceContext.setDataSourceName(datasource.name());
            log.debug("设置数据源为：" + datasource.name());
        } else {
            CurrentDataSourceContext.setDataSourceName(DatasourceContainerConstants.MASTER_DATASOURCE_NAME);
            log.debug("设置数据源为：dataSourceCurrent");
        }

        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息！");
            CurrentDataSourceContext.clearDataSourceName();
        }
    }

    /**
     * aop的顺序要早于spring的事务
     *
     * @author 林选伟
     * @date 2020/10/31 22:55
     */
    @Override
    public int getOrder() {
        return DatasourceContainerConstants.MULTI_DATA_SOURCE_EXCHANGE_AOP;
    }

}
