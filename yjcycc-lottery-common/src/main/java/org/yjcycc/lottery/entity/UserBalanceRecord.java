package org.yjcycc.lottery.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 余额变动(UserBalanceRecord)实体类
 *
 * @author yangjun
 * @since 2019-08-19 17:21:10
 */
@Alias("UserBalanceRecord")
public class UserBalanceRecord extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -85377237905512911L;
    // 投注单id
    private Long orderId;
    // 金额变动类型, 字典: balance_type, 1 投注 2 中奖
    private Integer type;
    // 变动金额
    private BigDecimal amount;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}