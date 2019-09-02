package org.yjcycc.lottery.entity;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 投注计划配置(PlanConfig)实体类
 *
 * @author yangjun
 * @since 2019-08-30 10:29:03
 */
@Alias("PlanConfig")
public class PlanConfig extends BaseEntity {
    private static final long serialVersionUID = 299471688905797715L;
    // 金额模式, 字典:amount_model, 1元 10角 100分 1000厘
    private Integer dictAmountModel;
    // 倍投方案, 数据字典:double_scheme, 1 盈利率 2 单倍
    private Integer dictDoubleScheme;
    // 彩票种类(字典标签label)
    private String dictLotteryType;
    // 倍数
    private Integer doubleCount;
    // 选号方案, 数据字典:choose_scheme, 1 手动设置胆码-拖码 2 统计选胆码-随机选拖码
    private Integer dictChooseScheme;
    // 胆码
    private String danNumber;
    // 拖码
    private String tuoNumber;
    // 占用状态, 0禁用 1否 2是
    private Integer isOccupy;
    // 提现盈利率, 达到盈利率后提现
    private Double cashOutRate;
    // 计划配置执行序号, 例如: 0,1,2,3...
    private Integer executeIndex;
    // 充值金额
    private BigDecimal recharge;
    // 余额变动
    private BigDecimal balance;
    // 累积盈利
    private BigDecimal totalProfitAmount;

    private List<PursueScheme> pursueList;
    private List<Plan> planList;

    public Integer getDictAmountModel() {
        return dictAmountModel;
    }

    public void setDictAmountModel(Integer dictAmountModel) {
        this.dictAmountModel = dictAmountModel;
    }

    public Integer getDictDoubleScheme() {
        return dictDoubleScheme;
    }

    public void setDictDoubleScheme(Integer dictDoubleScheme) {
        this.dictDoubleScheme = dictDoubleScheme;
    }

    public String getDictLotteryType() {
        return dictLotteryType;
    }

    public void setDictLotteryType(String dictLotteryType) {
        this.dictLotteryType = dictLotteryType;
    }

    public Integer getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(Integer doubleCount) {
        this.doubleCount = doubleCount;
    }

    public Integer getDictChooseScheme() {
        return dictChooseScheme;
    }

    public void setDictChooseScheme(Integer dictChooseScheme) {
        this.dictChooseScheme = dictChooseScheme;
    }

    public String getDanNumber() {
        return danNumber;
    }

    public void setDanNumber(String danNumber) {
        this.danNumber = danNumber;
    }

    public String getTuoNumber() {
        return tuoNumber;
    }

    public void setTuoNumber(String tuoNumber) {
        this.tuoNumber = tuoNumber;
    }

    public Integer getIsOccupy() {
        return isOccupy;
    }

    public void setIsOccupy(Integer isOccupy) {
        this.isOccupy = isOccupy;
    }

    public Double getCashOutRate() {
        return cashOutRate;
    }

    public void setCashOutRate(Double cashOutRate) {
        this.cashOutRate = cashOutRate;
    }

    public Integer getExecuteIndex() {
        return executeIndex;
    }

    public void setExecuteIndex(Integer executeIndex) {
        this.executeIndex = executeIndex;
    }

    public BigDecimal getRecharge() {
        return recharge;
    }

    public void setRecharge(BigDecimal recharge) {
        this.recharge = recharge;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalProfitAmount() {
        return totalProfitAmount;
    }

    public void setTotalProfitAmount(BigDecimal totalProfitAmount) {
        this.totalProfitAmount = totalProfitAmount;
    }

    public List<PursueScheme> getPursueList() {
        return pursueList;
    }

    public void setPursueList(List<PursueScheme> pursueList) {
        this.pursueList = pursueList;
    }

    public List<Plan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }
}