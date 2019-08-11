package org.yjcycc.demo.service.impl;

import org.springframework.stereotype.Service;
import org.yjcycc.demo.entity.Demo;
import org.yjcycc.demo.service.api.IDemoService;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;

@Service("demoService")
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements IDemoService {
}
