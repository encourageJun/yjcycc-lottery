package org.yjcycc.lottery.service;

import org.yjcycc.lottery.entity.PlanConfig;
import org.yjcycc.tools.common.service.BaseService;

import java.util.List;

/**
 * 投注计划配置(PlanConfig)表服务接口
 *
 * @author makejava
 * @since 2019-08-16 18:52:07
 */
public interface IPlanConfigService extends BaseService<PlanConfig> {

    List<PlanConfig> getPlanConfigList(String inIsOccupys, String lotteryType) throws Exception;

}