package org.yjcycc.lottery.constant.dict;

public enum AmountModel {
    // 金额模式
    // 字典:amount_model
    // 1元 10角 100分 1000厘

    yuan(1, "元"),
    jiao(10, "角"),
    fen(100, "分"),
    li(1000, "厘"),
    ;

    AmountModel(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return this.value;
    }


}
