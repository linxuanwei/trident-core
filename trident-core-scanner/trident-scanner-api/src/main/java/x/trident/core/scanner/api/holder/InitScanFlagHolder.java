package x.trident.core.scanner.api.holder;

/**
 * 初始化标记，防止初始化多次
 *
 * @author 林选伟
 * @date 2019-09-27-17:23
 */
public class InitScanFlagHolder {
    private InitScanFlagHolder() {
    }

    private static Boolean initManagerFlag = false;

    public static synchronized Boolean getFlag() {
        return initManagerFlag;
    }

    public static synchronized void setFlag() {
        initManagerFlag = true;
    }

}
