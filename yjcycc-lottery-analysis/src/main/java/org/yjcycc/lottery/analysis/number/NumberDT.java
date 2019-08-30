package org.yjcycc.lottery.analysis.number;

import org.yjcycc.lottery.analysis.combine.Combiner;
import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NumberDT extends Number {

    public NumberDT() {}

    public NumberDT(Integer singleCount) {
        super(singleCount);
    }

    public NumberDT(String danNumber, String tuoNumber, Integer singleCount) {
        super(danNumber, tuoNumber, singleCount);
    }

    @Override
    public String getOrderNumber() {
        return getDanNumber() + Constant.VERTICAL_SEPARATOR + getTuoNumber();
    }

    @Override
    public int getOrderCombineSize(String orderNumber) {
        // 胆拖
        String[] danNumbers = StringUtils.stringToArray(getDanNumber(), Constant.SEPARATOR);
        String[] tuoNumbers = StringUtils.stringToArray(getTuoNumber(), Constant.SEPARATOR);
        int danCount = danNumbers.length;
        int tuoCount = getSingleCount() - danCount;
        return new Combiner().calcAnyCombineSize(tuoCount, tuoNumbers.length, tuoNumbers);
    }

    @Override
    public String getOpenNumberExample(String orderNumber) {
        String[] numberSplit = orderNumber.split("|");
        String dan = numberSplit[0];
        String[] danArr = numberSplit[0].split(",");
        String[] tuoArr = numberSplit[1].split(",");
        int singleCount = getSingleCount();
        if (singleCount > danArr.length) {
            List<String> tuoCombine = new Combiner().calcAnyCombine(singleCount - danArr.length, tuoArr.length, tuoArr);
            String singleOrderNumber = dan + Constant.SEPARATOR + tuoCombine.get(0);
            return super.getOpenNumberExample(singleOrderNumber);
        }
        return null;
    }

    @Override
    public void compare(CompareOpenVO vo) {
        String[] numberSplit = vo.getOrderNumber().split("|");
        String dan = numberSplit[0];
        String[] danArr = numberSplit[0].split(",");
        String[] tuoArr = numberSplit[1].split(",");
        int singleCount = vo.getPlayCategory().getSingleCount();
        if (singleCount > danArr.length) {
            List<String> tuoCombine = new Combiner().calcAnyCombine(singleCount - danArr.length, tuoArr.length, tuoArr);
            List<String> combine = new ArrayList<>();
            if (tuoCombine != null && !tuoCombine.isEmpty()) {
                for (String tuoNumber : tuoCombine) {
                    combine.add(dan + Constant.SEPARATOR + tuoNumber);
                }
            }
            calcWinCount(vo, combine);
        }
    }
}
