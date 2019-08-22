package org.yjcycc.lottery.mapper;

import org.apache.ibatis.annotations.Param;
import org.yjcycc.lottery.entity.PursueScheme;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

import java.util.List;

/**
 * 追号方案(PursueScheme)表数据库访问层
 *
 * @author makejava
 * @since 2019-08-19 11:59:10
 */
public interface PursueSchemeMapper extends MyBatisBaseMapper<PursueScheme> {

    List<PursueScheme> getByPlanConfigId(@Param("planConfigId") Long planConfigId);

}