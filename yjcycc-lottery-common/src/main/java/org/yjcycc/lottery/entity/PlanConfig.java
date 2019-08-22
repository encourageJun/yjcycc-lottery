package org.yjcycc.lottery.entity;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 投注计划配置(PlanConfig)实体类
 *
 * @author yangjun
 * @since 2019-08-22 16:20:27
 */
@Alias("PlanConfig")
public class PlanConfig extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 443241460619232599L;
    // 金额模式, 字典:amount_model, 1元 10角 100分 1000厘
    private Integer dictAmountModel;
    // 倍投方案, 数据字典:double_scheme, 1 盈利率 2 单倍
    private Integer dictDoubleScheme;
    // 倍数
    private Integer doubleCount;
    // 选号方案, 数据字典:choose_scheme, 1 手动设置胆码-拖码 2 统计选胆码-随机选拖码
    private Integer dictChooseScheme;
    // 追号方案id
    private Long pursueId;
    // 胆码
    private String danNumber;
    // 拖码
    private String tuoNumber;
    // 占用状态, 0禁用 1否 2是
    private Integer isOccupy;

    private List<PursueScheme> pursueList;


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

    public Long getPursueId() {
        return pursueId;
    }

    public void setPursueId(Long pursueId) {
        this.pursueId = pursueId;
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

    public List<PursueScheme> getPursueList() {
        return pursueList;
    }

    public void setPursueList(List<PursueScheme> pursueList) {
        this.pursueList = pursueList;
    }
}