package org.yjcycc.lottery.analysis.number;

import org.yjcycc.lottery.analysis.combine.Combiner;
import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.utils.StringUtils;

import java.util.List;

public class NumberFS extends Number {

    public NumberFS() {}

    public NumberFS(Integer singleCount) {
        super(singleCount);
    }

    public NumberFS(String danNumber, String tuoNumber, Integer singleCount) {
        super(danNumber, tuoNumber, singleCount);
    }

    @Override
    public String getOrderNumber() {
        return StringUtils.listToString(new Combiner().getReverse(getDanNumber()), Constant.SEPARATOR);
    }

    @Override
    public int getOrderCombineSize(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            return 0;
        }
        String[] orderNumberArr = StringUtils.stringToArray(orderNumber, Constant.SEPARATOR);
        return new Combiner().calcAnyCombineSize(getSingleCount(), orderNumberArr.length, orderNumberArr);
    }

    @Override
    public String getOpenNumberExample(String orderNumber) {
        return super.getOpenNumberExample(orderNumber);
    }

    @Override
    public void compare(CompareOpenVO vo) {
        String[] numberArr = vo.getOrderNumber().split(",");
        List<String> combine = new Combiner().calcAnyCombine(vo.getPlayCategory().getSingleCount(), numberArr.length, numberArr);
        calcWinCount(vo, combine);
    }
}
