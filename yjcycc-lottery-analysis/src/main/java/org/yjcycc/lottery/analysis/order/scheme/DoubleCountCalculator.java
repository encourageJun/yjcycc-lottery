package org.yjcycc.lottery.analysis.order.scheme;

import org.yjcycc.lottery.analysis.number.Number;
import org.yjcycc.lottery.analysis.number.NumberConstructor;
import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.analysis.vo.DoubleCountSchemeVO;
import org.yjcycc.lottery.constant.dict.DoubleScheme;

import java.math.BigDecimal;

public class DoubleCountCalculator {

    /**
     * 计算倍数
     * @param vo
     * @return
     */
    public DoubleCountSchemeVO calculate(DoubleCountSchemeVO vo) {
        // 计算倍数
        if (DoubleScheme.scheme_1.getValue() == vo.getDictDoubleScheme()) {
            // 获取例子投注号码
            String orderNumber = vo.getOrderNumber();
            Number number = NumberConstructor.getConstructor(vo.getPlayCategory().getClassName(), vo.getPlayCategory().getSingleCount());
            String openNumberExample = number.getOpenNumberExample(orderNumber);

            // 计算中奖注数和中奖金额
            CompareOpenVO compareOpenVO = new CompareOpenVO(vo.getPlayCategory(), orderNumber, openNumberExample);
            Number number1 = NumberConstructor.getConstructor(vo.getPlayCategory().getClassName());
            number1.compare(compareOpenVO);
            vo.setDoubleCount(calcByFormula(vo.getDoubleCount(), vo, compareOpenVO));
        } else if (DoubleScheme.scheme_2.getValue() == vo.getDictDoubleScheme()) {
            vo.setDoubleCount(vo.getDoubleCount());
        }
        return vo;
    }

    /**
     * 根据公式计算倍数
     * 公式: (中奖金额 * 倍数 / 金额模式 - 投注总金额) / 投注总金额 >= 盈利率
     * @param doubleCount
     * @param doubleCountSchemeVO
     * @param compareOpenVO
     * @return
     */
    private int calcByFormula(int doubleCount, DoubleCountSchemeVO doubleCountSchemeVO, CompareOpenVO compareOpenVO) {
        BigDecimal currentOrderAmount = compareOpenVO.getOrderAmount().multiply(new BigDecimal(doubleCount)).divide(new BigDecimal(doubleCountSchemeVO.getDictAmountModel()), 4, BigDecimal.ROUND_UP);
        BigDecimal totalOrderAmount = doubleCountSchemeVO.getTotalOrderAmount().add(currentOrderAmount);
        BigDecimal totalWinAmount = compareOpenVO.getWinAmount().multiply(new BigDecimal(doubleCount)).divide(new BigDecimal(doubleCountSchemeVO.getDictAmountModel()), 4, BigDecimal.ROUND_UP);
        double leftRate = totalWinAmount.subtract(totalOrderAmount).divide(totalOrderAmount, 4, BigDecimal.ROUND_FLOOR).doubleValue();
        // 公式: (中奖金额 * 倍数 / 金额模式 - 投注总金额) / 投注总金额 >= 盈利率
        if (leftRate >= doubleCountSchemeVO.getProfitRate()) {
            return doubleCount;
        } else {
            doubleCount++;
            return calcByFormula(doubleCount, doubleCountSchemeVO, compareOpenVO);
        }
    }

}
