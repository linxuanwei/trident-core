
package x.trident.core.auth.permission;

import cn.hutool.core.util.StrUtil;
import x.trident.core.auth.api.PermissionServiceApi;
import x.trident.core.auth.api.SessionManagerApi;
import x.trident.core.auth.api.exception.AuthException;
import x.trident.core.auth.api.exception.enums.AuthExceptionEnum;
import x.trident.core.auth.api.pojo.login.LoginUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

import static x.trident.core.auth.api.exception.enums.AuthExceptionEnum.AUTH_EXPIRED_ERROR;
import static x.trident.core.auth.api.exception.enums.AuthExceptionEnum.PERMISSION_RES_VALIDATE_ERROR;

/**
 * 权限相关的service
 *
 * @author 林选伟
 * @date 2020/10/22 15:49
 */
@Service
public class PermissionServiceImpl implements PermissionServiceApi {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Override
    public void checkPermission(String token, String requestUrl) {

        // 1. 校验token是否传参
        if (StrUtil.isEmpty(token)) {
            throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
        }

        // 2. 获取token对应的用户信息
        LoginUser session = sessionManagerApi.getSession(token);
        if (session == null) {
            throw new AuthException(AUTH_EXPIRED_ERROR);
        }

        // 3. 验证用户有没有当前url的权限
        Set<String> resourceUrls = session.getResourceUrls();
        if (resourceUrls == null || resourceUrls.size() == 0) {
            throw new AuthException(PERMISSION_RES_VALIDATE_ERROR);
        } else {
            if (!resourceUrls.contains(requestUrl)) {
                throw new AuthException(PERMISSION_RES_VALIDATE_ERROR);
            }
        }
    }

}
