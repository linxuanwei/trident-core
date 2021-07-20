
package x.trident.core.log.manage.wrapper;

import cn.hutool.extra.spring.SpringUtil;
import x.trident.core.log.api.pojo.record.LogRecordDTO;
import x.trident.core.system.api.UserServiceApi;
import x.trident.core.system.api.pojo.user.SysUserDTO;
import x.trident.core.wrapper.api.BaseWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志结果进行包装
 *
 * @author 林选伟
 * @date 2021/2/28 10:59
 */
public class LogInfoWrapper implements BaseWrapper<LogRecordDTO> {

    @Override
    public Map<String, Object> doWrap(LogRecordDTO beWrappedModel) {

        if (beWrappedModel.getUserId() == null) {
            return new HashMap<>();
        }
        UserServiceApi userServiceApi = SpringUtil.getBean(UserServiceApi.class);
        SysUserDTO sysUserDTO = userServiceApi.getUserInfoByUserId(beWrappedModel.getUserId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("realName", sysUserDTO.getRealName());
        return map;
    }

}
