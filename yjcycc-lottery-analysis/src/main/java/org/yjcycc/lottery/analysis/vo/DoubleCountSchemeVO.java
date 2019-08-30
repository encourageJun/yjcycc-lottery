package org.yjcycc.lottery.analysis.vo;

import org.yjcycc.lottery.entity.PlayCategory;

import java.math.BigDecimal;

public class DoubleCountSchemeVO {

    // 以构造方法参数形式传入
    private int dictDoubleScheme; // 倍数方案, 数据字典值
    private int dictAmountModel; // 金额模式, 数据字典值
    private PlayCategory playCategory; // 玩法种类
    private double profitRate; // 盈利率
    private BigDecimal totalOrderAmount; // 往期累积投注金额
    private String orderNumber; // 投注号码

    // 计算后填充值
    private int doubleCount = 1; // 倍数, 默认初始倍数为1

    public DoubleCountSchemeVO() {}

    public DoubleCountSchemeVO(int dictDoubleScheme, int dictAmountModel, PlayCategory playCategory,
                               double profitRate, BigDecimal totalOrderAmount, String orderNumber, int initDoubleCount) {
        this.dictDoubleScheme = dictDoubleScheme;
        this.dictAmountModel = dictAmountModel;
        this.playCategory = playCategory;
        this.profitRate = profitRate;
        if (totalOrderAmount == null) {
            this.totalOrderAmount = BigDecimal.ZERO;
        } else {
            this.totalOrderAmount = totalOrderAmount;
        }
        this.orderNumber = orderNumber;
        this.doubleCount = initDoubleCount;
    }

    public int getDictDoubleScheme() {
        return dictDoubleScheme;
    }

    public void setDictDoubleScheme(int dictDoubleScheme) {
        this.dictDoubleScheme = dictDoubleScheme;
    }

    public int getDictAmountModel() {
        return dictAmountModel;
    }

    public void setDictAmountModel(int dictAmountModel) {
        this.dictAmountModel = dictAmountModel;
    }

    public PlayCategory getPlayCategory() {
        return playCategory;
    }

    public void setPlayCategory(PlayCategory playCategory) {
        this.playCategory = playCategory;
    }

    public double getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(double profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public int getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(int doubleCount) {
        this.doubleCount = doubleCount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
