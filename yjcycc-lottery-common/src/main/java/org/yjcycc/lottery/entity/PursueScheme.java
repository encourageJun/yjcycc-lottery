package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 追号方案(PursueScheme)实体类
 *
 * @author yangjun
 * @since 2019-08-20 11:18:04
 */
@Alias("PursueScheme")
public class PursueScheme extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 708482188322882996L;
    // 投注计划配置id
    private Long planConfigId;
    // 玩法种类id
    private Long playCategoryId;
    // 追号方案, 数据字典:pursue_scheme, 1 多组合-胆杀 2 杀号 3 胆拖 4 杀号与胆拖交替
    private Integer dictPursueScheme;
    // 期数范围-开始, 例:1
    private Integer stageCountStart;
    // 期数范围-结束, 例:5
    private Integer stageCountEnd;
    // 追号期数(例: 5期)
    private Integer stageCount;
    // 序号
    private Integer sort;

    private PlayCategory playCategory;


    public Long getPlanConfigId() {
        return planConfigId;
    }

    public void setPlanConfigId(Long planConfigId) {
        this.planConfigId = planConfigId;
    }

    public Long getPlayCategoryId() {
        return playCategoryId;
    }

    public void setPlayCategoryId(Long playCategoryId) {
        this.playCategoryId = playCategoryId;
    }

    public Integer getDictPursueScheme() {
        return dictPursueScheme;
    }

    public void setDictPursueScheme(Integer dictPursueScheme) {
        this.dictPursueScheme = dictPursueScheme;
    }

    public Integer getStageCountStart() {
        return stageCountStart;
    }

    public void setStageCountStart(Integer stageCountStart) {
        this.stageCountStart = stageCountStart;
    }

    public Integer getStageCountEnd() {
        return stageCountEnd;
    }

    public void setStageCountEnd(Integer stageCountEnd) {
        this.stageCountEnd = stageCountEnd;
    }

    public Integer getStageCount() {
        return stageCount;
    }

    public void setStageCount(Integer stageCount) {
        this.stageCount = stageCount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public PlayCategory getPlayCategory() {
        return playCategory;
    }

    public void setPlayCategory(PlayCategory playCategory) {
        this.playCategory = playCategory;
    }
}