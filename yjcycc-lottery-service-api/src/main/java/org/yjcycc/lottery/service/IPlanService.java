package org.yjcycc.lottery.service;

import org.yjcycc.lottery.entity.Plan;
import org.yjcycc.tools.common.service.BaseService;

import java.util.List;

/**
 * 投注计划(Plan)表服务接口
 *
 * @author makejava
 * @since 2019-08-16 18:52:07
 */
public interface IPlanService extends BaseService<Plan> {

    List<Plan> getPlanList(Long planConfigId, Integer planStatus, String lotteryType) throws Exception;

}