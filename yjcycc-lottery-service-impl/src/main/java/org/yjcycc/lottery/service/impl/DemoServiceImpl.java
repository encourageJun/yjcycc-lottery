package org.yjcycc.lottery.service.impl;

import org.springframework.stereotype.Service;
import org.yjcycc.lottery.entity.Demo;
import org.yjcycc.lottery.service.api.IDemoService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;

@Service("demoService")
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements IDemoService {
}
