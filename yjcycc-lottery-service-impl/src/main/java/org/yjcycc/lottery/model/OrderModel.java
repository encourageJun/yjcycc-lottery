package org.yjcycc.lottery.model;

import org.apache.log4j.Logger;
import org.yjcycc.lottery.analysis.order.scheme.Chooser;
import org.yjcycc.lottery.analysis.order.scheme.DoubleCountCalculator;
import org.yjcycc.lottery.analysis.vo.DoubleCountSchemeVO;
import org.yjcycc.lottery.analysis.vo.OrderNumberVO;
import org.yjcycc.lottery.constant.NumberConstant;
import org.yjcycc.lottery.constant.dict.BalanceType;
import org.yjcycc.lottery.constant.dict.ChooseScheme;
import org.yjcycc.lottery.constant.dict.OrderStatus;
import org.yjcycc.lottery.constant.dict.PursueSchemeDict;
import org.yjcycc.lottery.constant.parameter.ParameterCode;
import org.yjcycc.lottery.entity.*;
import org.yjcycc.lottery.mapper.PlayCategoryMapper;
import org.yjcycc.lottery.mapper.SysParameterMapper;
import org.yjcycc.lottery.mapper.UserBalanceMapper;
import org.yjcycc.lottery.service.*;
import org.yjcycc.lottery.utils.StringUtils;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

public class OrderModel {

    private Logger logger = Logger.getLogger(OrderModel.class);

    private String lotteryType;

    private Plan plan;

    private PlanConfig planConfig;

    private SysParameterMapper sysParameterMapper;
    private UserBalanceMapper userBalanceMapper;
    private PlayCategoryMapper playCategoryMapper;

    private IOrderService orderService;
    private IUserBalanceService userBalanceService;
    private IUserBalanceRecordService userBalanceRecordService;
    private IPlanService planService;
    private IPlanConfigService planConfigService;


    public PursueScheme getPursueScheme() {
        List<PursueScheme> pursueList = planConfig.getPursueList();
        if (pursueList == null || pursueList.isEmpty()) {
            logger.warn("未获取到投注计划配置中的追号方案!");
            return null;
        }
        PursueScheme pursueScheme = null;
        PlayCategory playCategory = null;
        int totalStageCount = 0;
        Integer stageCount = plan.getStageCount();
        if (stageCount == null || stageCount == 0) {
            stageCount = 1;
        } else {
            stageCount++;
        }
        plan.setStageCount(stageCount);
        for (PursueScheme pursue : pursueList) {
            playCategory = playCategoryMapper.getById(pursue.getPlayCategoryId());
            if (!lotteryType.equals(playCategory.getDictLotteryType())) {
                continue;
            }
            if (pursue.getStageCountStart() <= stageCount && pursue.getStageCountEnd() >= stageCount) {
                pursueScheme = pursue;
                if (pursue.getDictPursueScheme() == PursueSchemeDict.scheme_4.getValue()) {
                    if (plan.getStageCount() % 2 == 1) {
                        for (PursueScheme pur : pursueList) {
                            if (pur.getDictPursueScheme() == PursueSchemeDict.scheme_2.getValue()) {
                                pursueScheme = pur;
                            }
                        }
                    } else {
                        for (PursueScheme pur : pursueList) {
                            if (pur.getDictPursueScheme() == PursueSchemeDict.scheme_3.getValue()) {
                                pursueScheme = pur;
                            }
                        }
                    }
                }
            }
            totalStageCount = totalStageCount + pursue.getStageCount();
        }
        if (totalStageCount < stageCount) {
            return null;
        }
        plan.setTotalStageCount(totalStageCount);
        playCategory = playCategoryMapper.getById(pursueScheme.getPlayCategoryId());
        pursueScheme.setPlayCategory(playCategory);
        return pursueScheme;
    }

