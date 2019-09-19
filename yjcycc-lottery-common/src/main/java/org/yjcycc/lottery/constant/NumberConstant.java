package org.yjcycc.lottery.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NumberConstant {

    public final static String SOURCE_NUMBER = "01,02,03,04,05,06,07,08,09,10,11";

    public static String[] SOURCE_NUMBER_ARR = {"01","02","03","04","05","06","07","08","09","10","11"};


    public final static String NUMBER_01 = "01";
    public final static String NUMBER_02 = "02";
    public final static String NUMBER_03 = "03";
    public final static String NUMBER_04 = "04";
    public final static String NUMBER_05 = "05";
    public final static String NUMBER_06 = "06";
    public final static String NUMBER_07 = "07";
    public final static String NUMBER_08 = "08";
    public final static String NUMBER_09 = "09";
    public final static String NUMBER_10 = "10";
    public final static String NUMBER_11 = "11";

    public final static BigDecimal BASE_AMOUNT = new BigDecimal(2);


    public static List<String> SOURCE_NUMBER_LIST = new ArrayList<>();

    static {
        SOURCE_NUMBER_LIST.add(NUMBER_01);
        SOURCE_NUMBER_LIST.add(NUMBER_02);
        SOURCE_NUMBER_LIST.add(NUMBER_03);
        SOURCE_NUMBER_LIST.add(NUMBER_04);
        SOURCE_NUMBER_LIST.add(NUMBER_05);
        SOURCE_NUMBER_LIST.add(NUMBER_06);
        SOURCE_NUMBER_LIST.add(NUMBER_07);
        SOURCE_NUMBER_LIST.add(NUMBER_08);
        SOURCE_NUMBER_LIST.add(NUMBER_09);
        SOURCE_NUMBER_LIST.add(NUMBER_10);
        SOURCE_NUMBER_LIST.add(NUMBER_11);
    }

}
