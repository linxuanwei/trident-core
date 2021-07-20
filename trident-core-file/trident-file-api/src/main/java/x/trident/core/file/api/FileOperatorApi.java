
package x.trident.core.file.api;

import x.trident.core.file.api.enums.BucketAuthEnum;

import java.io.InputStream;

/**
 * 文件操纵者（内网操作）
 * <p>
 * 如果存在未包含的操作，可以调用getClient()自行获取client进行操作
 *
 * @author 林选伟
 * @date 2020/10/26 10:33
 */
public interface FileOperatorApi {

    /**
     * 初始化操作的客户端
     *
     * @author 林选伟
     * @date 2020/10/26 10:33
     */
    void initClient();

    /**
     * 销毁操作的客户端
     *
     * @author 林选伟
     * @date 2020/10/26 10:33
     */
    void destroyClient();

    /**
     * 获取操作的客户端
     * <p>
     * 例如，获取阿里云的客户端com.aliyun.oss.OSS
     *
     * @author 林选伟
     * @date 2020/10/26 10:35
     */
    Object getClient();

    /**
     * 查询存储桶是否存在
     * <p>
     * 例如：传入参数exampleBucket-1250000000，返回true代表存在此桶BucketAuthEnum.java
     *
     * @param bucketName 存储桶名称
     * @return boolean true-存在，false-不存在
     * @author 林选伟
     * @date 2020/10/26 10:36
     */
    boolean doesBucketExist(String bucketName);

    /**
     * 设置预定义策略
     * <p>
     * 预定义策略如公有读、公有读写、私有读
     *
     * @param bucketName     存储桶名称
     * @param bucketAuthEnum 存储桶的权限
     * @author 林选伟
     * @date 2020/10/26 10:37
     */
    void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum);

    /**
     * 判断是否存在文件
     *
     * @param bucketName 桶名称
     * @param key        唯一标示id，例如a.txt, doc/a.txt
     * @return true-存在文件，false-不存在文件
     * @author 林选伟
     * @date 2020/10/26 10:38
     */
    boolean isExistingFile(String bucketName, String key);

    /**
     * 存储文件
     *
     * @param bucketName 桶名称
     * @param key        唯一标示id，例如a.txt, doc/a.txt
     * @param bytes      文件字节数组
     * @author 林选伟
     * @date 2020/10/26 10:39
     */
    void storageFile(String bucketName, String key, byte[] bytes);

    /**
     * 存储文件（存放到指定的bucket里边）
     *
     * @param bucketName  桶名称
     * @param key         唯一标示id，例如a.txt, doc/a.txt
     * @param inputStream 文件流
     * @author 林选伟
     * @date 2020/10/26 10:39
     */
    void storageFile(String bucketName, String key, InputStream inputStream);

    /**
     * 获取某个bucket下的文件字节
     *
     * @param bucketName 桶名称
     * @param key        唯一标示id，例如a.txt, doc/a.txt
     * @return byte[] 字节数组为文件的字节数组
     * @author 林选伟
     * @date 2020/10/26 10:39
     */
    byte[] getFileBytes(String bucketName, String key);

    /**
     * 文件访问权限管理
     *
     * @param bucketName     桶名称
     * @param key            唯一标示id，例如a.txt, doc/a.txt
     * @param bucketAuthEnum 文件权限
     * @author 林选伟
     * @date 2020/10/26 10:40
     */
    void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum);

    /**
     * 拷贝文件
     *
     * @param originBucketName 源文件桶
     * @param originFileKey    源文件名称
     * @param newBucketName    新文件桶
     * @param newFileKey       新文件名称
     * @author 林选伟
     * @date 2020/10/26 10:40
     */
    void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey);

    /**
     * 获取文件的下载地址（带鉴权的），生成外网地址
     *
     * @param bucketName    文件桶
     * @param key           文件唯一标识
     * @param timeoutMillis url失效时间，单位毫秒
     * @return 外部系统可以直接访问的url
     * @author 林选伟
     * @date 2020/10/26 10:40
     */
    String getFileAuthUrl(String bucketName, String key, Long timeoutMillis);

    /**
     * 删除文件
     *
     * @param bucketName 文件桶
     * @param key        文件唯一标识
     * @author 林选伟
     * @date 2020/10/26 10:42
     */
    void deleteFile(String bucketName, String key);

}
