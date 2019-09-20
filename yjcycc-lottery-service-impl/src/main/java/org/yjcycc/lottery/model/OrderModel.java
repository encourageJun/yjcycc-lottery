package org.yjcycc.lottery.model;

import org.apache.log4j.Logger;
import org.yjcycc.lottery.analysis.number.RandomNumber;
import org.yjcycc.lottery.analysis.order.scheme.Chooser;
import org.yjcycc.lottery.analysis.order.scheme.DoubleCountCalculator;
import org.yjcycc.lottery.analysis.vo.DoubleCountSchemeVO;
import org.yjcycc.lottery.analysis.vo.OrderNumberVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.constant.NumberConstant;
import org.yjcycc.lottery.constant.dict.*;
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

/**
 * 投注过程
 */
public class OrderModel {

    private Logger logger = Logger.getLogger(OrderModel.class);

    private OpenNumber nextOpenNumber;

    private SysParameterMapper sysParameterMapper;
    private UserBalanceMapper userBalanceMapper;
    private PlayCategoryMapper playCategoryMapper;

    private IOrderService orderService;
    private IUserBalanceService userBalanceService;
    private IUserBalanceRecordService userBalanceRecordService;
    private IPlanService planService;
    private IPlanConfigService planConfigService;


    /**
     * 投注过程
     * @throws Exception
     */
    public void process() throws Exception {
        // 投注开关
        String pollSwitch = sysParameterMapper.getValueByCode(ParameterCode.ORDER_SWITCH); // 投注开关, 0关 1开
        if (StringUtils.isEmpty(pollSwitch) || pollSwitch.equals(Constant.STRING_ZERO)) {
            logger.warn("---------- 投注. 投注开关: 未开启! end.");
            return;
        }
        logger.info("---------- 投注. 投注开关: 已开启");

        // 获取未占用的计划配置
        List<PlanConfig> planConfigList = planConfigService.getPlanConfigList("1,2", nextOpenNumber.getLotteryType());
        if (planConfigList == null || planConfigList.isEmpty()) {
            logger.warn("---------- 投注. 未获取到继续投注的投注计划配置! end");
            logger.info("-------------------- 投注. 根据投注计划配置生成投注单. end.");
            return;
        }
        logger.info("--------------- 投注. 继续投注的计划配置数: " + planConfigList.size());

        for (PlanConfig planConfig : planConfigList) {
            if (planConfig.getIsOccupy() == 1) { // 未占用, 生成新计划
                // 新增计划
                logger.info("---------- 新增计划. start.");
                Plan plan = createNewPlan(planConfig.getId());
                logger.info("---------- 新增计划. next->");

                // 新计划投注
                logger.info("---------- 新计划投注. start.");
                order(plan, planConfig);
                logger.info("---------- 新计划投注. next->");
            } else { // 已占用, 继续投注
                // 获取追号中的投注计划
                List<Plan> planList = planService.getPlanList(planConfig.getId(), PlanStatus.status_0.getValue(), nextOpenNumber.getLotteryType());
                if (planList == null || planList.isEmpty()) {
                    logger.warn("---------- 未获取到追号中的投注计划!. next->");
                    // 新增计划
                    logger.info("---------- 新增计划. start.");
                    Plan plan = createNewPlan(planConfig.getId());
                    logger.info("---------- 新增计划. next->");

                    // 新计划投注
                    logger.info("---------- 新计划投注. start.");
                    order(plan, planConfig);
                    logger.info("---------- 新计划投注. next->");
                } else {
                    logger.info("--------------- 投注. 投注计划数: " + planList.size());
                    logger.info("---------- 获取追号中的投注计划. next->");

                    for (Plan plan : planList) {
                        if (planList == null || planList.isEmpty()) {
                            logger.warn("---------- 未获取到追号中的投注计划!. next->");
                        } else {
                            logger.info("---------- 追号中计划投注. start.");
                            order(plan, planConfig);
                            logger.info("---------- 追号中计划投注. next->");
                        }
                    }
                }
            }
        }

    }

    /**
     * 新增计划
     * @param planConfigId
     * @return
     * @throws Exception
     */
    private Plan createNewPlan(Long planConfigId) throws Exception {
        // 新增投注计划
        Plan plan = new Plan();
        plan.setPlanConfigId(planConfigId);
        plan.setStatus(0); // 中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖
        plan.setDictLotteryType(nextOpenNumber.getLotteryType());
        plan.setExecuteIndex(0);
        plan.setStageCount(0);
        plan.setTotalStageCount(0);
        plan.setWinAmount(BigDecimal.ZERO);
        plan.setProfitAmount(BigDecimal.ZERO);
        planService.saveOrUpdate(plan);
        logger.info("--------------- 投注. 新增投注计划id: " + plan.getId());
        return plan;
    }

