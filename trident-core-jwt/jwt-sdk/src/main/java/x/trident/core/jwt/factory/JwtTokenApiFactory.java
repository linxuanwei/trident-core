package x.trident.core.jwt.factory;

import cn.hutool.core.util.ObjectUtil;
import x.trident.core.jwt.JwtTokenOperator;
import x.trident.core.jwt.api.JwtApi;
import x.trident.core.jwt.api.exception.JwtException;
import x.trident.core.jwt.api.exception.enums.JwtExceptionEnum;
import x.trident.core.jwt.api.pojo.config.JwtConfig;


/**
 * jwt token操作工具的生产工厂
 *
 * @author 林选伟
 * @date 2021/1/21 18:15
 */
public class JwtTokenApiFactory {

    /**
     * 根据jwt秘钥和过期时间，获取jwt操作的工具
     */
    public static JwtApi createJwtApi(String jwtSecret, Integer expiredSeconds) {

        if (ObjectUtil.hasEmpty(jwtSecret, expiredSeconds)) {
            throw new JwtException(JwtExceptionEnum.JWT_PARAM_EMPTY);
        }

        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setJwtSecret(jwtSecret);
        jwtConfig.setExpiredSeconds(expiredSeconds.longValue());

        return new JwtTokenOperator(jwtConfig);
    }

}
