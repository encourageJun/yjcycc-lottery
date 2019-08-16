package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.PlayCategory;
import org.yjcycc.lottery.mapper.PlayCategoryMapper;
import org.yjcycc.lottery.service.IPlayCategoryService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 玩法类别(PlayCategory)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("playCategoryService")
public class PlayCategoryServiceImpl extends BaseServiceImpl<PlayCategory> implements IPlayCategoryService {

    @Resource
    private PlayCategoryMapper playCategoryMapper;
    
    @Resource
    private void initMapper(PlayCategoryMapper playCategoryMapper) {
        this.setBaseMapper(playCategoryMapper);
    }
}