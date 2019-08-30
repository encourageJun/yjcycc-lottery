package org.yjcycc.lottery.mapper;

import org.apache.ibatis.annotations.Param;
import org.yjcycc.lottery.entity.Plan;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

import java.util.List;

/**
 * 投注计划(Plan)表数据库访问层
 *
 * @author makejava
 * @since 2019-08-16 18:47:08
 */
public interface PlanMapper extends MyBatisBaseMapper<Plan> {

    List<Plan> getByPlanConfigId(@Param("planConfigId") Long planConfigId);

    void insertFromCopy(@Param("planConfigId") Long planConfigId);

}