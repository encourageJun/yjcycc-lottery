package org.yjcycc.lottery.constant.dict;

public enum DoubleScheme {

    // 倍投方案
    // 数据字典:double_scheme
    // 1 盈利率 2 倍数
    
    scheme_1(1, "盈利率"),
    scheme_2(2, "倍数"),
    ;

    DoubleScheme(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;

    private String name;

    public int getValue() {
        return this.value;
    }

}
