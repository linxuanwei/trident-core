
package x.trident.core.system.modular.user.mapper;

import x.trident.core.system.modular.user.entity.SysUser;
import x.trident.core.system.api.pojo.user.SysUserDTO;
import x.trident.core.system.api.pojo.user.request.SysUserRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户mapper接口
 *
 * @author luojie
 * @date 2020/11/6 14:50
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户列表
     *
     * @param page           分页参数
     * @param sysUserRequest 查询条件信息
     * @author 林选伟
     * @date 2020/11/21 15:16
     */
    Page<SysUserDTO> findUserPage(@Param("page") Page<SysUser> page, @Param("sysUserRequest") SysUserRequest sysUserRequest);

    /**
     * 查询用户列表
     *
     * @param sysUserRequest 查询条件信息
     * @author 林选伟
     * @date 2021/1/15 11:04
     */
    List<SysUserDTO> findUserList(@Param("sysUserRequest") SysUserRequest sysUserRequest);

}
