package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 用户余额(UserBalance)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("UserBalance")
public class UserBalance extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -60597693697096967L;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 余额
    private Double balance;


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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}