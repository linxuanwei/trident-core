
package x.trident.core.log.api.expander;

import x.trident.core.config.api.context.ConfigContext;
import x.trident.core.log.api.constants.LogFileConstants;

/**
 * 日志记录相关的配置
 *
 * @author 林选伟
 * @date 2020/10/28 16:11
 */
public class LogConfigExpander {
    private LogConfigExpander() {
    }

    /**
     * 获取日志记录的文件存储的位置（windows服务器）
     * <p>
     * 末尾不带斜杠
     */
    public static String getLogFileSavePathWindows() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_LOG_FILE_SAVE_PATH_WINDOWS", String.class, LogFileConstants.DEFAULT_FILE_SAVE_PATH_WINDOWS);
    }

    /**
     * 获取日志记录的文件存储的位置（linux和mac服务器）
     * <p>
     * 末尾不带斜杠
     */
    public static String getLogFileSavePathLinux() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_LOG_FILE_SAVE_PATH_LINUX", String.class, LogFileConstants.DEFAULT_FILE_SAVE_PATH_LINUX);
    }

}
