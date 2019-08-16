package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 投注单(Order)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("Order")
public class Order extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 603031587877434766L;
    // 投注计划id
    private Long planId;
    // 玩法种类, bs_play_category
    private Long playCategoryId;
    // 中奖状态, 字典:order_status, 0开奖中 1未中奖 2已中奖
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
    // 投注组合号码
    private String numberArr;
    // 投注金额
    private Double amount;
    // 单注金额
    private Double singleAmount;
    // 注数
    private Integer count;
    // 倍数
    private Integer doubleCount;


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

    public String getNumberArr() {
        return numberArr;
    }

    public void setNumberArr(String numberArr) {
        this.numberArr = numberArr;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Double singleAmount) {
        this.singleAmount = singleAmount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(Integer doubleCount) {
        this.doubleCount = doubleCount;
    }

}