package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.SysDict;
import org.yjcycc.lottery.mapper.SysDictMapper;
import org.yjcycc.lottery.service.ISysDictService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 数据字典(SysDict)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("sysDictService")
public class SysDictServiceImpl extends BaseServiceImpl<SysDict> implements ISysDictService {

    @Resource
    private SysDictMapper sysDictMapper;
    
    @Resource
    private void initMapper(SysDictMapper sysDictMapper) {
        this.setBaseMapper(sysDictMapper);
    }
}