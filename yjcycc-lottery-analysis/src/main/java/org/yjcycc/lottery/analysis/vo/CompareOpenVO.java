package org.yjcycc.lottery.analysis.vo;

import org.yjcycc.lottery.entity.PlayCategory;

import java.math.BigDecimal;

public class CompareOpenVO {
    // 以构造方法参数形式传入
    private PlayCategory playCategory; // 玩法类别
    private String orderNumber; // 投注号码
    private String openNumber; // 开奖号码, 直选传前二/前三号码

    // 计算后填充值
    private int orderCount; // 投注注数
    private BigDecimal orderAmount; // 当期单注投注金额
    private int winCount; // 中奖注数
    private BigDecimal winAmount; // 中奖金额

    public CompareOpenVO() {}

    public CompareOpenVO(PlayCategory playCategory, String orderNumber, String openNumber) {
        this.playCategory = playCategory;
        this.orderNumber = orderNumber;
        this.openNumber = openNumber;
    }

    public PlayCategory getPlayCategory() {
        return playCategory;
    }

    public void setPlayCategory(PlayCategory playCategory) {
        this.playCategory = playCategory;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOpenNumber() {
        return openNumber;
    }

    public void setOpenNumber(String openNumber) {
        this.openNumber = openNumber;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }
}
