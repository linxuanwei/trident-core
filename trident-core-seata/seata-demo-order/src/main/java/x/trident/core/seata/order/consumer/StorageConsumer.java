package x.trident.core.seata.order.consumer;

import x.trident.core.seata.api.StorageApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 仓储 api
 *
 * @author wangyl
 * @date 2021/04/10 16:42
 */
@FeignClient(name = "seata-demo-storage")
public interface StorageConsumer extends StorageApi {
}
