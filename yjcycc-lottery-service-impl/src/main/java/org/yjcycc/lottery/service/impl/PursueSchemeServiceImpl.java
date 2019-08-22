package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.PursueScheme;
import org.yjcycc.lottery.mapper.PursueSchemeMapper;
import org.yjcycc.lottery.service.IPursueSchemeService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 追号方案(PursueScheme)表服务实现类
 *
 * @author makejava
 * @since 2019-08-19 11:59:48
 */
@Service("pursueSchemeService")
public class PursueSchemeServiceImpl extends BaseServiceImpl<PursueScheme> implements IPursueSchemeService {

    @Resource
    private PursueSchemeMapper pursueSchemeMapper;
    
    @Resource
    private void initMapper(PursueSchemeMapper pursueSchemeMapper) {
        this.setBaseMapper(pursueSchemeMapper);
    }
}