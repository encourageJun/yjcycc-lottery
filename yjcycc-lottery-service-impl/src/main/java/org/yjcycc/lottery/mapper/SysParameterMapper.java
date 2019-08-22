package org.yjcycc.lottery.mapper;

import org.apache.ibatis.annotations.Param;
import org.yjcycc.lottery.entity.SysParameter;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

/**
 * 系统参数(SysParameter)表数据库访问层
 *
 * @author makejava
 * @since 2019-08-16 18:47:08
 */
public interface SysParameterMapper extends MyBatisBaseMapper<SysParameter> {

    String getValueByCode(@Param("code") String code);

}