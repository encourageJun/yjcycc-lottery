package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.UserBalanceRecord;
import org.yjcycc.lottery.service.IUserBalanceRecordService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 余额变动(UserBalanceRecord)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("userBalanceRecord")
public class UserBalanceRecordController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserBalanceRecord selectOne(Long id) {
        IUserBalanceRecordService userBalanceRecordService = (IUserBalanceRecordService) RMIClient.getRemoteService(IUserBalanceRecordService.class);
        
        try {
            return userBalanceRecordService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}