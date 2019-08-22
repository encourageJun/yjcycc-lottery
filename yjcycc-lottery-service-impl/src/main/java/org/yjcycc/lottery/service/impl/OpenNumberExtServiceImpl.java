package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.OpenNumberExt;
import org.yjcycc.lottery.mapper.OpenNumberExtMapper;
import org.yjcycc.lottery.service.IOpenNumberExtService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 开奖号码(OpenNumberExt)表服务实现类
 *
 * @author makejava
 * @since 2019-08-18 04:45:30
 */
@Service("openNumberExtService")
public class OpenNumberExtServiceImpl extends BaseServiceImpl<OpenNumberExt> implements IOpenNumberExtService {

    @Resource
    private OpenNumberExtMapper openNumberExtMapper;
    
    @Resource
    private void initMapper(OpenNumberExtMapper openNumberExtMapper) {
        this.setBaseMapper(openNumberExtMapper);
    }
}