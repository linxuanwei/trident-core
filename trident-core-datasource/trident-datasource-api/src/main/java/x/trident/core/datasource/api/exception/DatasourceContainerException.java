
package x.trident.core.datasource.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.datasource.api.constants.DatasourceContainerConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;


/**
 * 数据源容器操作异常
 *
 * @author 林选伟
 * @date 2020/10/31 22:10
 */
public class DatasourceContainerException extends ServiceException {

    public DatasourceContainerException(AbstractExceptionEnum exception, Object... params) {
        super(DatasourceContainerConstants.DS_CTN_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public DatasourceContainerException(AbstractExceptionEnum exception) {
        super(DatasourceContainerConstants.DS_CTN_MODULE_NAME, exception);
    }

}
