package org.yjcycc.lottery.mapper;

import org.apache.ibatis.annotations.Param;
import org.yjcycc.lottery.entity.OpenNumber;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

/**
 * 开奖号码(OpenNumber)表数据库访问层
 *
 * @author makejava
 * @since 2019-08-16 18:47:08
 */
public interface OpenNumberMapper extends MyBatisBaseMapper<OpenNumber> {

    OpenNumber getLastOpen(@Param("lotteryType") String lotteryType);

}