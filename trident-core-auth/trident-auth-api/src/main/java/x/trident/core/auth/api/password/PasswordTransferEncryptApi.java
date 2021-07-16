
package x.trident.core.auth.api.password;

/**
 * 密码传输时，将密码进行加密和解密的api
 *
 * @author 林选伟
 * @date 2020/12/21 16:50
 */
public interface PasswordTransferEncryptApi {

    /**
     * 加密密码
     *
     * @param originPassword 密码明文，待加密的密码
     * @return 加密后的密码密文
     */
    String encrypt(String originPassword);

    /**
     * 解密密码信息
     *
     * @param encryptedPassword 加密的密码
     * @return 解密后的密码明文
     */
    String decrypt(String encryptedPassword);

}
