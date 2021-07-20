package x.trident.core.system.integration.config;

import x.trident.core.system.integration.ErrorStaticJsonView;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 错误界面自动配置，一般用于404响应
 *
 * @author 林选伟
 * @date 2021/5/17 11:16
 */
@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class RestErrorViewAutoConfiguration {

    /**
     * 默认错误页面，返回json
     *
     * @author 林选伟
     * @date 2020/12/16 15:47
     */
    @Bean("error")
    public ErrorStaticJsonView error() {
        return new ErrorStaticJsonView();
    }

}
