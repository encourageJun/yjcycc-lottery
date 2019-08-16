package org.yjcycc.lottery.service.impl;

import org.yjcycc.lottery.entity.UserBalance;
import org.yjcycc.lottery.mapper.UserBalanceMapper;
import org.yjcycc.lottery.service.IUserBalanceService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户余额(UserBalance)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("userBalanceService")
public class UserBalanceServiceImpl extends BaseServiceImpl<UserBalance> implements IUserBalanceService {

    @Resource
    private UserBalanceMapper userBalanceMapper;
    
    @Resource
    private void initMapper(UserBalanceMapper userBalanceMapper) {
        this.setBaseMapper(userBalanceMapper);
    }
}