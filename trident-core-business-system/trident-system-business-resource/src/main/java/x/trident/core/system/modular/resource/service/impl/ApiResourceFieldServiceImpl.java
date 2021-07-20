package x.trident.core.system.modular.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import x.trident.core.db.api.factory.PageFactory;
import x.trident.core.db.api.factory.PageResultFactory;
import x.trident.core.db.api.pojo.page.PageResult;
import x.trident.core.system.api.exception.SystemModularException;
import x.trident.core.system.api.exception.enums.resource.ApiResourceFieldExceptionEnum;
import x.trident.core.system.api.pojo.resource.ApiResourceFieldRequest;
import x.trident.core.system.modular.resource.entity.ApiResourceField;
import x.trident.core.system.modular.resource.mapper.ApiResourceFieldMapper;
import x.trident.core.system.modular.resource.service.ApiResourceFieldService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 接口字段信息业务实现层
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@Service
public class ApiResourceFieldServiceImpl extends ServiceImpl<ApiResourceFieldMapper, ApiResourceField> implements ApiResourceFieldService {

	@Override
    public void add(ApiResourceFieldRequest apiResourceFieldRequest) {
        ApiResourceField apiResourceField = new ApiResourceField();
        BeanUtil.copyProperties(apiResourceFieldRequest, apiResourceField);
        this.save(apiResourceField);
    }

    @Override
    public void del(ApiResourceFieldRequest apiResourceFieldRequest) {
        ApiResourceField apiResourceField = this.queryApiResourceField(apiResourceFieldRequest);
        this.removeById(apiResourceField.getApiResourceId());
    }

    @Override
    public void edit(ApiResourceFieldRequest apiResourceFieldRequest) {
        ApiResourceField apiResourceField = this.queryApiResourceField(apiResourceFieldRequest);
        BeanUtil.copyProperties(apiResourceFieldRequest, apiResourceField);
        this.updateById(apiResourceField);
    }

    @Override
    public ApiResourceField detail(ApiResourceFieldRequest apiResourceFieldRequest) {
        return this.queryApiResourceField(apiResourceFieldRequest);
    }

    @Override
    public PageResult<ApiResourceField> findPage(ApiResourceFieldRequest apiResourceFieldRequest) {
        LambdaQueryWrapper<ApiResourceField> wrapper = createWrapper(apiResourceFieldRequest);
        Page<ApiResourceField> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public List<ApiResourceField> findList(ApiResourceFieldRequest apiResourceFieldRequest) {
        LambdaQueryWrapper<ApiResourceField> wrapper = this.createWrapper(apiResourceFieldRequest);
        return this.list(wrapper);
    }

    /**
     * 获取信息
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private ApiResourceField queryApiResourceField(ApiResourceFieldRequest apiResourceFieldRequest) {
        ApiResourceField apiResourceField = this.getById(apiResourceFieldRequest.getApiResourceId());
        if (ObjectUtil.isEmpty(apiResourceField)) {
            throw new SystemModularException(ApiResourceFieldExceptionEnum.APIRESOURCEFIELD_NOT_EXISTED);
        }
        return apiResourceField;
    }

    /**
     * 创建查询wrapper
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private LambdaQueryWrapper<ApiResourceField> createWrapper(ApiResourceFieldRequest apiResourceFieldRequest) {
        LambdaQueryWrapper<ApiResourceField> queryWrapper = new LambdaQueryWrapper<>();

        Long apiResourceId = apiResourceFieldRequest.getApiResourceId();
        String fieldLocation = apiResourceFieldRequest.getFieldLocation();
        String fieldName = apiResourceFieldRequest.getFieldName();
        String fieldCode = apiResourceFieldRequest.getFieldCode();
        String fieldType = apiResourceFieldRequest.getFieldType();
        String fieldRequired = apiResourceFieldRequest.getFieldRequired();
        String fieldValidationMsg = apiResourceFieldRequest.getFieldValidationMsg();

        queryWrapper.eq(ObjectUtil.isNotNull(apiResourceId), ApiResourceField::getApiResourceId, apiResourceId);
        queryWrapper.like(ObjectUtil.isNotEmpty(fieldLocation), ApiResourceField::getFieldLocation, fieldLocation);
        queryWrapper.like(ObjectUtil.isNotEmpty(fieldName), ApiResourceField::getFieldName, fieldName);
        queryWrapper.like(ObjectUtil.isNotEmpty(fieldCode), ApiResourceField::getFieldCode, fieldCode);
        queryWrapper.like(ObjectUtil.isNotEmpty(fieldType), ApiResourceField::getFieldType, fieldType);
        queryWrapper.like(ObjectUtil.isNotEmpty(fieldRequired), ApiResourceField::getFieldRequired, fieldRequired);
        queryWrapper.like(ObjectUtil.isNotEmpty(fieldValidationMsg), ApiResourceField::getFieldValidationMsg, fieldValidationMsg);

        return queryWrapper;
    }

}