package org.yjcycc.lottery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjcycc.lottery.entity.OpenNumber;
import org.yjcycc.lottery.service.IOpenNumberService;
import org.yjcycc.lottery.service.vo.OpenVO;
import org.yjcycc.tools.zk.rmi.RMIClient;

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

    @PostMapping("open")
    public void open(OpenVO openVO) {

        if (openVO.getOpenList() == null || openVO.getOpenList().isEmpty()) {
            return;
        }



    }

}