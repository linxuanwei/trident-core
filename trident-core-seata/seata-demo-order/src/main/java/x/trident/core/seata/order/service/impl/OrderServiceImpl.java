package x.trident.core.seata.order.service.impl;

import x.trident.core.seata.order.consumer.StorageConsumer;
import x.trident.core.seata.order.consumer.WalletConsumer;
import x.trident.core.seata.order.entity.Order;
import x.trident.core.seata.order.mapper.OrderMapper;
import x.trident.core.seata.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单 业务层
 *
 * @author wangyl
 * @date 2021/04/21 08:33
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StorageConsumer storageConsumer;

    @Resource
    private WalletConsumer walletConsumer;

    @Resource
    private OrderMapper orderMapper;

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public Order create(String userId, String commodityCode, int orderCount) {
        Order order = new Order();

        //保存订单
        orderMapper.insertOrder(order);

        //扣减商品库存
        storageConsumer.deduct(commodityCode, orderCount);

        //扣用户钱
        walletConsumer.debit(userId, order.getTotalAmount());

        return order;
    }

}
