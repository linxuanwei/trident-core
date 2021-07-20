
package x.trident.core.system.api.pojo.menu.layui;

import lombok.Data;

import java.util.List;

/**
 * Layui用于beetl渲染首页菜单的实体
 *
 * @author 林选伟
 * @date 2020/12/27 18:39
 */
@Data
public class LayuiAppIndexMenusVO {

    /**
     * 应用的编码
     */
    private String appCode;

    /**
     * 应用的中文名称
     */
    private String appName;

    /**
     * 该应用对应的菜单树
     */
    private List<LayuiIndexMenuTreeNode> layuiIndexMenuTreeNodes;

}
