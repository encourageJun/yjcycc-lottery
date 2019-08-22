package org.yjcycc.lottery.constant.dict;

public enum PlanStatus {

    // 中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖

    status_0(0, "追号中"),
    status_1(1, "未中奖"),
    status_2(2, "已中奖"),
    ;

    PlanStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return this.value;
    }

}
