
package x.trident.core.system.api;

import x.trident.core.pojo.dict.SimpleDict;

import java.util.Set;

/**
 * 应用相关api
 *
 * @author 林选伟
 * @date 2020/11/24 21:37
 */
public interface AppServiceApi {

    /**
     * 通过app编码获取app的详情
     *
     * @param appCodes 应用的编码
     * @return 应用的信息
     */
    Set<SimpleDict> getAppsByAppCodes(Set<String> appCodes);

    /**
     * 通过app编码获取app的中文名
     *
     * @param appCode 应用的编码
     * @return 应用的中文名
     */
    String getAppNameByAppCode(String appCode);

    /**
     * 获取当前激活的应用编码
     *
     * @return 激活的应用编码
     */
    String getActiveAppCode();

}
