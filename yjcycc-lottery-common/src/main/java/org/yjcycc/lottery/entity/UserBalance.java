package org.yjcycc.lottery.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 用户余额(UserBalance)实体类
 *
 * @author yangjun
 * @since 2019-08-19 17:18:45
 */
@Alias("UserBalance")
public class UserBalance extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 631577411794653047L;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 余额
    private BigDecimal balance;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}