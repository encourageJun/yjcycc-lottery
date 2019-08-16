package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.Plan;
import org.yjcycc.lottery.service.IPlanService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 投注计划(Plan)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("plan")
public class PlanController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Plan selectOne(Long id) {
        IPlanService planService = (IPlanService) RMIClient.getRemoteService(IPlanService.class);
        
        try {
            return planService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}