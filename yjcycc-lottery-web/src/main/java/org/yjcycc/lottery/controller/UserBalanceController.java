package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.UserBalance;
import org.yjcycc.lottery.service.IUserBalanceService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 用户余额(UserBalance)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("userBalance")
public class UserBalanceController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserBalance selectOne(Long id) {
        IUserBalanceService userBalanceService = (IUserBalanceService) RMIClient.getRemoteService(IUserBalanceService.class);
        
        try {
            return userBalanceService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}