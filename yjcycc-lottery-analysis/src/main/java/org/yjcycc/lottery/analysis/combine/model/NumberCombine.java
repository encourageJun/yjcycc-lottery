package org.yjcycc.lottery.analysis.combine.model;

import org.apache.commons.lang.StringUtils;
import org.yjcycc.lottery.constant.NumberConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberCombine {

    /**
     * 1个号码组合
     */
    public List<String> getOneNumberCombine() {
        String[] numberCombine = new String[1];
        List<String> ONE_NUMBER_COMBINE = new ArrayList<>();
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, numberCombine, NumberConstant.SOURCE_NUMBER_ARR, ONE_NUMBER_COMBINE);
        print("One", ONE_NUMBER_COMBINE);
        return ONE_NUMBER_COMBINE;
    }

    /**
     * 2个号码组合
     */
    public List<String> getTwoNumberCombine() {
        String[] numberCombine = new String[2];
        List<String> TWO_NUMBER_COMBINE = new ArrayList<>();
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, numberCombine, NumberConstant.SOURCE_NUMBER_ARR, TWO_NUMBER_COMBINE);
        print("Two", TWO_NUMBER_COMBINE);
        return TWO_NUMBER_COMBINE;
    }

    /**
     * 3个号码组合
     */
    public List<String> getThreeNumberCombine() {
        String[] numberCombine = new String[3];
        List<String> THREE_NUMBER_COMBINE = new ArrayList<>();
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, numberCombine, NumberConstant.SOURCE_NUMBER_ARR, THREE_NUMBER_COMBINE);
        print("Three", THREE_NUMBER_COMBINE);
        return THREE_NUMBER_COMBINE;
    }

    /**
     * 4个号码组合
     */
    public List<String> getFourNumberCombine() {
        String[] numberCombine = new String[4];
        List<String> FOUR_NUMBER_COMBINE = new ArrayList<>();
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, numberCombine, NumberConstant.SOURCE_NUMBER_ARR, FOUR_NUMBER_COMBINE);
        print("Four", FOUR_NUMBER_COMBINE);
        return FOUR_NUMBER_COMBINE;
    }

    /**
     * 5个号码组合
     */
    public List<String> getFiveNumberCombine() {
        String[] numberCombine = new String[5];
        List<String> FIVE_NUMBER_COMBINE = new ArrayList<>();
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, numberCombine, NumberConstant.SOURCE_NUMBER_ARR, FIVE_NUMBER_COMBINE);
        print("Five", FIVE_NUMBER_COMBINE);
        return FIVE_NUMBER_COMBINE;
    }

    public void combine(int m, int n, String[] numberCombine, String[] sourceNumberArr, List<String> targetList) {
        int i = 0;
        for (i = n; i >= m; i--) {
            numberCombine[m - 1] = sourceNumberArr[i - 1];
            if (m > 1) {
                combine(m - 1, i - 1, numberCombine, sourceNumberArr, targetList);
            } else {
                targetList.add(StringUtils.join(numberCombine, ","));
            }
        }
    }

    private void print(String type, List<String> targetList) {
        /*System.out.println(type + ": ");
        for (int i = 0 ; i < targetList.size(); i++) {
            System.out.println((i+1) + ": " + targetList.get(i));
        }*/
    }

    public static void main(String[] args) {


    }

}
