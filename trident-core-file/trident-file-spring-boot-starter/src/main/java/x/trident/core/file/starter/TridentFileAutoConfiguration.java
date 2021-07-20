
package x.trident.core.file.starter;

import x.trident.core.file.api.FileOperatorApi;
import x.trident.core.file.api.expander.FileConfigExpander;
import x.trident.core.file.api.pojo.props.LocalFileProperties;
import x.trident.core.file.local.LocalFileOperator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件的自动配置
 *
 * @author 林选伟
 * @date 2020/12/1 14:34
 */
@Configuration
public class TridentFileAutoConfiguration {

    /**
     * 本地文件操作
     *
     * @author 林选伟
     * @date 2020/12/1 14:40
     */
    @Bean
    @ConditionalOnMissingBean(FileOperatorApi.class)
    public FileOperatorApi fileOperatorApi() {

        LocalFileProperties localFileProperties = new LocalFileProperties();

        // 从系统配置表中读取配置
        localFileProperties.setLocalFileSavePathLinux(FileConfigExpander.getLocalFileSavePathLinux());
        localFileProperties.setLocalFileSavePathWin(FileConfigExpander.getLocalFileSavePathWindows());

        return new LocalFileOperator(localFileProperties);
    }

}
