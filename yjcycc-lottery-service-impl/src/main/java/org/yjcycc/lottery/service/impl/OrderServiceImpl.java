package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.Order;
import org.yjcycc.lottery.mapper.OrderMapper;
import org.yjcycc.lottery.service.IOrderService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 投注单(Order)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {

    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private void initMapper(OrderMapper orderMapper) {
        this.setBaseMapper(orderMapper);
    }
}