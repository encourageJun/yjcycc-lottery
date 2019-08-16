package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.PlanConfig;
import org.yjcycc.lottery.service.IPlanConfigService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 投注计划配置(PlanConfig)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("planConfig")
public class PlanConfigController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public PlanConfig selectOne(Long id) {
        IPlanConfigService planConfigService = (IPlanConfigService) RMIClient.getRemoteService(IPlanConfigService.class);
        
        try {
            return planConfigService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}