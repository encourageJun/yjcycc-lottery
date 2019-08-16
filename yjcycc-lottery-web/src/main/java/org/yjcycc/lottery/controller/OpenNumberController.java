package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.OpenNumber;
import org.yjcycc.lottery.service.IOpenNumberService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 开奖号码(OpenNumber)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("openNumber")
public class OpenNumberController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public OpenNumber selectOne(Long id) {
        IOpenNumberService openNumberService = (IOpenNumberService) RMIClient.getRemoteService(IOpenNumberService.class);
        
        try {
            return openNumberService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}