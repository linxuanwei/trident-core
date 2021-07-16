package x.trident.core.db.api.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import x.trident.core.db.api.pojo.page.PageResult;

import java.util.List;

/**
 * 分页的返回结果创建工厂
 * <p>
 * 一般由mybatis-plus的Page对象转为PageResult
 *
 * @author 林选伟
 * @date 2020/10/15 16:57
 */
public class PageResultFactory {
    private PageResultFactory() {
    }

    /**
     * 将mybatis-plus的page转成自定义的PageResult，扩展了totalPage总页数
     */
    public static <T> PageResult<T> createPageResult(Page<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setRows(page.getRecords());
        pageResult.setTotalRows(Convert.toInt(page.getTotal()));
        pageResult.setPageNo(Convert.toInt(page.getCurrent()));
        pageResult.setPageSize(Convert.toInt(page.getSize()));
        pageResult.setTotalPage(
                PageUtil.totalPage(pageResult.getTotalRows(), pageResult.getPageSize()));
        return pageResult;
    }

    /**
     * 将mybatis-plus的page转成自定义的PageResult，扩展了totalPage总页数
     */
    public static <T> PageResult<T> createPageResult(List<T> rows, Long count, Integer pageSize, Integer pageNo) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setRows(rows);
        pageResult.setTotalRows(Convert.toInt(count));
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPage(PageUtil.totalPage(pageResult.getTotalRows(), pageSize));
        return pageResult;
    }

}
