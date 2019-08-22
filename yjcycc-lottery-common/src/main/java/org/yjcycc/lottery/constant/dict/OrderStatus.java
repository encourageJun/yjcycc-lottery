package org.yjcycc.lottery.constant.dict;

public enum OrderStatus {

    // 投注单-中奖状态
    // 字典:order_status
    // 0开奖中 1未中奖 2已中奖

    status_0(0, "开奖中"),
    status_1(1, "未中奖"),
    status_2(2, "已中奖"),
    ;

    private int value;
    private String name;

    OrderStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

}
