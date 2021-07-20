
package x.trident.core.system.api.pojo.user;

import cn.hutool.core.collection.IterUtil;
import x.trident.core.enums.TreeNodeEnum;
import x.trident.core.tree.factory.node.DefaultTreeNode;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户选择树节点封装
 * <p>
 * 默认的根节点id是-1，名称是根节点
 *
 * @author 林选伟
 * @date 2021/1/15 13:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSelectTreeNode extends DefaultTreeNode {

    /**
     * 节点类型：org: 机构  user: 用户
     */
    @ChineseDescription("节点类型：org: 机构  user: 用户")
    private String nodeType;

    /**
     * 节点值
     */
    @ChineseDescription("节点值")
    private String value;

    /**
     * 是否被选中
     */
    @ChineseDescription("是否被选中")
    private Boolean selected = false;

    /**
     * 是否禁用
     */
    @ChineseDescription("是否禁用")
    private Boolean disabled = false;

    public Boolean getDisabled() {
        if (this.disabled) {
            return true;
        }
        if (TreeNodeEnum.ORG.getCode().equals(nodeType) && IterUtil.isEmpty(this.getChildren())) {
            return true;
        }
        return this.disabled;
    }

}
