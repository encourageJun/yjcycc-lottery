package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.SysParameter;
import org.yjcycc.lottery.mapper.SysParameterMapper;
import org.yjcycc.lottery.service.ISysParameterService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统参数(SysParameter)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("sysParameterService")
public class SysParameterServiceImpl extends BaseServiceImpl<SysParameter> implements ISysParameterService {

    @Resource
    private SysParameterMapper sysParameterMapper;
    
    @Resource
    private void initMapper(SysParameterMapper sysParameterMapper) {
        this.setBaseMapper(sysParameterMapper);
    }
}