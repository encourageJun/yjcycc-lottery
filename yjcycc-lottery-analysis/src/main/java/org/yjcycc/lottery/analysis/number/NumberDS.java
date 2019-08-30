package org.yjcycc.lottery.analysis.number;

import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

public class NumberDS extends Number {

    public NumberDS() {}

    public NumberDS(Integer singleCount) {
        super(singleCount);
    }

    public NumberDS(String danNumber, String tuoNumber, Integer singleCount) {
        super(danNumber, tuoNumber,singleCount);
    }

    @Override
    public String getOrderNumber() {
        return getDanNumber();
    }

    @Override
    public int getOrderCombineSize(String orderNumber) {
        String[] orderNumberArr = StringUtils.stringToArray(orderNumber, Constant.VERTICAL_SEPARATOR);
        return orderNumberArr.length;
    }

    @Override
    public String getOpenNumberExample(String orderNumber) {
        return super.getOpenNumberExample(orderNumber);
    }

    @Override
    public void compare(CompareOpenVO vo) {
        String[] numberSplit = vo.getOrderNumber().split("|");
        List<String> combine = Arrays.asList(numberSplit);
        calcWinCount(vo, combine);
    }
}
