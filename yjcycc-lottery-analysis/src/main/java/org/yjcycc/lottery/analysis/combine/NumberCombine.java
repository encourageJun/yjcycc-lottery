package org.yjcycc.lottery.analysis.combine;

import org.yjcycc.lottery.constant.NumberConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberCombine {

    private final static String EMPTY_NUMBER = "";

    private static String[] numberCombine;

    /**
     * 1个号码组合
     */
    public static List<String> ONE_NUMBER_COMBINE = new ArrayList<>();

    /**
     * 2个号码组合
     */
    public static List<String> TWO_NUMBER_COMBINE = new ArrayList<>();

    /**
     * 3个号码组合
     */
    public static List<String> THREE_NUMBER_COMBINE = new ArrayList<>();

    /**
     * 4个号码组合
     */
    public static List<String> FOUR_NUMBER_COMBINE = new ArrayList<>();

    /**
     * 5个号码组合
     */
    public static List<String> FIVE_NUMBER_COMBINE = new ArrayList<>();

    static {
        initOneNumberCombine();
        initTwoNumberCombine();
        initThreeNumberCombine();
        initFourNumberCombine();
        initFiveNumberCombine();
    }

    public static void initOneNumberCombine() {
        numberCombine = new String[] {EMPTY_NUMBER};
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, ONE_NUMBER_COMBINE);
        print("One", ONE_NUMBER_COMBINE);
    }

    public static void initTwoNumberCombine() {
        numberCombine = new String[] {EMPTY_NUMBER, EMPTY_NUMBER};
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, TWO_NUMBER_COMBINE);
        print("Two", TWO_NUMBER_COMBINE);
    }

    public static void initThreeNumberCombine() {
        numberCombine = new String[] {EMPTY_NUMBER, EMPTY_NUMBER, EMPTY_NUMBER};
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, THREE_NUMBER_COMBINE);
        print("Three", THREE_NUMBER_COMBINE);
    }

    public static void initFourNumberCombine() {
        numberCombine = new String[] {EMPTY_NUMBER, EMPTY_NUMBER, EMPTY_NUMBER, EMPTY_NUMBER};
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, FOUR_NUMBER_COMBINE);
        print("Four", FOUR_NUMBER_COMBINE);
    }

    public static void initFiveNumberCombine() {
        numberCombine = new String[] {EMPTY_NUMBER, EMPTY_NUMBER, EMPTY_NUMBER, EMPTY_NUMBER, EMPTY_NUMBER};
        combine(numberCombine.length, NumberConstant.SOURCE_NUMBER_ARR.length, FIVE_NUMBER_COMBINE);
        print("Five", FIVE_NUMBER_COMBINE);
    }

    private static void combine(int m, int n, List<String> targetList) {
        int i = 0;
        for (i = n; i >= m; i--) {
            numberCombine[m - 1] = NumberConstant.SOURCE_NUMBER_ARR[i - 1];
            if (m > 1) {
                combine(m - 1, i - 1, targetList);

            } else {
                targetList.add(Arrays.toString(numberCombine));
            }
        }
    }

    private static void print(String type, List<String> targetList) {
        System.out.println(type + ": ");
        for (int i = 0 ; i < targetList.size(); i++) {
            System.out.println((i+1) + ": " + targetList.get(i));
        }
    }

    public static void main(String[] args) {


    }

}