    public List<OrderNumberVO> getOrderNumber() {
        Chooser chooser = new Chooser();
        Integer chooseScheme = planConfig.getDictChooseScheme();
        String danNumber = null;
        String tuoNumber = null;
        if (ChooseScheme.scheme_1.getValue() == chooseScheme) {
            danNumber = planConfig.getDanNumber();
            tuoNumber = planConfig.getTuoNumber();
        } else if (ChooseScheme.scheme_2.getValue() == chooseScheme) {
            // FIXME
            // 统计选码

        } else if (ChooseScheme.scheme_3.getValue() == chooseScheme) {
            danNumber = planConfig.getDanNumber();
            if (planConfig.getTuoCount() == null) {
                planConfig.setTuoCount(2);
            }
            tuoNumber = chooser.randomNumber(danNumber, planConfig.getTuoCount());
        }
        if (StringUtils.isEmpty(danNumber) || StringUtils.isEmpty(tuoNumber)) {
            logger.warn("未获取到投注计划配置中的胆码或拖码!");
            return null;
        }
        PursueScheme pursueScheme = getPursueScheme();
        if (pursueScheme == null) {
            return null;
        }
        double profitRate = Double.parseDouble(sysParameterMapper.getValueByCode(ParameterCode.PURSUE_PROFIT_RATE));
        List<OrderNumberVO> orderNumberList = chooser.getOrderNumber(pursueScheme, danNumber, tuoNumber);
        for (OrderNumberVO vo : orderNumberList) {
            // 倍投方案(根据利润率计算倍数)
            DoubleCountSchemeVO doubleCountSchemeVO = new DoubleCountSchemeVO(planConfig.getDictDoubleScheme(), planConfig.getDictAmountModel(),
                    pursueScheme.getPlayCategory(), profitRate, plan.getTotalAmount(), vo.getOrderNumber(), planConfig.getDoubleCount());
            doubleCountSchemeVO = new DoubleCountCalculator().calculate(doubleCountSchemeVO);
            vo.setDoubleCount(doubleCountSchemeVO.getDoubleCount());
        }
        return orderNumberList;
    }

