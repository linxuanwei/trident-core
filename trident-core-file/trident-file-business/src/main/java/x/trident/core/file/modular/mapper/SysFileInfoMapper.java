
package x.trident.core.file.modular.mapper;

import x.trident.core.file.api.pojo.request.SysFileInfoRequest;
import x.trident.core.file.api.pojo.response.SysFileInfoListResponse;
import x.trident.core.file.modular.entity.SysFileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
public interface SysFileInfoMapper extends BaseMapper<SysFileInfo> {

    /**
     * 附件列表（有分页）
     *
     * @author majianguo
     * @date 2020/12/27 12:57
     */
    List<SysFileInfoListResponse> fileInfoList(@Param("page") Page<SysFileInfoListResponse> page, @Param("sysFileInfoRequest") SysFileInfoRequest sysFileInfoRequest);

}
