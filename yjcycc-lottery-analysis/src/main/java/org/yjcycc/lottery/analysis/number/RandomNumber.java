package org.yjcycc.lottery.analysis.number;

import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.constant.NumberConstant;
import org.yjcycc.lottery.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomNumber {

    public String next(String danNumber, int targetCount) {
        String[] danNumbers = StringUtils.stringToArray(danNumber, Constant.ESCAPE_VERTICAL_SEPARATOR);
        if (danNumbers != null && danNumbers.length > 0) {
            String tuoNumber = Constant.EMPTY;
            for (String dan : danNumbers) {
                List<String> dans = Arrays.asList(StringUtils.stringToArray(dan, Constant.SEPARATOR));
                List<String> others = new ArrayList<>();
                for (String d : NumberConstant.SOURCE_NUMBER_LIST) {
                    if (dans.contains(d)) {
                        continue;
                    }
                    others.add(d);
                }
                int i = 0;
                while (i < targetCount) {
                    int index = getNext(others.size(), tuoNumber, others);
                    if (i != 0) {
                        tuoNumber = Constant.VERTICAL_SEPARATOR + tuoNumber;
                    }
                    tuoNumber = others.get(index) + tuoNumber;
                    i++;
                }
                if (danNumbers.length > 1) {
                    tuoNumber = tuoNumber + Constant.VERTICAL_SEPARATOR;
                }
            }
            return tuoNumber;
        }
        return null;
    }

    private int getNext(int range, String number, List<String> others) {
        int index = new Random().nextInt(range);
        String num = others.get(index);
        if (number.contains(num)) {
            return getNext(range, number, others);
        }
        return index;
    }

}
