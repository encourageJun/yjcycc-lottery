package org.yjcycc.lottery.constant.dict;

public enum PursueSchemeDict {
    
    // 追号方案
    // 数据字典:pursue_scheme
    // 1 多组合-胆杀 2 杀号 3 胆拖 4 杀号与胆拖交替

    scheme_1(1, "多组合-胆杀"),
    scheme_2(2, "杀号"),
    scheme_3(3, "胆拖"),
    scheme_4(4, "杀号与胆拖交替"),
    ;

    private int value;
    private String name;

    PursueSchemeDict(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public static PursueSchemeDict getByValue(int value) {
        for (PursueSchemeDict dict : PursueSchemeDict.values()) {
            if (dict.getValue() == value) {
                return dict;
            }
        }
        return null;
    }

}
