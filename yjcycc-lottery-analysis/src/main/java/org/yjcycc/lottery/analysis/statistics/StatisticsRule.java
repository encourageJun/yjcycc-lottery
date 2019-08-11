package org.yjcycc.lottery.analysis.statistics;

import org.springframework.stereotype.Component;

@Component("StatisticsRule")
public class StatisticsRule {

    /**
     * 出号: 组合号码中有任一号码出现在开奖号码中
     * @return
     */
    public boolean anyoneInOpen(String[] combineNumbers, String openNumber) {
        for (String combineNumber : combineNumbers) {
            if (openNumber.contains(combineNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 漏号: 组合号码中所有号码都未出现在开奖号码中
     * @return
     */
    public boolean anyoneOutOpen(String[] combineNumbers, String openNumber) {
        for (String combineNumber : combineNumbers) {
            if (openNumber.contains(combineNumber)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 同步: 2个号码同期出/未出在开奖号码中
     * @return
     */
    public boolean aync(String number1, String number2, String openNumber) {
        if ((openNumber.contains(number1) && openNumber.contains(number2)) || (!openNumber.contains(number1) && !openNumber.contains(number2))) {
            return true;
        }
        return false;
    }

    /**
     * 对冲: 2个号码总是一出一漏在开奖号码中
     * @return
     */
    public boolean hedge(String number1, String number2, String openNumber) {
        if ((openNumber.contains(number1) && !openNumber.contains(number2)) || (!openNumber.contains(number1) && openNumber.contains(number2))) {
            return true;
        }
        return false;
    }

}