    public void process(List<OrderNumberVO> orderNumberList, OpenNumber openNumber) throws RemoteException, Exception {
        OpenNumber nextOpenNumber = new OpenNumber(openNumber.getNextStage(), openNumber.getOpenNumber(), openNumber.getLotteryType());

        // 当期计划投注总额
        BigDecimal orderAmount = BigDecimal.ZERO;
        int doubleCount = 0;
        int stageCount = plan.getStageCount();
        // 余额
//        UserBalance userBalance = userBalanceMapper.getById(1L);

        if (orderNumberList == null || orderNumberList.isEmpty()) {
            return;
        }

        logger.info("---------- 生成投注单. start.");
        for (OrderNumberVO vo : orderNumberList) {
            BigDecimal singleOrderAmount = NumberConstant.BASE_AMOUNT
                    .multiply(new BigDecimal(vo.getOrderCombineSize()))
                    .multiply(new BigDecimal(vo.getDoubleCount()))
                    .divide(new BigDecimal(planConfig.getDictAmountModel()))
                    .setScale(4, BigDecimal.ROUND_FLOOR);
            orderAmount = orderAmount.add(singleOrderAmount);
            doubleCount = doubleCount + vo.getDoubleCount();

            Order order = new Order();
            order.setPlanId(plan.getId());
            order.setExecuteIndex(plan.getExecuteIndex());
            order.setPlayCategoryId(vo.getPlayCategory().getId());
            order.setLotteryType(openNumber.getLotteryType());
            order.setStatus(OrderStatus.status_0.getValue());
            order.setDictAmountModel(planConfig.getDictAmountModel());
            order.setStage(nextOpenNumber.getStage());
            order.setStageDate(nextOpenNumber.getStageDate());
            order.setStageNum(nextOpenNumber.getStageNum());
            order.setStageIndex(nextOpenNumber.getStageIndex());
            order.setDoubleCount(vo.getDoubleCount());
            order.setNumber(vo.getOrderNumber());
            order.setAmount(singleOrderAmount);
            order.setWinCount(0);
            order.setWinAmount(BigDecimal.ZERO);
            orderService.saveOrUpdate(order);
            logger.info("--------------- 投注, 计划id: " + plan.getId());
            logger.info("--------------- 投注, 计划总期数: " + plan.getTotalStageCount());
            logger.info("--------------- 投注, 第" + stageCount + "期");
            logger.info("--------------- 投注, 生成投注单: " + order.getId());
            logger.info("--------------- 投注, 投注号码: " + vo.getOrderNumber());
            logger.info("--------------- 投注, 投注金额: " + singleOrderAmount.doubleValue());
            logger.info("--------------- 投注, 投注倍数: " + vo.getDoubleCount());

            // 更新余额
            BigDecimal balance = planConfig.getBalance().subtract(order.getAmount()).setScale(4, BigDecimal.ROUND_FLOOR);
            logger.info("--------------- 投注, 余额变动, 原余额: " + planConfig.getBalance().doubleValue() + ", 现余额: " + balance.doubleValue());
            planConfig.setBalance(balance);
            planConfigService.saveOrUpdate(planConfig);

            // 新增余额变化记录
            UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
            userBalanceRecord.setOrderId(order.getId());
            userBalanceRecord.setType(BalanceType.BALANCE_TYPE_1.getValue());
            userBalanceRecord.setAmount(order.getAmount());
            userBalanceRecordService.saveOrUpdate(userBalanceRecord);
            logger.info("--------------- 投注, 余额变动记录, 变动金额: " + order.getAmount().doubleValue() + ", 变动类型: " + BalanceType.BALANCE_TYPE_1.getName());
        }
        logger.info("---------- 生成投注单. next->");

        logger.info("---------- 更新计划. start.");
        // 更新计划
        plan.setDoubleCount(doubleCount);
        plan.setStageCount(stageCount);
        plan.setAmount(orderAmount);
        if (plan.getTotalAmount() == null) {
            plan.setTotalAmount(BigDecimal.ZERO);
        }
        plan.setTotalAmount(plan.getTotalAmount().add(orderAmount));
        planService.saveOrUpdate(plan);
        logger.info("--------------- 投注, 计划id: " + plan.getId());
        logger.info("--------------- 投注, 计划总期数: " + plan.getTotalStageCount());
        logger.info("--------------- 投注, 第" + plan.getStageCount() + "期");
        logger.info("--------------- 投注, 本期投注金额: " + orderAmount.doubleValue());
        logger.info("--------------- 投注, 累积投注金额: " + plan.getTotalAmount().doubleValue());
        logger.info("---------- 更新计划. next->");

        logger.info("---------- 更新计划配置. start.");
        // 更新计划配置
        planConfig.setIsOccupy(2); // 占用状态, 0禁用 1否 2是
        logger.info("--------------- 投注, 计划配置id: " + planConfig.getId() + ", 被占用");
        planConfigService.saveOrUpdate(planConfig);
        logger.info("---------- 更新计划配置. end.");
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public PlanConfig getPlanConfig() {
        return planConfig;
    }

    public void setPlanConfig(PlanConfig planConfig) {
        this.planConfig = planConfig;
    }

    public SysParameterMapper getSysParameterMapper() {
        return sysParameterMapper;
    }

    public void setSysParameterMapper(SysParameterMapper sysParameterMapper) {
        this.sysParameterMapper = sysParameterMapper;
    }

    public UserBalanceMapper getUserBalanceMapper() {
        return userBalanceMapper;
    }

    public void setUserBalanceMapper(UserBalanceMapper userBalanceMapper) {
        this.userBalanceMapper = userBalanceMapper;
    }

    public PlayCategoryMapper getPlayCategoryMapper() {
        return playCategoryMapper;
    }

    public void setPlayCategoryMapper(PlayCategoryMapper playCategoryMapper) {
        this.playCategoryMapper = playCategoryMapper;
    }

    public IOrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    public IUserBalanceService getUserBalanceService() {
        return userBalanceService;
    }

    public void setUserBalanceService(IUserBalanceService userBalanceService) {
        this.userBalanceService = userBalanceService;
    }

    public IUserBalanceRecordService getUserBalanceRecordService() {
        return userBalanceRecordService;
    }

    public void setUserBalanceRecordService(IUserBalanceRecordService userBalanceRecordService) {
        this.userBalanceRecordService = userBalanceRecordService;
    }

    public IPlanService getPlanService() {
        return planService;
    }

    public void setPlanService(IPlanService planService) {
        this.planService = planService;
    }

    public IPlanConfigService getPlanConfigService() {
        return planConfigService;
    }

    public void setPlanConfigService(IPlanConfigService planConfigService) {
        this.planConfigService = planConfigService;
    }
}
