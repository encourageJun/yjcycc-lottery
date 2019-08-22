package org.yjcycc.lottery.analysis.statistics;

import org.yjcycc.lottery.analysis.combine.Combiner;
import org.yjcycc.lottery.entity.OpenNumber;

import java.util.*;

public class StatisticsItem {

    private final static String IN_PRE = "IN_";

    private final static String OUT_PRE = "OUT_";

    private LinkedHashMap<String, Integer> inMap = new LinkedHashMap<>();

    private LinkedHashMap<String, Integer> outMap = new LinkedHashMap<>();

    private Map<String, Integer> tempInMap = new HashMap<>();
    private Map<String, Integer> tempOutMap = new HashMap<>();

    public StatisticsItem() {
        List<String> combineList = new Combiner().getOneNumberCombine();
        for (String oneNumber : combineList) {
            inMap.put(IN_PRE + oneNumber, 0);
            outMap.put(OUT_PRE + oneNumber, 0);
            tempInMap.put(IN_PRE + oneNumber, 0);
            tempOutMap.put(OUT_PRE + oneNumber, 0);
        }
    }

    public List<Map<String, Integer>> inOrOutTotal(List<OpenNumber> openList) {
        StatisticsRule statisticsRule = new StatisticsRule();
        List<String> combineList = new Combiner().getOneNumberCombine();

        for (OpenNumber openNumber : openList) {
            for (String oneNumber : combineList) {
                if (statisticsRule.anyoneInOpen(new String[]{oneNumber}, openNumber.getOpenNumber())) {
                    tempInMap.put(IN_PRE + oneNumber, tempInMap.get(IN_PRE + oneNumber) + 1);
                    inMap.put(IN_PRE + oneNumber, tempInMap.get(IN_PRE + oneNumber));
                }
                if (statisticsRule.anyoneOutOpen(new String[]{oneNumber}, openNumber.getOpenNumber())) {
                    tempOutMap.put(OUT_PRE + oneNumber, tempOutMap.get(OUT_PRE + oneNumber) + 1);
                    outMap.put(OUT_PRE + oneNumber, tempOutMap.get(OUT_PRE + oneNumber));
                }
            }
        }

        return getResult();
    }

    private List<Map<String, Integer>> getResult() {
        List<Map<String, Integer>> list = new ArrayList<>();
        list.add(inMap);
        list.add(outMap);
        return list;
    }


}
