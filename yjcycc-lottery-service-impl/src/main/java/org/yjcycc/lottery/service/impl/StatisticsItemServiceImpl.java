package org.yjcycc.lottery.service.impl;

import org.springframework.stereotype.Service;
import org.yjcycc.lottery.analysis.statistics.StatisticsItem;
import org.yjcycc.lottery.entity.OpenNumber;

import java.util.ArrayList;
import java.util.List;

@Service("statisticsItemService")
public class StatisticsItemServiceImpl {

    public void statisticsInOrOutTotal() {
        List<OpenNumber> openList = new ArrayList<>();
        new StatisticsItem().inOrOutTotal(openList);
    }

}
