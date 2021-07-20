
package x.trident.core.office.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的数据监听器
 *
 * @author 林选伟
 * @date 2020/11/4 13:55
 */
public class SimpleDataListener<T> extends AnalysisEventListener<T> {

    /**
     * 实体类List集合
     */
    private final List<T> dataList = new ArrayList<>();

    /**
     * 获取实体类List集合
     *
     * @return 实体类List集合
     */
    public List<T> getDataList() {
        return dataList;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    Excel每行数据转换成的对象类
     * @param context EasyExcel分析上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        // 添加到集合中
        dataList.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context EasyExcel分析上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // unused
    }
}

