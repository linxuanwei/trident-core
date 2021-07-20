
package x.trident.core.system.modular.resource.pojo;

import x.trident.core.tree.factory.base.AbstractTreeNode;
import x.trident.core.scanner.api.annotation.ChineseDescription;
import lombok.Data;

import java.util.List;

/**
 * 资源树节点的描述
 *
 * @author 林选伟
 * @date 2020/3/26 14:29
 */
@Data
public class ResourceTreeNode implements AbstractTreeNode<ResourceTreeNode> {

    /**
     * 资源id
     */
    @ChineseDescription("资源id")
    private String code;

    /**
     * 父级资源id
     */
    @ChineseDescription("父级资源id")
    private String parentCode;

    /**
     * 资源名称
     */
    @ChineseDescription("资源名称")
    private String nodeName;

    /**
     * 是否是资源标识
     * <p>
     * true-是资源标识
     * false-虚拟节点，不是一个具体资源
     */
    @ChineseDescription("是否是资源标识")
    private Boolean resourceFlag;

    /**
     * 能否选择
     */
    @ChineseDescription("能否选择")
    private Boolean checked;

    /**
     * 子节点集合
     */
    @ChineseDescription("子节点集合")
    private List<ResourceTreeNode> children;

    @Override
    public String getNodeId() {
        return this.code;
    }

    @Override
    public String getNodeParentId() {
        return this.parentCode;
    }

    @Override
    public void setChildrenNodes(List<ResourceTreeNode> childrenNodes) {
        this.children = childrenNodes;
    }
}
