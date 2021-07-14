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

    /**
     * 判断是否完成资源扫描初始化
     *
     * @return true-完成  false-未完成
     */
    public static synchronized Boolean hasInitialized() {
        return initManagerFlag;
    }

    public static synchronized void setFlag() {
        initManagerFlag = true;
    }

}
