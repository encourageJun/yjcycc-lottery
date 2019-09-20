package org.yjcycc.lottery.analysis.vo;

import org.yjcycc.lottery.entity.PlayCategory;

public class OrderNumberVO {

    // 投注号码
    private String orderNumber;

    // 原始号码
    private String sourceNumber;

    // 投注注数
    private int orderCombineSize;

    // 玩法种类
    private PlayCategory playCategory;

    // 倍数
    private int doubleCount;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(String sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public int getOrderCombineSize() {
        return orderCombineSize;
    }

    public void setOrderCombineSize(int orderCombineSize) {
        this.orderCombineSize = orderCombineSize;
    }

    public PlayCategory getPlayCategory() {
        return playCategory;
    }

    public void setPlayCategory(PlayCategory playCategory) {
        this.playCategory = playCategory;
    }

    public int getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(int doubleCount) {
        this.doubleCount = doubleCount;
    }
}
