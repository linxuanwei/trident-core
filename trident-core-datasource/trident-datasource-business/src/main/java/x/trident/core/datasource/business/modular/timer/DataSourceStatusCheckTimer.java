
package x.trident.core.datasource.business.modular.timer;


import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Component;
import x.trident.core.datasource.api.enums.DataSourceStatusEnum;
import x.trident.core.datasource.api.pojo.request.DatabaseInfoRequest;
import x.trident.core.datasource.business.modular.entity.DatabaseInfo;
import x.trident.core.datasource.business.modular.service.DatabaseInfoService;
import x.trident.core.timer.api.TimerAction;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时刷新各个数据源的状态，并更新到数据库
 *
 * @author 林选伟
 * @date 2021/4/22 10:45
 */
@Component
public class DataSourceStatusCheckTimer implements TimerAction {

    @Resource
    private DatabaseInfoService databaseInfoService;

    @Override
    public void action(String params) {

        // 获取所有的数据源信息
        List<DatabaseInfo> list = databaseInfoService.list();

        if (ObjectUtil.isEmpty(list)) {
            return;
        }

        // 校验每个数据库连接的信息
        for (DatabaseInfo databaseInfo : list) {

            // 设置jdbc相关连接
            DatabaseInfoRequest databaseInfoRequest = new DatabaseInfoRequest();
            databaseInfoRequest.setJdbcDriver(databaseInfo.getJdbcDriver());
            databaseInfoRequest.setJdbcUrl(databaseInfo.getJdbcUrl());
            databaseInfoRequest.setUsername(databaseInfo.getUsername());
            databaseInfoRequest.setPassword(databaseInfo.getPassword());

            // 检测每个连接的准确性
            try {
                databaseInfoService.validateConnection(databaseInfoRequest);
            } catch (Exception exception) {
                // 如果有错误信息，将错误信息存储到表中
                String errorMessage = exception.getMessage();

                // 如果当前非错误状态则更新状态
                if (!DataSourceStatusEnum.ERROR.getCode().equals(databaseInfo.getStatusFlag())) {
                    databaseInfo.setStatusFlag(DataSourceStatusEnum.ERROR.getCode());
                    databaseInfo.setErrorDescription(errorMessage);
                    databaseInfoService.updateById(databaseInfo);
                }

                continue;
            }

            // 如果数据库状态为空，则修改为正常
            if (!DataSourceStatusEnum.ENABLE.getCode().equals(databaseInfo.getStatusFlag())) {
                databaseInfo.setStatusFlag(DataSourceStatusEnum.ENABLE.getCode());
                databaseInfoService.updateById(databaseInfo);
            }
        }

    }

}
