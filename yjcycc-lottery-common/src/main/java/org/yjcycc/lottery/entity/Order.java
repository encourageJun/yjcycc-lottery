package org.yjcycc.lottery.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 投注单(Order)实体类
 *
 * @author makejava
 * @since 2019-08-18 08:54:47
 */
@Alias("Order")
public class Order extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -76683179330834974L;
    // 投注计划id
    private Long planId;
    // 玩法种类, bs_play_category
    private Long playCategoryId;
    // 11选5类别, 1:3分11选5 2:1分11选5
    private String lotteryType;
    // 中奖状态, 字典:order_status, 0待开奖 1未中奖 2已中奖
    private Integer status;
    // 金额模式, 字典:amount_model, 1元 10角 100分 1000厘
    private Integer dictAmountModel;
    // 投注单号
    private String orderNo;
    // 期号
    private String stage;
    // 期号-前缀日期
    private String stageDate;
    // 期号-后缀序号
    private String stageNum;
    // 期号-数字序号
    private Integer stageIndex;
    // 投注号码
    private String number;
    // 投注金额
    private BigDecimal amount;
    // 中奖金额
    private BigDecimal winAmount;
    // 中奖注数
    private Integer winCount;
    // 倍数
    private Integer doubleCount;
    // 计划配置执行序号, 例如: 0,1,2,3...
    private Integer executeIndex;

    private PlayCategory playCategory;

    private Plan plan;


    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlayCategoryId() {
        return playCategoryId;
    }

    public void setPlayCategoryId(Long playCategoryId) {
        this.playCategoryId = playCategoryId;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDictAmountModel() {
        return dictAmountModel;
    }

    public void setDictAmountModel(Integer dictAmountModel) {
        this.dictAmountModel = dictAmountModel;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStageDate() {
        return stageDate;
    }

    public void setStageDate(String stageDate) {
        this.stageDate = stageDate;
    }

    public String getStageNum() {
        return stageNum;
    }

    public void setStageNum(String stageNum) {
        this.stageNum = stageNum;
    }

    public Integer getStageIndex() {
        return stageIndex;
    }

    public void setStageIndex(Integer stageIndex) {
        this.stageIndex = stageIndex;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public Integer getWinCount() {
        return winCount;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    public Integer getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(Integer doubleCount) {
        this.doubleCount = doubleCount;
    }

    public PlayCategory getPlayCategory() {
        return playCategory;
    }

    public void setPlayCategory(PlayCategory playCategory) {
        this.playCategory = playCategory;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Integer getExecuteIndex() {
        return executeIndex;
    }

    public void setExecuteIndex(Integer executeIndex) {
        this.executeIndex = executeIndex;
    }
}