
package x.trident.core.system.api.pojo.organization;

import x.trident.core.auth.api.enums.DataScopeTypeEnum;
import lombok.Data;

import java.util.Set;

/**
 * 查询用户的数据范围时候的返回结果封装
 *
 * @author 林选伟
 * @date 2020/11/6 11:30
 */
@Data
public class DataScopeDTO {

    /**
     * 数据范围类型的响应结果
     */
    private Set<DataScopeTypeEnum> dataScopeTypeEnums;

    /**
     * 用户id数据范围集合
     */
    private Set<Long> userIds;

    /**
     * 组织架构id数据范围集合
     */
    private Set<Long> organizationIds;

}
