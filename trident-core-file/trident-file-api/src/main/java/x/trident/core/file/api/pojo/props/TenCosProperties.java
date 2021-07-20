
package x.trident.core.file.api.pojo.props;

import lombok.Data;

/**
 * 腾讯云cos文件存储配置
 *
 * @author 林选伟
 * @date 2020/10/26 11:49
 */
@Data
public class TenCosProperties {

    /**
     * secretId
     */
    private String secretId;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 地域id（默认北京）
     */
    private String regionId = "ap-beijing";

}
