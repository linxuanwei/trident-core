
package x.trident.core.auth.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import x.trident.core.auth.session.cache.logintoken.MemoryLoginTokenCache;
import x.trident.core.auth.session.cache.loginuser.MemoryLoginUserCache;
import x.trident.core.auth.session.timer.ClearInvalidLoginUserCacheTimer;
import x.trident.core.auth.api.SessionManagerApi;
import x.trident.core.auth.api.cookie.SessionCookieCreator;
import x.trident.core.auth.api.expander.AuthConfigExpander;
import x.trident.core.auth.api.password.PasswordStoredEncryptApi;
import x.trident.core.auth.api.password.PasswordTransferEncryptApi;
import x.trident.core.auth.api.pojo.login.LoginUser;
import x.trident.core.auth.password.BcryptPasswordStoredEncrypt;
import x.trident.core.auth.password.RsaPasswordTransferEncrypt;
import x.trident.core.auth.session.DefaultSessionManager;
import x.trident.core.auth.session.cookie.DefaultSessionCookieCreator;
import x.trident.core.cache.api.CacheOperatorApi;
import x.trident.core.cache.api.constants.CacheConstants;
import x.trident.core.jwt.JwtTokenOperator;
import x.trident.core.jwt.api.JwtApi;
import x.trident.core.jwt.api.pojo.config.JwtConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;


/**
 * 认证和鉴权模块的自动配置
 *
 * @author 林选伟
 * @date 2020/11/30 22:16
 */
@Configuration
public class TridentAuthAutoConfiguration {

    /**
     * jwt操作工具类的配置
     *
     * @author 林选伟
     * @date 2020/12/1 14:40
     */
    @Bean
    @ConditionalOnMissingBean(JwtApi.class)
    public JwtApi jwtApi() {

        JwtConfig jwtConfig = new JwtConfig();

        // 从系统配置表中读取配置
        jwtConfig.setJwtSecret(AuthConfigExpander.getAuthJwtSecret());
        jwtConfig.setExpiredSeconds(AuthConfigExpander.getAuthJwtTimeoutSeconds());

        return new JwtTokenOperator(jwtConfig);
    }

    /**
     * Bcrypt方式的密码加密
     *
     * @author 林选伟
     * @date 2020/12/21 17:45
     */
    @Bean
    @ConditionalOnMissingBean(PasswordStoredEncryptApi.class)
    public PasswordStoredEncryptApi passwordStoredEncryptApi() {
        return new BcryptPasswordStoredEncrypt();
    }

    /**
     * RSA方式密码加密传输
     *
     * @author 林选伟
     * @date 2020/12/21 17:45
     */
    @Bean
    @ConditionalOnMissingBean(PasswordTransferEncryptApi.class)
    public PasswordTransferEncryptApi passwordTransferEncryptApi() {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCytSVn3ff7eBJckAFYwgJjqE9Zq2uAL4g+hkfQqGALdT8NJKALFxNzeSD/xTBLAJrtALWbN1dvyktoVNPAuuzCZO1BxYZNaAU3IKFaj73OSPzca5SGY0ibMw0KvEPkC3sZQeqBqx+VqYAqan90BeG/r9p36Eb0wrshj5XmsFeo6QIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALK1JWfd9/t4ElyQAVjCAmOoT1mra4AviD6GR9CoYAt1Pw0koAsXE3N5IP/FMEsAmu0AtZs3V2/KS2hU08C67MJk7UHFhk1oBTcgoVqPvc5I/NxrlIZjSJszDQq8Q+QLexlB6oGrH5WpgCpqf3QF4b+v2nfoRvTCuyGPleawV6jpAgMBAAECgYBS9fUfetQcUWl0vwVhBu/FA+WSYxnMsEQ3gm7kVsX/i7Zxi4cgnt3QxXKkSg5ZQzaov6OPIuncY7UOAhMrbZtq/Hh8atdTVC/Ng/X9Bomodplq/+KTe/vIfWW5rlQAnMNFVaidxhCVRlRHNusapmj2vYwsiyI9kXUJNHryxtFC4QJBANtQuh3dtd79t3MVaC3TUD/EsGBe9TB3Eykbgv0muannC2Oq8Ci4vIp0NSA+FNCoB8ctgfKJUdBS8RLVnYyu3RcCQQDQmY+AuAXEpO9SgcYeRnQSOU2OSuC1wLt1MRVpPTdvE3bfRnkVxMrK0n3YilQWkQzfkERSG4kRFLIw605xPWn/AkEAiw3vQ9p8Yyu5MiXDjTKrchMyxZfPnHATXQANmJcCJ0DQDtymMxuWp66wtIXIStgPPnGTMAVzM0Qzh/6bS0Tf9wJAWj+1yFjVlghNyoJ+9qZAnYnRNhjLM5dZAxDjVI65pwLi0SKqTHLB0hJThBYE32aODUNba7KiEJPFrEiBvZh2fQJARbboHuHT0PqD1UTJGgodHlaw48kreBU+twext/9/jIqvwmFF88BmQgssHGW/tn4E6Qy3+rCCNWreEReY0gATYw==";
        return new RsaPasswordTransferEncrypt(publicKey, privateKey);
    }

