
package x.trident.core.file.api.context;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.file.api.FileOperatorApi;

/**
 * 文件操作api的上下文
 *
 * @author 林选伟
 * @date 2020/10/26 10:30
 */
public class FileContext {

    /**
     * 获取文件操作接口
     *
     * @author 林选伟
     * @date 2020/10/17 14:30
     */
    public static FileOperatorApi me() {
        return SpringUtil.getBean(FileOperatorApi.class);
    }

}
