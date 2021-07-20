
package x.trident.core.log.starter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import x.trident.core.log.api.LogManagerApi;
import x.trident.core.log.api.LogRecordApi;
import x.trident.core.log.api.enums.LogSaveTypeEnum;
import x.trident.core.log.api.expander.LogConfigExpander;
import x.trident.core.log.api.pojo.log.SysLogProperties;
import x.trident.core.log.api.threadpool.LogManagerThreadPool;
import x.trident.core.log.db.DbLogManagerServiceImpl;
import x.trident.core.log.db.DbLogRecordServiceImpl;
import x.trident.core.log.db.service.SysLogService;
import x.trident.core.log.db.service.impl.SysLogServiceImpl;
import x.trident.core.log.file.FileLogManagerServiceImpl;
import x.trident.core.log.file.FileLogRecordServiceImpl;
import x.trident.core.log.requestapi.RequestApiLogRecordAop;

/**
 * 系统日志的自动配置
 *
 * @author 林选伟
 * @date 2020/12/1 17:12
 */
@Configuration
public class TridentLogAutoConfiguration {

    /**
     * 日志配置的前缀
     */
    public static final String SYS_LOG_PREFIX = "sys-log";


    /**
     * 系统日志service
     *
     * @return sysLogService
     */
    @Bean
    @ConditionalOnMissingBean(SysLogService.class)
    @ConditionalOnProperty(prefix = SYS_LOG_PREFIX, name = "type", havingValue = "db")
    public SysLogService sysLogService() {
        return new SysLogServiceImpl();
    }

    /**
     * 系统日志的配置
     */
    @Bean
    @ConfigurationProperties(prefix = SYS_LOG_PREFIX)
    public SysLogProperties sysLogProperties() {
        return new SysLogProperties();
    }

    /**
     * 每个请求接口记录日志的AOP
     * 根据配置文件初始化日志记录器
     * 日志存储类型：db-数据库，file-文件，默认存储在数据库中
     *
     * @param sysLogProperties 系统日志配置文件
     * @param sysLogService    系统日志service
     */
    @Bean
    public RequestApiLogRecordAop requestApiLogRecordAop(SysLogProperties sysLogProperties, SysLogServiceImpl sysLogService) {

        // 如果类型是文件
        if (StrUtil.isNotBlank(sysLogProperties.getType())
                && LogSaveTypeEnum.FILE.getCode().equals(sysLogProperties.getType())) {

            // 修改为从sys_config中获取日志存储位置
            String fileSavePath = "";
            if (SystemUtil.getOsInfo().isWindows()) {
                fileSavePath = LogConfigExpander.getLogFileSavePathWindows();
            } else {
                fileSavePath = LogConfigExpander.getLogFileSavePathLinux();
            }

            return new RequestApiLogRecordAop(new FileLogRecordServiceImpl(fileSavePath, new LogManagerThreadPool()));
        }

        // 其他情况用数据库存储日志
        return new RequestApiLogRecordAop(new DbLogRecordServiceImpl(new LogManagerThreadPool(), sysLogService));
    }

    /**
     * 日志管理器
     *
     * @param sysLogProperties 系统日志配置文件
     */
    @Bean
    public LogManagerApi logManagerApi(SysLogProperties sysLogProperties) {

        // 如果类型是文件
        if (StrUtil.isNotBlank(sysLogProperties.getType())
                && LogSaveTypeEnum.FILE.getCode().equals(sysLogProperties.getType())) {

            // 修改为从sys_config中获取日志存储位置
            String fileSavePath = "";
            if (SystemUtil.getOsInfo().isWindows()) {
                fileSavePath = LogConfigExpander.getLogFileSavePathWindows();
            } else {
                fileSavePath = LogConfigExpander.getLogFileSavePathLinux();
            }

            return new FileLogManagerServiceImpl(fileSavePath);
        }

        // 其他情况用数据库存储日志
        return new DbLogManagerServiceImpl();
    }

    /**
     * 日志记录的api
     */
    @Bean
    public LogRecordApi logRecordApi(SysLogServiceImpl sysLogService) {
        return new DbLogRecordServiceImpl(new LogManagerThreadPool(), sysLogService);
    }

}
