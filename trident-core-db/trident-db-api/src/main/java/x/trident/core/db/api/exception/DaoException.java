
package x.trident.core.db.api.exception;

import cn.hutool.core.util.StrUtil;
import x.trident.core.db.api.constants.DbConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;


/**
 * 数据库操作异常
 *
 * @author 林选伟
 * @date 2020/10/15 15:59
 */
public class DaoException extends ServiceException {

    public DaoException(AbstractExceptionEnum exception) {
        super(DbConstants.DB_MODULE_NAME, exception);
    }

    public DaoException(AbstractExceptionEnum exception, Object... params) {
        super(DbConstants.DB_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
