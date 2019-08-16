package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.SysParameter;
import org.yjcycc.lottery.service.ISysParameterService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 系统参数(SysParameter)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("sysParameter")
public class SysParameterController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysParameter selectOne(Long id) {
        ISysParameterService sysParameterService = (ISysParameterService) RMIClient.getRemoteService(ISysParameterService.class);
        
        try {
            return sysParameterService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}