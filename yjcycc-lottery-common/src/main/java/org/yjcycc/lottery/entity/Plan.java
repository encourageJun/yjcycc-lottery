package org.yjcycc.lottery.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 投注计划(Plan)实体类
 *
 * @author yangjun
 * @since 2019-08-23 18:26:34
 */
@Alias("Plan")
public class Plan extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -22781716297625028L;
    // 投注计划配置id
    private Long planConfigId;
    // 彩票种类(字典标签label)
    private String dictLotteryType;
    // 中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖
    private Integer status;
    // 当前投注期数
    private Integer stageCount;
    // 总期数
    private Integer totalStageCount;
    // 当期投注金额
    private BigDecimal amount;
    // 当期投注倍数
    private Integer doubleCount;
    // 累积投注金额
    private BigDecimal totalAmount;
    // 中奖金额
    private BigDecimal winAmount;
    // 盈利金额
    private BigDecimal profitAmount;
    // 计划配置执行序号, 例如: 0,1,2,3..., 持续累加, 已有计划数据的情况下不可清零.(来自计划配置的executeIndex)
    private Integer executeIndex;

    private PlanConfig planConfig;


    public Long getPlanConfigId() {
        return planConfigId;
    }

    public void setPlanConfigId(Long planConfigId) {
        this.planConfigId = planConfigId;
    }

    public String getDictLotteryType() {
        return dictLotteryType;
    }

    public void setDictLotteryType(String dictLotteryType) {
        this.dictLotteryType = dictLotteryType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStageCount() {
        return stageCount;
    }

    public void setStageCount(Integer stageCount) {
        this.stageCount = stageCount;
    }

    public Integer getTotalStageCount() {
        return totalStageCount;
    }

    public void setTotalStageCount(Integer totalStageCount) {
        this.totalStageCount = totalStageCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(Integer doubleCount) {
        this.doubleCount = doubleCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }

    public PlanConfig getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(PlanConfig planConfig) {
        this.planConfig = planConfig;
    }

    public Integer getExecuteIndex() {
        return executeIndex;
    }

    public void setExecuteIndex(Integer executeIndex) {
        this.executeIndex = executeIndex;
    }
}