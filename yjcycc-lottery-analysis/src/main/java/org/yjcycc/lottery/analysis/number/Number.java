package org.yjcycc.lottery.analysis.number;

import org.yjcycc.lottery.analysis.combine.Combiner;
import org.yjcycc.lottery.analysis.open.compare.OpenRule;
import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.constant.NumberConstant;
import org.yjcycc.lottery.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Number {

    // 胆码
    private String danNumber;

    // 拖码
    private String tuoNumber;

    // 单注号码个数
    private Integer singleCount;

    public abstract String getOrderNumber();

    public abstract int getOrderCombineSize(String orderNumber);

    public abstract void compare(CompareOpenVO vo);

    public String getOpenNumberExample(String orderNumber) {
        String[] orderNumberArr = StringUtils.stringToArray(orderNumber, Constant.SEPARATOR);
        int singleCount = getSingleCount();
        if (singleCount > 5) {
            return StringUtils.join(Arrays.copyOfRange(orderNumberArr, 0, 5));
        } else {
            String[] openNumberArr = new String[5];
            System.arraycopy(orderNumberArr, 0, openNumberArr, 0, orderNumberArr.length);
            String[] otherNumberArr = StringUtils.listToArray(new Combiner().getReverse(orderNumber).subList(0, singleCount - orderNumberArr.length));
            System.arraycopy(otherNumberArr, 0, openNumberArr, 0, otherNumberArr.length);
            return StringUtils.join(openNumberArr);
        }
    }

    public void calcWinCount(CompareOpenVO vo, List<String> combine) {
        if (combine == null || combine.isEmpty()) {
            return;
        }
        vo.setOrderCount(combine.size());
        vo.setOrderAmount(NumberConstant.BASE_AMOUNT.multiply(new BigDecimal(vo.getOrderCount())));

        List<String> combineList = new ArrayList<>();
        for (String numberCombine : combine) {
            String[] numberCombines = numberCombine.split(",");
            if (numberCombines.length > 5) {
                List<String> tempCombine = new Combiner().calcAnyCombine(5, numberCombines.length, numberCombines);
                combineList.addAll(tempCombine);
            } else {
                combineList.add(numberCombine);
            }
        }
        OpenRule rule = new OpenRule();
        int winCount = 0;// 中奖注数
        for (String number : combineList) {
            System.out.println("number: " + number);
            if (rule.isWin(number, vo.getOpenNumber())) {
                winCount++;
            }
        }
        vo.setWinCount(winCount);
        vo.setWinAmount(vo.getPlayCategory().getSingleAmount().multiply(new BigDecimal(winCount)));
    }

    public Number() {}

    public Number(Integer singleCount) {
        this.singleCount = singleCount;
    }

    public Number(String danNumber, String tuoNumber, Integer singleCount) {
        this.danNumber = danNumber;
        this.tuoNumber = tuoNumber;
        this.singleCount = singleCount;
    }



    public String getDanNumber() {
        return danNumber;
    }

    public void setDanNumber(String danNumber) {
        this.danNumber = danNumber;
    }

    public String getTuoNumber() {
        return tuoNumber;
    }

    public void setTuoNumber(String tuoNumber) {
        this.tuoNumber = tuoNumber;
    }

    public Integer getSingleCount() {
        return singleCount;
    }

    public void setSingleCount(Integer singleCount) {
        this.singleCount = singleCount;
    }
}
