package org.yjcycc.lottery.constant.dict;

import org.apache.commons.lang.StringUtils;

/**
 * 11选5类别
 */
public enum LotteryType {

    TYPE_3_MINUTES(441, "f3_11x5", "三分11选5"),
    TYPE_1_MINUTES(1321, "f1_11x5", "一分11选5")
    ;

    LotteryType(int value, String label, String name) {
        this.value = value;
        this.label = label;
        this.name = name;
    }

    private int value;
    private String label;
    private String name;

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static LotteryType getByLabel(String label) {
        if (StringUtils.isEmpty(label)) {
            return null;
        }
        for (LotteryType lotteryType : LotteryType.values()) {
            if (lotteryType.getLabel().equals(label)) {
                return lotteryType;
            }
        }
        return null;
    }


}
