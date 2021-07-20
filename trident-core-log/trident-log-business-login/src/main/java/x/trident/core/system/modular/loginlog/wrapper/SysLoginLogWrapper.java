
package x.trident.core.system.modular.loginlog.wrapper;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.system.api.UserServiceApi;
import x.trident.core.system.api.pojo.user.SysUserDTO;
import x.trident.core.system.modular.loginlog.entity.SysLoginLog;
import x.trident.core.wrapper.api.BaseWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志结果进行包装
 *
 * @author 林选伟
 * @date 2021/2/28 10:59
 */
public class SysLoginLogWrapper implements BaseWrapper<SysLoginLog> {

    @Override
    public Map<String, Object> doWrap(SysLoginLog sysLoginLog) {

        if (sysLoginLog.getUserId() == null) {
            return new HashMap<>();
        }
        UserServiceApi userServiceApi = SpringUtil.getBean(UserServiceApi.class);
        SysUserDTO sysUserDTO = userServiceApi.getUserInfoByUserId(sysLoginLog.getUserId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("realName", sysUserDTO.getRealName());
        return map;
    }

}
