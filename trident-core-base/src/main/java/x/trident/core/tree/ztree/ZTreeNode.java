package x.trident.core.tree.ztree;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import x.trident.core.constants.TreeConstants;
import x.trident.core.tree.factory.base.AbstractTreeNode;

import java.util.List;

/**
 * jquery zTree 插件的节点封装
 *
 * @author 林选伟
 * @date 2021/1/6 21:47
 */
@ToString
@EqualsAndHashCode
public class ZTreeNode implements AbstractTreeNode<ZTreeNode> {

    /**
     * 节点id
     */
    @Getter
    @Setter
    private Long id;

    /**
     * 父节点id
     */
    private Long pId;

    /**
     * 节点名称
     */
    @Getter
    @Setter
    private String name;

    /**
     * 是否打开节点
     */
    @Getter
    @Setter
    private Boolean open;

    /**
     * 是否被选中
     */
    @Getter
    @Setter
    private Boolean checked;

    /**
     * 节点图标  single or group
     */
    @Getter
    @Setter
    private String iconSkin;

    /**
     * 子节点集合
     */
    @Getter
    @Setter
    private List<ZTreeNode> children;

    /**
     * 创建ztree的父级节点
     *
     * @author 林选伟
     * @date 2021/1/6 21:47
     */
    public static ZTreeNode createParent() {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setChecked(true);
        zTreeNode.setId(TreeConstants.DEFAULT_PARENT_ID);
        zTreeNode.setName("顶级");
        zTreeNode.setOpen(true);
        zTreeNode.setpId(TreeConstants.VIRTUAL_ROOT_PARENT_ID);
        return zTreeNode;
    }


    @Override
    public String getNodeId() {
        return id.toString();
    }

    @Override
    public String getNodeParentId() {
        return pId.toString();
    }

    @Override
    public void setChildrenNodes(List<ZTreeNode> childrenNodes) {
        this.children = childrenNodes;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getpId() {
        return pId;
    }
}
