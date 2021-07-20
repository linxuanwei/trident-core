
package x.trident.core.log.api.context;


import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 临时缓存服务器信息
 *
 * @author 林选伟
 * @date 2020/10/27 17:53
 */
public class ServerInfoContext {

    /**
     * 服务器IP
     */
    private static String serverIp;

    /**
     * 禁止new创建
     */
    private ServerInfoContext() {
    }

    /**
     * 获取server的ip
     */
    public static String getServerIp() {
        if (StrUtil.isEmpty(serverIp)) {
            serverIp = NetUtil.getLocalhostStr();
        }
        return serverIp;
    }

}
