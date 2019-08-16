package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 玩法类别(PlayCategory)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("PlayCategory")
public class PlayCategory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 268706739101650341L;
    // 名称
    private String name;
    // 代码
    private String code;
    // 单注号码个数
    private Integer singleCount;
    // 单注奖金
    private Double singleAmount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSingleCount() {
        return singleCount;
    }

    public void setSingleCount(Integer singleCount) {
        this.singleCount = singleCount;
    }

    public Double getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Double singleAmount) {
        this.singleAmount = singleAmount;
    }

}