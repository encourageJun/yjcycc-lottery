package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.UserBalanceRecord;
import org.yjcycc.lottery.mapper.UserBalanceRecordMapper;
import org.yjcycc.lottery.service.IUserBalanceRecordService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 余额变动(UserBalanceRecord)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("userBalanceRecordService")
public class UserBalanceRecordServiceImpl extends BaseServiceImpl<UserBalanceRecord> implements IUserBalanceRecordService {

    @Resource
    private UserBalanceRecordMapper userBalanceRecordMapper;
    
    @Resource
    private void initMapper(UserBalanceRecordMapper userBalanceRecordMapper) {
        this.setBaseMapper(userBalanceRecordMapper);
    }
}