    /**
     * session cookie的创建
     *
     * @author 林选伟
     * @date 2020/12/27 15:48
     */
    @Bean
    @ConditionalOnMissingBean(SessionCookieCreator.class)
    public SessionCookieCreator sessionCookieCreator() {
        return new DefaultSessionCookieCreator();
    }

    /**
     * 登录用户的缓存，默认使用内存方式
     * <p>
     * 如需redis，可在项目创建一个名为 loginUserCache 的bean替代即可
     *
     * @author 林选伟
     * @date 2021/1/31 21:04
     */
    @Bean
    @ConditionalOnMissingBean(name = "loginUserCache")
    public MemoryLoginUserCache loginUserCache() {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();
        TimedCache<String, LoginUser> loginUsers = CacheUtil.newTimedCache(1000L * sessionExpiredSeconds);
        return new MemoryLoginUserCache(loginUsers);
    }

    /**
     * 登录用户token的缓存，默认使用内存方式
     * <p>
     * 如需redis，可在项目创建一个名为 allPlaceLoginTokenCache 的bean替代即可
     *
     * @author 林选伟
     * @date 2021/1/31 21:04
     */
    @Bean
    @ConditionalOnMissingBean(name = "allPlaceLoginTokenCache")
    public MemoryLoginTokenCache allPlaceLoginTokenCache() {
        TimedCache<String, Set<String>> loginTokens = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        return new MemoryLoginTokenCache(loginTokens);
    }

    /**
     * 默认的session缓存为内存缓存，方便启动
     * <p>
     * 如需替换请在项目中增加一个SessionManagerApi Bean即可
     *
     * @author 林选伟
     * @date 2020/11/30 22:17
     */
    @Bean
    @ConditionalOnMissingBean(SessionManagerApi.class)
    public SessionManagerApi sessionManagerApi(CacheOperatorApi<LoginUser> loginUserCache, CacheOperatorApi<Set<String>> allPlaceLoginTokenCache) {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();
        return new DefaultSessionManager(loginUserCache, allPlaceLoginTokenCache, sessionExpiredSeconds, sessionCookieCreator());
    }

    /**
     * 清空无用登录用户缓存的定时任务
     *
     * @author 林选伟
     * @date 2021/3/30 11:32
     */
    @Bean
    @ConditionalOnMissingBean(ClearInvalidLoginUserCacheTimer.class)
    public ClearInvalidLoginUserCacheTimer clearInvalidLoginUserCacheTimer() {
        return new ClearInvalidLoginUserCacheTimer(loginUserCache(), allPlaceLoginTokenCache());
    }

}
