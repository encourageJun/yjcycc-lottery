package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 投注计划(Plan)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("Plan")
public class Plan extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 905281132558275262L;
    // 投注计划配置id
    private Long planConfigId;
    // 中奖状态, 字典:plan_status, 0未中奖 1已中奖
    private Integer status;
    // 累积金额
    private Double totalAmount;


    public Long getPlanConfigId() {
        return planConfigId;
    }

    public void setPlanConfigId(Long planConfigId) {
        this.planConfigId = planConfigId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

}