package org.yjcycc.lottery.constant.dict;

public enum BalanceType {

    // 金额变动类型, 字典: balance_type, 1 投注 2 中奖
    BALANCE_TYPE_1(1, "投注"),
    BALANCE_TYPE_2(2, "中奖"),
    ;

    BalanceType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static String getNameByValue(int value) {
        for (BalanceType dict : BalanceType.values()) {
            if (dict.getValue() == value) {
                return dict.getName();
            }
        }
        return null;
    }


}
