
package x.trident.core.auth.api.password;

/**
 * 密码存储时，将密码进行加密的api
 *
 * @author 林选伟
 * @date 2020/12/21 16:50
 */
public interface PasswordStoredEncryptApi {

    /**
     * 加密密码
     *
     * @param originPassword 密码明文，待加密的密码
     * @return 加密后的密码密文
     */
    String encrypt(String originPassword);

    /**
     * 校验密码加密前和加密后是否一致，多用于判断用户输入密码是否正确
     *
     * @param encryptBefore 密码明文
     * @param encryptAfter  密码
     * @return true-密码正确，false-密码错误
     */

    Boolean checkPassword(String encryptBefore, String encryptAfter);

}
