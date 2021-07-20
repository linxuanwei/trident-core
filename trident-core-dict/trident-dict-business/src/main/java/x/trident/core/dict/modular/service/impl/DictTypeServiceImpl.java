
package x.trident.core.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import x.trident.core.auth.api.context.LoginContext;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.dict.api.enums.DictTypeClassEnum;
import x.trident.core.dict.api.exception.DictException;
import x.trident.core.dict.api.exception.enums.DictExceptionEnum;
import x.trident.core.dict.modular.entity.SysDictType;
import x.trident.core.dict.modular.mapper.DictTypeMapper;
import x.trident.core.dict.modular.pojo.request.DictTypeRequest;
import x.trident.core.dict.modular.service.DictTypeService;
import x.trident.core.pinyin.api.PinYinApi;
import x.trident.core.enums.StatusEnum;
import x.trident.core.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类型表 服务实现类
 *
 * @author 林选伟
 * @date 2020/12/26 22:36
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, SysDictType> implements DictTypeService {

    @Resource
    private PinYinApi pinYinApi;

    @Override
    public void add(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        SysDictType sysDictType = new SysDictType();
        BeanUtil.copyProperties(dictTypeRequest, sysDictType);
        sysDictType.setStatusFlag(StatusEnum.ENABLE.getCode());
        sysDictType.setDictTypeNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDictType.getDictTypeName()));
        this.save(sysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        SysDictType sysDictType = this.querySysDictType(dictTypeRequest);
        sysDictType.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysDictType);

    }

    @Override
    public void edit(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 更新数据
        SysDictType sysDictType = this.querySysDictType(dictTypeRequest);
        BeanUtil.copyProperties(dictTypeRequest, sysDictType);
        sysDictType.setDictTypeCode(null);
        // 设置首字母拼音
        sysDictType.setDictTypeNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDictType.getDictTypeName()));
        this.updateById(sysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editStatus(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 更新数据
        SysDictType oldSysDictType = this.querySysDictType(dictTypeRequest);
        oldSysDictType.setStatusFlag(dictTypeRequest.getStatusFlag());
        this.updateById(oldSysDictType);
    }

    @Override
    public SysDictType detail(DictTypeRequest dictTypeRequest) {
        return this.getOne(this.createWrapper(dictTypeRequest), false);
    }

    @Override
    public List<SysDictType> findList(DictTypeRequest dictTypeRequest) {
        return this.list(this.createWrapper(dictTypeRequest));
    }

    @Override
    public PageResult<SysDictType> findPage(DictTypeRequest dictTypeRequest) {
        Page<SysDictType> page = this.page(PageFactory.defaultPage(), this.createWrapper(dictTypeRequest));
        return PageResultFactory.createPageResult(page);
    }


    /**
     * 校验dictTypeClass是否是系统字典，如果是系统字典只能超级管理员操作
     *
     * @author 林选伟
     * @date 2020/12/25 15:57
     */
    private void validateSystemTypeClassOperate(DictTypeRequest dictTypeRequest) {
        if (DictTypeClassEnum.SYSTEM_TYPE.getCode().equals(dictTypeRequest.getDictTypeClass())) {
            if (!LoginContext.me().getSuperAdminFlag()) {
                throw new DictException(DictExceptionEnum.SYSTEM_DICT_NOT_ALLOW_OPERATION);
            }
        }
    }

    /**
     * 根据主键id获取对象
     *
     * @author chenjinlong
     * @date 2021/1/26 13:28
     */
    private SysDictType querySysDictType(DictTypeRequest dictTypeRequest) {
        SysDictType sysDictType = this.getById(dictTypeRequest.getDictTypeId());
        if (ObjectUtil.isEmpty(sysDictType)) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, dictTypeRequest.getDictTypeId());
        }
        return sysDictType;
    }

    /**
     * 实体构建queryWrapper
     *
     * @author 林选伟
     * @date 2021/1/24 22:03
     */
    private LambdaQueryWrapper<SysDictType> createWrapper(DictTypeRequest dictTypeRequest) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();

        Long dictTypeId = dictTypeRequest.getDictTypeId();
        String dictTypeCode = dictTypeRequest.getDictTypeCode();
        String dictTypeName = dictTypeRequest.getDictTypeName();

        // SQL拼接
        queryWrapper.eq(ObjectUtil.isNotNull(dictTypeId), SysDictType::getDictTypeId, dictTypeId);
        queryWrapper.eq(ObjectUtil.isNotNull(dictTypeCode), SysDictType::getDictTypeCode, dictTypeCode);
        queryWrapper.like(ObjectUtil.isNotNull(dictTypeName), SysDictType::getDictTypeName, dictTypeName);

        // 查询未删除的
        queryWrapper.eq(SysDictType::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }

}
