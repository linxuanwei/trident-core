
package x.trident.core.system.api.pojo.organization;

import x.trident.core.tree.factory.base.AbstractTreeNode;
import x.trident.core.tree.xmtree.base.AbstractXmSelectNode;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import lombok.Data;

import java.util.List;

/**
 * 组织机构树节点
 *
 * @author chenjinlong
 * @date 2020/12/27 18:36
 */
@Data
public class OrganizationTreeNode implements AbstractTreeNode<OrganizationTreeNode>, AbstractXmSelectNode {

    /**
     * 父id，一级节点父id是0
     */
    @ChineseDescription("父id，一级节点父id是0")
    private Long parentId;

    /**
     * 节点名称
     */
    @ChineseDescription("节点名称")
    private String title;

    /**
     * 节点值
     */
    @ChineseDescription("节点值")
    private Long id;

    /**
     * 是否展开状态 不展开-false 展开-true
     */
    @ChineseDescription("是否展开状态 不展开-false 展开-true")
    private boolean spread = true;

    /**
     * 是否选中
     */
    @ChineseDescription("是否选中")
    private boolean selected = false;

    /**
     * 子节点的集合
     */
    @ChineseDescription("子节点的集合")
    private List<OrganizationTreeNode> children;

    @Override
    public String getNodeId() {
        return this.id.toString();
    }

    @Override
    public String getNodeParentId() {
        return this.parentId.toString();
    }

    @Override
    public void setChildrenNodes(List<OrganizationTreeNode> childrenNodes) {
        this.children = childrenNodes;
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public String getValue() {
        return String.valueOf(id);
    }

    @Override
    public Boolean getSelected() {
        return this.selected;
    }

    @Override
    public Boolean getDisabled() {
        return false;
    }

    @Override
    public List getChildren() {
        return this.children;
    }

}
