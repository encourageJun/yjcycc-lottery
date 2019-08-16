package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.SysDict;
import org.yjcycc.lottery.service.ISysDictService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 数据字典(SysDict)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("sysDict")
public class SysDictController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysDict selectOne(Long id) {
        ISysDictService sysDictService = (ISysDictService) RMIClient.getRemoteService(ISysDictService.class);
        
        try {
            return sysDictService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}