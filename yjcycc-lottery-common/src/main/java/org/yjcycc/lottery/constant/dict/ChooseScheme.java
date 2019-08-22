package org.yjcycc.lottery.constant.dict;

public enum ChooseScheme {

    // 选号方案
    // 数据字典:choose_scheme
    // 1 手动设置胆码-拖码 2 统计选胆码-随机选拖码

    scheme_1(1, "手动设置胆码-拖码"),
    scheme_2(2, "统计选胆码-随机选拖码"),
    ;

    private int value;
    private String name;

    ChooseScheme(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

}
