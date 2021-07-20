
package x.trident.core.system.modular.user.service;

import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.pojo.dict.SimpleDict;
import x.trident.core.system.api.UserServiceApi;
import x.trident.core.system.api.pojo.user.SysUserDTO;
import x.trident.core.system.api.pojo.user.UserSelectTreeNode;
import x.trident.core.system.api.pojo.user.request.SysUserRequest;
import x.trident.core.system.modular.user.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统用户service
 *
 * @author luojie
 * @date 2020/11/6 10:28
 */
public interface SysUserService extends IService<SysUser>, UserServiceApi {

    /**
     * 新用户注册
     *
     * @param sysUserRequest 请求参数
     * @author chenjinlong
     * @date 2021/1/15 15:17
     */
    void register(SysUserRequest sysUserRequest);

    /**
     * 增加用户
     *
     * @param sysUserRequest 请求参数封装
     * @author 林选伟
     * @date 2020/11/21 12:32
     */
    void add(SysUserRequest sysUserRequest);

    /**
     * 删除系统用户
     *
     * @param sysUserRequest 删除参数
     * @author 林选伟
     * @date 2020/11/21 14:54
     */
    void del(SysUserRequest sysUserRequest);

    /**
     * 编辑用户
     *
     * @param sysUserRequest 请求参数封装
     * @author 林选伟
     * @date 2020/11/21 12:32
     */
    void edit(SysUserRequest sysUserRequest);

    /**
     * 更新用户信息（一般用于更新个人信息）
     *
     * @param sysUserRequest 请求参数封装
     * @author 林选伟
     * @date 2020/11/21 12:32
     */
    void editInfo(SysUserRequest sysUserRequest);

    /**
     * 修改状态
     *
     * @param sysUserRequest 请求参数封装
     * @author 林选伟
     * @date 2020/11/21 14:19
     */
    void editStatus(SysUserRequest sysUserRequest);

    /**
     * 修改密码
     *
     * @param sysUserRequest 请求参数封装
     * @author 林选伟
     * @date 2020/11/21 14:26
     */
    void editPassword(SysUserRequest sysUserRequest);

    /**
     * 重置密码
     *
     * @param sysUserRequest 重置参数
     * @author luojie
     * @date 2020/11/6 13:47
     */
    void resetPassword(SysUserRequest sysUserRequest);

    /**
     * 修改头像
     *
     * @param sysUserRequest 修改头像参数
     * @author luojie
     * @date 2020/11/6 13:47
     */
    void editAvatar(SysUserRequest sysUserRequest);

    /**
     * 授权角色给某个用户
     *
     * @param sysUserRequest 授权参数
     * @author 林选伟
     * @date 2020/11/21 14:43
     */
    void grantRole(SysUserRequest sysUserRequest);

    /**
     * 授权组织机构数据范围给某个用户
     *
     * @param sysUserRequest 授权参数
     * @author 林选伟
     * @date 2020/11/21 14:48
     */
    void grantData(SysUserRequest sysUserRequest);

    /**
     * 查看用户详情
     *
     * @param sysUserRequest 查看参数
     * @return 用户详情结果
     * @author luojie
     * @date 2020/11/6 13:46
     */
    SysUserDTO detail(SysUserRequest sysUserRequest);

    /**
     * 查询系统用户
     *
     * @param sysUserRequest 查询参数
     * @return 查询分页结果
     * @author 林选伟
     * @date 2020/11/21 15:24
     */
    PageResult<SysUserDTO> findPage(SysUserRequest sysUserRequest);

    /**
     * 导出用户
     *
     * @param response httpResponse
     * @author luojie
     * @date 2020/11/6 13:47
     */
    void export(HttpServletResponse response);

    /**
     * 用户选择树数据
     *
     * @param sysUserRequest 参数
     * @author 林选伟
     * @date 2021/1/15 11:16
     */
    List<UserSelectTreeNode> userSelectTree(SysUserRequest sysUserRequest);

    /**
     * 根据账号获取用户
     *
     * @param account 账号
     * @return 用户
     * @author luojie
     * @date 2020/11/6 15:09
     */
    SysUser getUserByAccount(String account);

    /**
     * 获取用户头像的url
     *
     * @param fileId 文件id
     * @author 林选伟
     * @date 2020/12/27 19:13
     */
    String getUserAvatarUrl(Long fileId);

    /**
     * 获取用户头像的url
     *
     * @param fileId 文件id
     * @param token  预览文件带的token
     * @author 林选伟
     * @date 2020/12/27 19:13
     */
    String getUserAvatarUrl(Long fileId, String token);

    /**
     * 根据机构id获取用户树节点列表
     *
     * @param orgId 机构id
     * @author 林选伟
     * @date 2021/1/15 11:16
     */
    List<UserSelectTreeNode> getUserTreeNodeList(Long orgId);

    /**
     * 用户下拉列表选择
     *
     * @param sysUserRequest 查询参数
     * @return 用户列表集合
     * @author luojie
     * @date 2020/11/6 13:47
     */
    List<SimpleDict> selector(SysUserRequest sysUserRequest);

    /**
     * 批量删除用户
     *
     * @author 林选伟
     * @date 2021/4/7 16:13
     */
    void batchDelete(SysUserRequest sysUserRequest);

}
