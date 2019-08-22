package org.yjcycc.lottery.model;

import org.apache.log4j.Logger;
import org.yjcycc.lottery.analysis.order.scheme.Chooser;
import org.yjcycc.lottery.analysis.order.scheme.DoubleCountCalculator;
import org.yjcycc.lottery.analysis.vo.DoubleCountSchemeVO;
import org.yjcycc.lottery.analysis.vo.OrderNumberVO;
import org.yjcycc.lottery.constant.dict.ChooseScheme;
import org.yjcycc.lottery.constant.dict.PursueSchemeDict;
import org.yjcycc.lottery.constant.parameter.ParameterCode;
import org.yjcycc.lottery.entity.Plan;
import org.yjcycc.lottery.entity.PlanConfig;
import org.yjcycc.lottery.entity.PursueScheme;
import org.yjcycc.lottery.mapper.SysParameterMapper;
import org.yjcycc.lottery.utils.StringUtils;

import java.util.List;

public class OrderModel {

    private Logger logger = Logger.getLogger(OrderModel.class);

    private Plan plan;

    private PlanConfig planConfig;

    private SysParameterMapper sysParameterMapper;

    public PursueScheme getPursueScheme() {
        List<PursueScheme> pursueList = planConfig.getPursueList();
        if (pursueList == null || pursueList.isEmpty()) {
            logger.warn("未获取到投注计划配置中的追号方案!");
            return null;
        }
        PursueScheme pursueScheme = null;
        int totalStageCount = 0;
        for (PursueScheme pursue : pursueList) {
            if (pursue.getStageCountStart() <= plan.getStageCount() && pursue.getStageCountEnd() >= plan.getStageCount()) {
                pursueScheme = pursue;
                if (pursue.getDictPursueScheme() == PursueSchemeDict.scheme_4.getValue()) {
                    if (plan.getStageCount() % 2 == 1) {
                        for (PursueScheme pur : pursueList) {
                            if (pur.getDictPursueScheme() == PursueSchemeDict.scheme_2.getValue()) {
                                pursueScheme = pur;
                            }
                        }
                    } else {
                        for (PursueScheme pur : pursueList) {
                            if (pur.getDictPursueScheme() == PursueSchemeDict.scheme_3.getValue()) {
                                pursueScheme = pur;
                            }
                        }
                    }
                }
            }
            totalStageCount = totalStageCount + pursue.getStageCount();
        }
        plan.setTotalStageCount(totalStageCount);
        return pursueScheme;
    }

    public List<OrderNumberVO> getOrderNumber() {
        Integer chooseScheme = planConfig.getDictChooseScheme();
        String danNumber = null;
        String tuoNumber = null;
        if (ChooseScheme.scheme_1.getValue() == chooseScheme) {
            danNumber = planConfig.getDanNumber();
            tuoNumber = planConfig.getTuoNumber();
        } else if (ChooseScheme.scheme_2.getValue() == chooseScheme) {
            // FIXME
            // 统计选码

        }
        if (StringUtils.isEmpty(danNumber) || StringUtils.isEmpty(tuoNumber)) {
            logger.warn("未获取到投注计划配置中的胆码或拖码!");
            return null;
        }
        PursueScheme pursueScheme = getPursueScheme();
        double profitRate = Double.parseDouble(sysParameterMapper.getValueByCode(ParameterCode.PROFIT_RATE));
        List<OrderNumberVO> orderNumberList = new Chooser().getOrderNumber(pursueScheme, danNumber, tuoNumber);
        for (OrderNumberVO vo : orderNumberList) {
            // 倍投方案(根据利润率计算倍数)
            DoubleCountSchemeVO doubleCountSchemeVO = new DoubleCountSchemeVO(planConfig.getDictDoubleScheme(), planConfig.getDictAmountModel(),
                    pursueScheme.getPlayCategory(), profitRate, plan.getTotalAmount(), vo.getOrderNumber());
            doubleCountSchemeVO = new DoubleCountCalculator().calculate(doubleCountSchemeVO);
            vo.setDoubleCount(doubleCountSchemeVO.getDoubleCount());
        }
        return orderNumberList;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public PlanConfig getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(PlanConfig planConfig) {
        this.planConfig = planConfig;
    }

    public SysParameterMapper getSysParameterMapper() {
        return sysParameterMapper;
    }

    public void setSysParameterMapper(SysParameterMapper sysParameterMapper) {
        this.sysParameterMapper = sysParameterMapper;
    }
}
