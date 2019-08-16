package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 余额变动(UserBalanceRecord)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("UserBalanceRecord")
public class UserBalanceRecord extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -16789401280605527L;
    // 金额变动类型, 字典: balance_type, 1 投注 2 中奖
    private Integer type;
    // 变动金额
    private Double amount;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}