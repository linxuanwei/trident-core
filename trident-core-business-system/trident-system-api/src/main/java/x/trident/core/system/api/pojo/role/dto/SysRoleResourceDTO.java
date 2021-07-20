
package x.trident.core.system.api.pojo.role.dto;

import x.trident.core.db.api.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色资源关联
 *
 * @author 林选伟
 * @date 2020/11/5 下午4:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleResourceDTO extends BaseEntity {

    /**
     * 主键
     */
    private Long roleResourceId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源编码
     */
    private String resourceCode;

}
