
package x.trident.core.db.api.factory;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import x.trident.core.util.HttpServletUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页参数快速获取
 *
 * @author 林选伟
 * @date 2020/10/17 17:33
 */
public class PageFactory {

    private PageFactory() {
    }

    /**
     * 每页大小（默认20）
     */
    private static final String PAGE_SIZE_PARAM_NAME = "pageSize";

    /**
     * 第几页（从1开始）
     */
    private static final String PAGE_NO_PARAM_NAME = "pageNo";

    /**
     * 默认分页，在使用时PageFactory.defaultPage会自动获取pageSize和pageNo参数
     *
     * @author 林选伟
     * @date 2020/3/30 16:42
     */
    public static <T> Page<T> defaultPage() {

        int pageSize = 20;
        int pageNo = 1;

        HttpServletRequest request = HttpServletUtil.getRequest();

        //每页条数
        String pageSizeString = request.getParameter(PAGE_SIZE_PARAM_NAME);
        if (ObjectUtil.isNotEmpty(pageSizeString)) {
            pageSize = Integer.parseInt(pageSizeString);
        }

        //第几页
        String pageNoString = request.getParameter(PAGE_NO_PARAM_NAME);
        if (ObjectUtil.isNotEmpty(pageNoString)) {
            pageNo = Integer.parseInt(pageNoString);
        }

        return new Page<>(pageNo, pageSize);
    }

}
