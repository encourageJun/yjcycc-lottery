package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.Order;
import org.yjcycc.lottery.service.IOrderService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 投注单(Order)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("order")
public class OrderController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Order selectOne(Long id) {
        IOrderService orderService = (IOrderService) RMIClient.getRemoteService(IOrderService.class);
        
        try {
            return orderService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}