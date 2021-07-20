package x.trident.core.tree.xmtree.base;

import java.util.List;

/**
 * 封装用于xm-select组件的节点的方法
 *
 * @author 林选伟
 * @date 2021/1/31 18:30
 */
public interface AbstractXmSelectNode {

    /**
     * 显示的名称
     *
     * @author 林选伟
     * @date 2021/1/31 18:30
     */
    String getName();

    /**
     * 选中值, 当前多选唯一
     *
     * @author 林选伟
     * @date 2021/1/31 18:30
     */
    String getValue();

    /**
     * 是否选中
     *
     * @author 林选伟
     * @date 2021/1/31 18:31
     */
    Boolean getSelected();

    /**
     * 是否禁用
     *
     * @author 林选伟
     * @date 2021/1/31 18:31
     */
    Boolean getDisabled();

    /**
     * 获取分组的列表
     *
     * @author 林选伟
     * @date 2021/1/31 18:33
     */
    List<?> getChildren();

}
