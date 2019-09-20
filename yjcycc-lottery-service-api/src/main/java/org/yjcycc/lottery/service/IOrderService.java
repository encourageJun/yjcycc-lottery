package org.yjcycc.lottery.service;

import org.yjcycc.lottery.entity.Order;
import org.yjcycc.tools.common.service.BaseService;

import java.rmi.RemoteException;
import java.util.List;

/**
 * 投注单(Order)表服务接口
 *
 * @author makejava
 * @since 2019-08-16 18:52:07
 */
public interface IOrderService extends BaseService<Order> {

    List<Order> getOrderList(String stage, String lotteryType) throws Exception;

    List<Order> getOrderList(Integer status, String lotteryType) throws Exception;

    List<Order> getOrderListByPlanId(Long planId) throws Exception;

}