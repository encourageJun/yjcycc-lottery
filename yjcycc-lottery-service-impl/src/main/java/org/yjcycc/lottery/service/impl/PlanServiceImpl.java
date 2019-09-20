package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.Plan;
import org.yjcycc.lottery.entity.PlanConfig;
import org.yjcycc.lottery.mapper.PlanMapper;
import org.yjcycc.lottery.service.IPlanService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 投注计划(Plan)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("planService")
public class PlanServiceImpl extends BaseServiceImpl<Plan> implements IPlanService {

    @Resource
    private PlanMapper planMapper;
    
    @Resource
    private void initMapper(PlanMapper planMapper) {
        this.setBaseMapper(planMapper);
    }

    public List<Plan> getPlanList(Long planConfigId, Integer planStatus, String lotteryType) {
        Plan planParam = new Plan();
        planParam.setPlanConfigId(planConfigId);
        planParam.setStatus(planStatus); // PlanStatus.status_0.getValue()
        planParam.setDictLotteryType(lotteryType);
        return planMapper.findList(planParam);
    }

}