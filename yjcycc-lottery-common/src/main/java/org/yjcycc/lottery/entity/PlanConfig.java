package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 投注计划配置(PlanConfig)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("PlanConfig")
public class PlanConfig extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -79856617396494258L;
    // 玩法类别id
    private Long playCategoryId;
    // 倍投方案, 数据字典:double_scheme, 1 盈利率 2 单倍
    private Integer dictDoubleScheme;
    // 选号方案, 数据字典:number_scheme, 1 手动设置胆码-拖码 2 统计选胆码-随机选拖码
    private Integer dictNumberScheme;
    // 追号方案, 数据字典:pursue_scheme, 1 多组合-胆杀 2 杀号 3 胆拖 4 杀号与胆拖交替
    private Integer dictPursueScheme;
    // 金额模式, 字典:amount_model, 1元 10角 100分 1000厘
    private Integer dictAmountModel;
    // 占用状态, 0禁用 1否 2是
    private Integer isOccupy;


    public Long getPlayCategoryId() {
        return playCategoryId;
    }

    public void setPlayCategoryId(Long playCategoryId) {
        this.playCategoryId = playCategoryId;
    }

    public Integer getDictDoubleScheme() {
        return dictDoubleScheme;
    }

    public void setDictDoubleScheme(Integer dictDoubleScheme) {
        this.dictDoubleScheme = dictDoubleScheme;
    }

    public Integer getDictNumberScheme() {
        return dictNumberScheme;
    }

    public void setDictNumberScheme(Integer dictNumberScheme) {
        this.dictNumberScheme = dictNumberScheme;
    }

    public Integer getDictPursueScheme() {
        return dictPursueScheme;
    }

    public void setDictPursueScheme(Integer dictPursueScheme) {
        this.dictPursueScheme = dictPursueScheme;
    }

    public Integer getDictAmountModel() {
        return dictAmountModel;
    }

    public void setDictAmountModel(Integer dictAmountModel) {
        this.dictAmountModel = dictAmountModel;
    }

    public Integer getIsOccupy() {
        return isOccupy;
    }

    public void setIsOccupy(Integer isOccupy) {
        this.isOccupy = isOccupy;
    }

}