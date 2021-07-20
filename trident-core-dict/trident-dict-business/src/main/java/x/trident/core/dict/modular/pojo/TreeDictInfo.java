
package x.trident.core.dict.modular.pojo;

import x.trident.core.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 字典详细信息
 * <p>
 * 字典表的实现可以用内存，数据库或redis(具体实现可以根据业务需求自定义)
 *
 * @author 林选伟
 * @date 2020/10/30 11:05
 */
@Data
public class TreeDictInfo implements AbstractTreeNode<TreeDictInfo> {

    /**
     * 字典id
     */
    private Long dictId;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 上级字典id
     */
    private Long dictParentId;

    /**
     * tree子节点
     */
    private List<TreeDictInfo> children;

    @Override
    public String getNodeId() {
        if (this.dictId == null) {
            return null;
        } else {
            return this.dictId.toString();
        }
    }

    @Override
    public String getNodeParentId() {
        if (this.dictParentId == null) {
            return null;
        } else {
            return this.dictParentId.toString();
        }
    }

    @Override
    public void setChildrenNodes(List<TreeDictInfo> linkedList) {
        this.children = linkedList;
    }

}
