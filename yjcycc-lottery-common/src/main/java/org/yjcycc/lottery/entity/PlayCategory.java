package org.yjcycc.lottery.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 玩法类别(PlayCategory)实体类
 *
 * @author yangjun
 * @since 2019-08-22 15:06:36
 */
@Alias("PlayCategory")
public class PlayCategory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 900647267619528559L;
    // 名称
    private String name;
    // 代码
    private String code;
    // 玩法类型, 数据字典:play_type, 1复式 2胆拖 3单式
    private Integer dictPlayType;
    // 反射类名
    private String className;
    // 单注号码个数
    private Integer singleCount;
    // 单注奖金
    private BigDecimal singleAmount;


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

    public Integer getDictPlayType() {
        return dictPlayType;
    }

    public void setDictPlayType(Integer dictPlayType) {
        this.dictPlayType = dictPlayType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getSingleCount() {
        return singleCount;
    }

    public void setSingleCount(Integer singleCount) {
        this.singleCount = singleCount;
    }

    public BigDecimal getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(BigDecimal singleAmount) {
        this.singleAmount = singleAmount;
    }

}