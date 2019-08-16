package org.yjcycc.lottery.controller;

import org.yjcycc.lottery.entity.PlayCategory;
import org.yjcycc.lottery.service.IPlayCategoryService;
import org.yjcycc.tools.zk.rmi.RMIClient;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

/**
 * 玩法类别(PlayCategory)表控制层
 *
 * @author makejava
 * @since 2019-08-16 19:20:38
 */
@RestController
@RequestMapping("playCategory")
public class PlayCategoryController {

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public PlayCategory selectOne(Long id) {
        IPlayCategoryService playCategoryService = (IPlayCategoryService) RMIClient.getRemoteService(IPlayCategoryService.class);
        
        try {
            return playCategoryService.getById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

}