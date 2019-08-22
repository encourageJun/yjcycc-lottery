package org.yjcycc.lottery.service;

import org.yjcycc.lottery.entity.OpenNumber;
import org.yjcycc.lottery.service.vo.OpenVO;
import org.yjcycc.tools.common.service.BaseService;

import java.rmi.RemoteException;

/**
 * 开奖号码(OpenNumber)表服务接口
 *
 * @author makejava
 * @since 2019-08-16 18:52:07
 */
public interface IOpenNumberService extends BaseService<OpenNumber> {

    void open(OpenVO openVO) throws RemoteException;

}