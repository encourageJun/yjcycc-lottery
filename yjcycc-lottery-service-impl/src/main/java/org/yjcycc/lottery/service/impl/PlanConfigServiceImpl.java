package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.PlanConfig;
import org.yjcycc.lottery.mapper.PlanConfigMapper;
import org.yjcycc.lottery.service.IPlanConfigService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 投注计划配置(PlanConfig)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("planConfigService")
public class PlanConfigServiceImpl extends BaseServiceImpl<PlanConfig> implements IPlanConfigService {

    @Resource
    private PlanConfigMapper planConfigMapper;
    
    @Resource
    private void initMapper(PlanConfigMapper planConfigMapper) {
        this.setBaseMapper(planConfigMapper);
    }
}