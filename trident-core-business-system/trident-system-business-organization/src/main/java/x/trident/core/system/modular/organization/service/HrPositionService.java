
package x.trident.core.system.modular.organization.service;

import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.system.api.PositionServiceApi;
import x.trident.core.system.api.pojo.organization.HrPositionRequest;
import x.trident.core.system.modular.organization.entity.HrPosition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 职位信息服务
 *
 * @author chenjinlong
 * @date 2020/11/04 11:07
 */
public interface HrPositionService extends IService<HrPosition>, PositionServiceApi {

    /**
     * 添加职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void add(HrPositionRequest hrPositionRequest);

    /**
     * 删除职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void del(HrPositionRequest hrPositionRequest);

    /**
     * 编辑职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void edit(HrPositionRequest hrPositionRequest);

    /**
     * 更新状态
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/18 23:00
     */
    void changeStatus(HrPositionRequest hrPositionRequest);

    /**
     * 查看详情
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    HrPosition detail(HrPositionRequest hrPositionRequest);

    /**
     * 查询职位详情列表
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情列表
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    List<HrPosition> findList(HrPositionRequest hrPositionRequest);

    /**
     * 分页查询职位详情列表
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情分页列表
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    PageResult<HrPosition> findPage(HrPositionRequest hrPositionRequest);

    /**
     * 批量删除系统职位
     *
     * @param hrPositionRequest 请求参数
     * @author 林选伟
     * @date 2021/4/8 13:51
     */
    void batchDel(HrPositionRequest hrPositionRequest);

}
