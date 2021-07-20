
package x.trident.core.file.api.pojo.props;

import lombok.Data;

/**
 * 本地文件存储配置
 *
 * @author 林选伟
 * @date 2020/6/7 22:30
 */
@Data
public class LocalFileProperties {

    /**
     * 本地文件存储位置（linux）
     */
    private String localFileSavePathLinux = "/tmp/tempFilePath";

    /**
     * 本地文件存储位置（windows）
     */
    private String localFileSavePathWin = "D:\\tempFilePath";

}