    /**
     * 投注
     * @param plan
     * @param planConfig
     * @throws RemoteException
     * @throws Exception
     */
    private void order(Plan plan, PlanConfig planConfig) throws RemoteException, Exception {
        // 获取投注号码
        List<OrderNumberVO> orderNumberList = getOrderNumber(plan, planConfig);
        if (orderNumberList == null || orderNumberList.isEmpty()) {
            return;
        }

        // 当期计划投注总额
        BigDecimal orderAmount = BigDecimal.ZERO;
        int doubleCount = 0;
        int stageCount = plan.getStageCount();

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
            order.setLotteryType(nextOpenNumber.getLotteryType());
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

    /**
     * 获取投注号码
     * @param plan
     * @param planConfig
     * @return
     */
    private List<OrderNumberVO> getOrderNumber(Plan plan, PlanConfig planConfig) {
        String danNumber = null; // 胆码
        String tuoNumber = null; // 拖码
        Integer chooseScheme = planConfig.getDictChooseScheme();
        if (ChooseScheme.scheme_1.getValue() == chooseScheme) {
            // 手动设置胆码-拖码
            danNumber = planConfig.getDanNumber();
            tuoNumber = planConfig.getTuoNumber();
        } else if (ChooseScheme.scheme_2.getValue() == chooseScheme) {
            // FIXME
            // 统计选码

        } else if (ChooseScheme.scheme_3.getValue() == chooseScheme) {
            // 手动设置胆码-随机选拖码
            danNumber = planConfig.getDanNumber();
            if (planConfig.getTuoCount() == null || planConfig.getTuoCount() == 0) {
                planConfig.setTuoCount(2);
            }
            tuoNumber = new RandomNumber().next(danNumber, planConfig.getTuoCount());
        }
        if (StringUtils.isEmpty(danNumber) || StringUtils.isEmpty(tuoNumber)) {
            logger.warn("未获取到投注计划配置中的胆码或拖码!");
            return null;
        }

        // 计算期数
        calcStageCount(plan, planConfig);

        // 获取投注计划配置中的追号方案
        PursueScheme pursueScheme = getPursueScheme(plan, planConfig);
        if (pursueScheme == null) {
            return null;
        }

        // 系统参数: 盈利率
        double profitRate = Double.parseDouble(sysParameterMapper.getValueByCode(ParameterCode.PURSUE_PROFIT_RATE));
        List<OrderNumberVO> orderNumberList = new Chooser().getOrderNumber(pursueScheme, danNumber, tuoNumber);
        BigDecimal totalAmount = plan.getTotalAmount(); // 累积投注金额
        for (OrderNumberVO vo : orderNumberList) {
            // 倍投方案(根据利润率计算倍数)
            DoubleCountSchemeVO doubleCountSchemeVO = new DoubleCountSchemeVO(planConfig.getDictDoubleScheme(), planConfig.getDictAmountModel(),
                    pursueScheme.getPlayCategory(), profitRate, plan.getTotalAmount(), vo.getOrderNumber(), planConfig.getDoubleCount(), orderNumberList.size());
            doubleCountSchemeVO = new DoubleCountCalculator().calculate(doubleCountSchemeVO);
            vo.setDoubleCount(doubleCountSchemeVO.getDoubleCount());
        }
        return orderNumberList;
    }

    /**
     * 计算第几期
     * @param plan
     * @param planConfig
     */
    private void calcStageCount(Plan plan, PlanConfig planConfig) {
        int totalStageCount = 0;
        Integer stageCount = plan.getStageCount();
        if (stageCount == null || stageCount == 0) {
            stageCount = 1;
        } else {
            stageCount++;
        }
        plan.setStageCount(stageCount); // 第N期

        for (PursueScheme pursue : planConfig.getPursueList()) {
            totalStageCount = totalStageCount + pursue.getStageCount();
        }
        if (totalStageCount < stageCount) {
            return;
        }
        plan.setTotalStageCount(totalStageCount); // 总期数
    }

    /**
     * 获取到投注计划配置中的追号方案
     * @param plan
     * @param planConfig
     * @return
     */
    private PursueScheme getPursueScheme(Plan plan, PlanConfig planConfig) {
        List<PursueScheme> pursueList = planConfig.getPursueList();
        if (pursueList == null || pursueList.isEmpty()) {
            logger.warn("未获取到投注计划配置中的追号方案!");
            return null;
        }

        PursueScheme pursueScheme = null;
        PlayCategory playCategory = null;

        for (PursueScheme pursue : pursueList) {
            playCategory = playCategoryMapper.getById(pursue.getPlayCategoryId());
            if (!nextOpenNumber.getLotteryType().equals(playCategory.getDictLotteryType())) {
                continue;
            }
            if (pursue.getStageCountStart() <= plan.getStageCount() && pursue.getStageCountEnd() >= plan.getStageCount()) {
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
        }
        playCategory = playCategoryMapper.getById(pursueScheme.getPlayCategoryId());
        pursueScheme.setPlayCategory(playCategory);
        return pursueScheme;
    }

    public OpenNumber getNextOpenNumber() {
        return nextOpenNumber;
    }

    public void setNextOpenNumber(OpenNumber nextOpenNumber) {
        this.nextOpenNumber = nextOpenNumber;
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
