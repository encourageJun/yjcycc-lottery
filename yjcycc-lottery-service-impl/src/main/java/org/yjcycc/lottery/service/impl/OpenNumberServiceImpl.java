package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.OpenNumber;
import org.yjcycc.lottery.mapper.OpenNumberMapper;
import org.yjcycc.lottery.service.IOpenNumberService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 开奖号码(OpenNumber)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("openNumberService")
public class OpenNumberServiceImpl extends BaseServiceImpl<OpenNumber> implements IOpenNumberService {

    @Resource
    private OpenNumberMapper openNumberMapper;
    
    @Resource
    private void initMapper(OpenNumberMapper openNumberMapper) {
        this.setBaseMapper(openNumberMapper);
    }
}