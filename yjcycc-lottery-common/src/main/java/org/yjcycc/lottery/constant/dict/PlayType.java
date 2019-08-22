package org.yjcycc.lottery.constant.dict;

public enum PlayType {

    // 玩法类型, 数据字典:play_type, 1复式 2胆拖 3单式 4直选
    PLAY_TYPE_1(1, "复式/组选"),
    PLAY_TYPE_2(2, "胆拖"),
    PLAY_TYPE_3(3, "单式"),
    PLAY_TYPE_4(4, "直选"),
    ;

    PlayType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return this.value;
    }

    public static PlayType getByValue(int value) {
        for (PlayType playType : PlayType.values()) {
            if (value == playType.getValue()) {
                return playType;
            }
        }
        return null;
    }

}
