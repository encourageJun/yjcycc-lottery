package org.yjcycc.lottery.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjcycc.lottery.entity.OpenNumber;
import org.yjcycc.lottery.entity.OpenNumberExt;
import org.yjcycc.lottery.mapper.*;
import org.yjcycc.lottery.model.OpenCompareModel;
import org.yjcycc.lottery.model.OrderModel;
import org.yjcycc.lottery.service.*;
import org.yjcycc.lottery.service.vo.OpenVO;
import org.yjcycc.lottery.utils.StringUtils;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * 开奖号码(OpenNumber)表服务实现类
 *
 * @author makejava
 * @since 2019-08-16 19:10:52
 */
@Service("openNumberService")
public class OpenNumberServiceImpl extends BaseServiceImpl<OpenNumber> implements IOpenNumberService {

    private Logger logger = Logger.getLogger(OpenNumberServiceImpl.class);

    @Resource
    private PlayCategoryMapper playCategoryMapper;
    @Resource
    private UserBalanceMapper userBalanceMapper;
    @Resource
    private SysParameterMapper sysParameterMapper;
    @Resource
    private PlanMapper planMapper;

    @Resource
    private IOpenNumberExtService openNumberExtService;
    @Resource
    private IOpenNumberService openNumberService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IPlanService planService;
    @Resource
    private IPlanConfigService planConfigService;
    @Resource
    private IUserBalanceService userBalanceService;
    @Resource
    private IUserBalanceRecordService userBalanceRecordService;


    @Resource
    private void initMapper(OpenNumberMapper openNumberMapper) {
        this.setBaseMapper(openNumberMapper);
    }


    public void save(OpenNumber openNumber) throws Exception {
        openNumberExtService.saveOrUpdate(openNumber.getExt());
        openNumber.setExtId(openNumber.getExt().getId());
        openNumberService.saveOrUpdate(openNumber);
    }

    public OpenNumber get(String stage, String lotteryType) {
        OpenNumber openNumberParam = new OpenNumber();
        openNumberParam.setStage(stage);
        openNumberParam.setLotteryType(lotteryType);
        return baseMapper.get(openNumberParam);
    }

    @Transactional
    @Override
    public void open(OpenVO openVO) throws Exception {
        if (openVO == null || openVO.getOpenList() == null || openVO.getOpenList().isEmpty()) {
            return;
        }

        for(OpenNumber openNumber : openVO.getOpenList()) {
            if (openNumber == null || openNumber.getLotteryType() == null
                    || StringUtils.isEmpty(openNumber.getStage()) || StringUtils.isEmpty(openNumber.getOpenNumber())) {
                logger.warn("参数为空: lotteryType/stage/openNumber !");
                continue;
            }
            String lotteryType = openNumber.getLotteryType();
            String currentStage = openNumber.getStage();
            String currentOpenNumber = openNumber.getOpenNumber();

            // 当前开奖号码
            OpenNumber newOpenNumber = new OpenNumber(currentStage, currentOpenNumber, lotteryType);
            String previousStage = newOpenNumber.getPreviousStage();

            // 检查是否已开奖
            OpenNumber opened = this.get(currentStage, lotteryType);
            if (opened != null) {
                logger.warn("期号:"+currentStage+"已开奖!");
                continue;
            }

            // 获取上一期扩展信息
            OpenNumber previousOpenNumber = this.get(previousStage, lotteryType);
            if (previousOpenNumber == null) {
                previousOpenNumber = new OpenNumber();
            }
            OpenNumberExt newOpenNumberExt = new OpenNumberExt(previousOpenNumber.getExt(), openNumber.getOpenNumber());
            newOpenNumber.setExt(newOpenNumberExt);

            // 保存开奖
            this.save(newOpenNumber);

            // 对奖
            OpenCompareModel openCompareModel = new OpenCompareModel();
            openCompareModel.setNewOpenNumber(newOpenNumber);
            openCompareModel.setPlanMapper(planMapper);
            openCompareModel.setOpenNumberExtService(openNumberExtService);
            openCompareModel.setOpenNumberService(openNumberService);
            openCompareModel.setOrderService(orderService);
            openCompareModel.setPlanService(planService);
            openCompareModel.setPlanConfigService(planConfigService);
            openCompareModel.setUserBalanceService(userBalanceService);
            openCompareModel.setUserBalanceRecordService(userBalanceRecordService);
            try {
                logger.info("========================= 对奖. start.");
                openCompareModel.process();
                logger.info("========================= 对奖. end.");
            } catch (Exception e) {
                logger.error("开奖失败, 期号:" + newOpenNumber.getStage() + ". end.", e);
                continue;
            }

            // 投注(异步)
            OrderModel orderModel = new OrderModel();
            orderModel.setNextOpenNumber(newOpenNumber.getNextOpenNumber());
            orderModel.setSysParameterMapper(sysParameterMapper);
            orderModel.setPlayCategoryMapper(playCategoryMapper);
            orderModel.setUserBalanceMapper(userBalanceMapper);
            orderModel.setOrderService(orderService);
            orderModel.setUserBalanceService(userBalanceService);
            orderModel.setUserBalanceRecordService(userBalanceRecordService);
            orderModel.setPlanService(planService);
            orderModel.setPlanConfigService(planConfigService);
            try {
                logger.info("========================= 投注. start.");
                orderModel.process();
                logger.info("========================= 投注. end.");
            } catch (Exception e) {
                logger.error("投注失败, 期号:" + newOpenNumber.getNextStage() + ". end.", e);
                continue;
            }

            // 统计(异步)

        }
    }

}