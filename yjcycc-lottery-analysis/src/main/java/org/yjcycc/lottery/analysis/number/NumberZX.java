package org.yjcycc.lottery.analysis.number;

import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.constant.NumberConstant;
import org.yjcycc.lottery.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NumberZX extends Number {

    public NumberZX() {}

    public NumberZX(Integer singleCount) {
        super(singleCount);
    }

    public NumberZX(String danNumber, String tuoNumber, Integer singleCount) {
        super(danNumber, tuoNumber, singleCount);
    }

    @Override
    public String getOrderNumber() {
        return getDanNumber();
    }

    @Override
    public int getOrderCombineSize(String orderNumber) {
        return calc(orderNumber).size();
    }

    @Override
    public String getOpenNumberExample(String orderNumber) {
        return calc(orderNumber).get(0);
    }

    @Override
    public void compare(CompareOpenVO vo) {
        List<String> orderNumberList = calc(vo.getOrderNumber());
        vo.setOrderCount(orderNumberList.size());
        vo.setOrderAmount(NumberConstant.BASE_AMOUNT.multiply(new BigDecimal(vo.getOrderCount())));
        int winCount = 0;// 中奖注数
        for (String number : orderNumberList) {
            if (number.equals(vo.getOpenNumber())) {
                winCount++;
            }
        }
        vo.setWinCount(winCount);
        vo.setWinAmount(vo.getPlayCategory().getSingleAmount().multiply(new BigDecimal(vo.getWinCount())));
    }

    private List<String> calc(String orderNumber) {
        int singleCount = getSingleCount();
        String[] orderNumberArr = StringUtils.stringToArray(orderNumber, Constant.ESCAPE_VERTICAL_SEPARATOR);
        String[] orderNumberArr1 = StringUtils.stringToArray(orderNumberArr[0], Constant.SEPARATOR);
        String[] orderNumberArr2 = StringUtils.stringToArray(orderNumberArr[1], Constant.SEPARATOR);
        String[] orderNumberArr3 = null;
        if (singleCount == 3) {
            orderNumberArr3 = StringUtils.stringToArray(orderNumberArr[2], Constant.SEPARATOR);
        }
        List<String> combineList = new ArrayList<>();
        for (String orderNumber1 : orderNumberArr1) {
            for (String orderNumber2 : orderNumberArr2) {
                if (orderNumber2.equals(orderNumber1)) {
                    continue;
                }
                if (singleCount == 2) {
                    combineList.add(orderNumber1 + Constant.SEPARATOR + orderNumber2);
                } else if (singleCount == 3) {
                    for (String orderNumber3 : orderNumberArr3) {
                        if (orderNumber3.equals(orderNumber2) || orderNumber3.equals(orderNumber1)) {
                            continue;
                        }
                        combineList.add(orderNumber1 + Constant.SEPARATOR + orderNumber2 + Constant.SEPARATOR + orderNumber3);
                    }
                }
            }
        }
        return combineList;
    }
}
