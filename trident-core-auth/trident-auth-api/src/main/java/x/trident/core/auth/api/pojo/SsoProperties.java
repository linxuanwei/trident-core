package x.trident.core.auth.api.pojo;

import lombok.Data;

/**
 * SSO的配置
 *
 * @author 林选伟
 * @date 2021/5/25 22:28
 */
@Data
public class SsoProperties {

    /**
     * 是否开启，true-开启单点，false-关闭单点
     */
    private Boolean openFlag;

}
