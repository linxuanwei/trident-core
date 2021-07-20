
package x.trident.core.mongodb.api.exception;

import x.trident.core.mongodb.api.constants.MongodbConstants;
import x.trident.core.exception.AbstractExceptionEnum;
import x.trident.core.exception.base.ServiceException;

/**
 * Mongodb模块的异常
 *
 * @author 林选伟
 * @date 2021/13/17 23:59
 */
public class MongodbException extends ServiceException {

    public MongodbException(AbstractExceptionEnum exception) {
        super(MongodbConstants.MONGODB_MODULE_NAME, exception);
    }

}
