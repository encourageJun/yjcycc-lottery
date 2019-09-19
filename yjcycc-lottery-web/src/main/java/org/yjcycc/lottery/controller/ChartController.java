package org.yjcycc.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.entity.OpenNumber;
import org.yjcycc.lottery.service.IOpenNumberService;
import org.yjcycc.tools.common.Pager;
import org.yjcycc.tools.zk.rmi.RMIClient;

import java.util.*;

/**
 * 走势图
 */

@Controller
@RequestMapping("chart")
public class ChartController {

    @RequestMapping("show")
    public String show(String chartType, Model model) {
        model.addAttribute("chartType", chartType);
        if ("five".equals(chartType)) {
            return "chart/five";
        } else {
            return "chart/three";
        }
    }

    @ResponseBody
    @RequestMapping("getOpenList")
    public Map<String,Object> getOpenList(String lotteryType) {
        Map<String,Object> retMap = new HashMap<>();

        IOpenNumberService openNumberService = (IOpenNumberService) RMIClient.getRemoteService(IOpenNumberService.class);

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("lotteryType", lotteryType);
            Pager<OpenNumber> pager = openNumberService.findPager(map, 1, 50);
            if (pager != null && pager.getList() != null) {
                List<OpenNumber> list = pager.getList();
                retMap.put("lastStage", pager.getList().get(0).getStage());
                Collections.sort(list, new Comparator<OpenNumber>() {
                    public int compare(OpenNumber arg0, OpenNumber arg1) {
                        return arg0.getStageIndex().compareTo(arg1.getStageIndex());
                    }
                });
                retMap.put("list", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retMap;
    }

    @ResponseBody
    @RequestMapping("getSingle")
    public OpenNumber getSingle(String lotteryType) {
        IOpenNumberService openNumberService = (IOpenNumberService) RMIClient.getRemoteService(IOpenNumberService.class);

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("lotteryType", lotteryType);
            Pager<OpenNumber> pager = openNumberService.findPager(map, 1, 50);
            if (pager != null && pager.getList() != null) {
                List<OpenNumber> list = pager.getList();
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
