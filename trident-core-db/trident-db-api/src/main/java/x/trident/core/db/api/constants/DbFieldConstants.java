package x.trident.core.db.api.constants;

/**
 * 数据库常用字段的枚举
 *
 * @author 林选伟
 * @date 2020/10/16 17:07
 */
public class DbFieldConstants {
    private DbFieldConstants() {
    }

    /**
     * 主键id的字段名
     */
    public static final String ID = "id";

    /**
     * 创建用户的字段名
     */
    public static final String CREATE_USER = "createUser";

    /**
     * 创建时间的字段名
     */
    public static final String CREATE_TIME = "createTime";

    /**
     * 更新用户的字段名
     */
    public static final String UPDATE_USER = "updateUser";

    /**
     * 更新时间的字段名
     */
    public static final String UPDATE_TIME = "updateTime";

    /**
     * 删除标记的字段名
     */
    public static final String DEL_FLAG = "delFlag";

    /**
     * 数据状态的字段
     * 状态：1-启用，2-禁用
     */
    public static final String STATUS_FLAG = "statusFlag";

}
