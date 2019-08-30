package org.yjcycc.lottery.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.analysis.vo.OrderNumberVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.constant.dict.BalanceType;
import org.yjcycc.lottery.constant.dict.OrderStatus;
import org.yjcycc.lottery.constant.dict.PlanStatus;
import org.yjcycc.lottery.constant.parameter.ParameterCode;
import org.yjcycc.lottery.entity.*;
import org.yjcycc.lottery.mapper.*;
import org.yjcycc.lottery.model.OpenModel;
import org.yjcycc.lottery.model.OrderModel;
import org.yjcycc.lottery.service.*;
import org.yjcycc.lottery.service.vo.OpenVO;
import org.yjcycc.lottery.utils.StringUtils;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private OrderMapper orderMapper;
    @Resource
    private UserBalanceMapper userBalanceMapper;
    @Resource
    private SysParameterMapper sysParameterMapper;
    @Resource
    private PlanMapper planMapper;

    @Resource
    private IOpenNumberExtService openNumberExtService;
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

    @Transactional(readOnly=false)
    @Override
    public void open(OpenVO openVO) throws RemoteException {
        if (openVO == null || openVO.getOpenList() == null || openVO.getOpenList().isEmpty()) {
            return;
        }

        String lotteryType = null;
        for(OpenNumber openNumber : openVO.getOpenList()) {
            if (openNumber == null || openNumber.getLotteryType() == null
                    || StringUtils.isEmpty(openNumber.getStage()) || StringUtils.isEmpty(openNumber.getOpenNumber())) {
                logger.warn("参数为空: lotteryType/stage/openNumber !");
                continue;
            }
            lotteryType = openNumber.getLotteryType();

            OpenNumber openNumberParam = new OpenNumber();
            openNumberParam.setStage(openNumber.getStage());
            openNumberParam.setLotteryType(lotteryType);
            openNumberParam = baseMapper.get(openNumberParam);
            OpenNumber newOpenNumber;
            if (openNumberParam != null) {
                logger.warn("期号:"+openNumber.getStage()+"已开奖!");
                continue;
            } else {
                newOpenNumber = new OpenNumber(openNumber.getStage(), openNumber.getOpenNumber(), lotteryType);

                String currentStage = newOpenNumber.getStage();
                String previousStage = newOpenNumber.getPreviousStage();
                String nextStage = newOpenNumber.getNextStage();

                OpenNumber previousOpenNumber = new OpenNumber();
                if (StringUtils.isNotEmpty(previousStage)) {
                    previousOpenNumber.setStage(previousStage);
                    previousOpenNumber.setLotteryType(lotteryType);
                    previousOpenNumber = baseMapper.get(previousOpenNumber);
                }
                if (previousOpenNumber == null) {
                    previousOpenNumber = new OpenNumber();
                }
                OpenNumberExt newOpenNumberExt = new OpenNumberExt(previousOpenNumber.getExt(), openNumber.getOpenNumber());
                newOpenNumber.setExt(newOpenNumberExt);
            }
            if (newOpenNumber == null) {
                continue;
            }

            // 开奖
            try {
                logger.info("========================= 开奖. start.");
                openProcess(newOpenNumber);
                logger.info("========================= 开奖. end.");
            } catch (Exception e) {
                logger.error("开奖失败, 期号:" + newOpenNumber.getStage() + ". end.", e);
                continue;
            }

            // 投注
            try {
                logger.info("========================= 投注. start.");
                orderProcess(newOpenNumber);
                logger.info("========================= 投注. end.");
            } catch (Exception e) {
                logger.error("投注失败, 期号:" + newOpenNumber.getNextStage() + ". end.", e);
                continue;
            }

            // 统计(异步)

        }
    }

    @Transactional(readOnly=false)
    public void openProcess(OpenNumber openNumber) throws RemoteException {
        logger.info("---------- 保存开奖号码. start.");
        // 保存开奖号码
        openNumberExtService.saveOrUpdate(openNumber.getExt());
        openNumber.setExtId(openNumber.getExt().getId());
        this.saveOrUpdate(openNumber);
        logger.info("--------------- 开奖. 保存成功, 期号: "+openNumber.getStage()+", 开奖号码: " + openNumber.getOpenNumber() + ", 11选5类别: " + openNumber.getLotteryType());
        logger.info("---------- 保存开奖号码. next->");

        logger.info("---------- 获取待开奖的投注单. start.");
        // 获取待开奖的投注单
        Order orderParam = new Order();
        orderParam.setStage(openNumber.getStage());
        orderParam.setLotteryType(openNumber.getLotteryType());
        List<Order> orderList = orderMapper.findList(orderParam);
        if (orderList == null || orderList.isEmpty()) {
            logger.info("---------- 未获取到待开奖的投注单! end.");
            return;
        }
        logger.info("--------------- 开奖. 投注单数: " + orderList.size());
        logger.info("---------- 获取待开奖的投注单. next->");

        for (Order order : orderList) {
            logger.info("---------- 对奖. start.");
            // 对奖
            CompareOpenVO vo = new CompareOpenVO(order.getPlayCategory(), order.getNumber(), openNumber.getOpenNumber());
            new OpenModel().compare(vo);
            int doubleCount = order.getDoubleCount();
            int amountModel = order.getDictAmountModel();
            BigDecimal winAmount = vo.getWinAmount().multiply(new BigDecimal(doubleCount)).divide(new BigDecimal(amountModel)).setScale(BigDecimal.ROUND_FLOOR, 4);

            Integer status = 0; // 开奖状态, 0
            if (winAmount.compareTo(BigDecimal.ZERO) > 0) {
                status = 2; // 已中奖
                logger.info("--------------- 开奖. 投注单id: " + order.getId() + ", 中奖金额: " + winAmount.doubleValue());
            } else {
                status = 1; // 未中奖
                logger.info("--------------- 开奖. 投注单id: " + order.getId() + ", " + OrderStatus.getNameByValue(status));
            }
            // 更新投注单
            order.setStatus(status);
            order.setWinCount(vo.getWinCount());
            order.setWinAmount(winAmount);
            orderService.saveOrUpdate(order);
            logger.info("---------- 对奖. next->");

            if (winAmount.compareTo(BigDecimal.ZERO) > 0) {
                logger.info("---------- 更新余额. start.");
                // 更新余额
                UserBalance userBalance = userBalanceMapper.getById(1L);
                BigDecimal balance = winAmount.add(userBalance.getBalance()).setScale(BigDecimal.ROUND_FLOOR, 4);
                logger.info("--------------- 开奖. 余额id: " + userBalance.getId() + ", 原余额: " + userBalance.getBalance().doubleValue() + ", 现余额: " + balance.doubleValue());
                userBalance.setBalance(balance);
                userBalanceService.saveOrUpdate(userBalance);
                logger.info("---------- 更新余额. next->");

                logger.info("---------- 新增余额变化记录. start.");
                // 新增余额变化记录
                UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
                userBalanceRecord.setOrderId(order.getId());
                userBalanceRecord.setType(BalanceType.BALANCE_TYPE_2.getValue());
                userBalanceRecord.setAmount(winAmount);
                userBalanceRecordService.saveOrUpdate(userBalanceRecord);
                logger.info("--------------- 开奖. 记录id: " + userBalanceRecord.getId() + ", 变动金额: " + winAmount.doubleValue() + ", 变动类型: " + BalanceType.BALANCE_TYPE_2.getName());
                logger.info("---------- 新增余额变化记录. end.");
            }
        }

        // 更新投注计划配置
        Map<String,Object> planConfigMap = new HashMap<>();
        planConfigMap.put("inIsOccupy", "2,3");
        planConfigMap.put("dictLotteryType", openNumber.getLotteryType());
        List<PlanConfig> planConfigList = planConfigService.findListByMap(planConfigMap);
        if (planConfigList != null && !planConfigList.isEmpty()) {
            for (PlanConfig planConfig : planConfigList) {

                Plan planParam = new Plan();
//                planParam.setExecuteIndex(planConfig.getExecuteIndex());
                planParam.setPlanConfigId(planConfig.getId());
                planParam.setStatus(PlanStatus.status_0.getValue());
                planParam.setDictLotteryType(openNumber.getLotteryType());
                List<Plan> planningList = planService.findList(planParam);

                boolean isWin = false; // 是否中奖
                boolean isComplete = false; // 总期数是否已追完
                if (planningList != null && !planningList.isEmpty()) {
                    for (Plan plan : planningList) {
                        Order orderPar = new Order();
                        orderPar.setPlanId(plan.getId());
                        orderPar.setExecuteIndex(plan.getExecuteIndex());
                        List<Order> oList = orderService.findList(orderPar);

                        BigDecimal totalAmount = BigDecimal.ZERO; // 计划投注总额
                        BigDecimal totalWinAmount = BigDecimal.ZERO; // 计划中奖总额
                        if (oList != null && !oList.isEmpty()) {
                            for (Order order : oList) {
                                totalAmount = totalAmount.add(order.getAmount());
                                totalWinAmount = totalWinAmount.add(order.getWinAmount());
                            }
                        }
                        logger.info("---------- 更新投注状态. start.");
                        // 更新投注计划
                        if (totalWinAmount.compareTo(BigDecimal.ZERO) > 0) { // 中奖
                            plan.setWinAmount(totalWinAmount);
                            plan.setProfitAmount(totalWinAmount.subtract(totalAmount).setScale(BigDecimal.ROUND_FLOOR, 4));
                            plan.setStatus(2); // 中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖
                            logger.info("--------------- 开奖. 投注计划id: " + plan.getId() + ", 已中奖, 中奖金额: " + totalWinAmount.doubleValue() + ", 盈利金额: " + plan.getProfitAmount().doubleValue());
                        } else { // 未中奖
                            if (plan.getStageCount() == plan.getTotalStageCount().intValue()) { // 最后一期更新为未中奖
                                plan.setStatus(1); // 中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖
                                logger.info("--------------- 开奖. 投注计划id: " + plan.getId() + ", 最后一期, 未中奖, 累积投注金额: " + plan.getTotalAmount().doubleValue());
                            } else {
                                logger.info("--------------- 开奖. 投注计划id: " + plan.getId() + ", 未中奖, 继续投注, 累积投注金额: " + plan.getTotalAmount().doubleValue());
                            }
                        }
                        planService.saveOrUpdate(plan);
                        logger.info("---------- 更新投注状态. next->");
                    }
                    for (Plan plan : planningList) {
                        if (plan.getStatus() == PlanStatus.status_2.getValue()) {
                            isWin = true;
                            break;
                        }
                        if (plan.getStatus() == PlanStatus.status_1.getValue()) {
                            isComplete = true;
                            break;
                        }
                    }
                } else {
                    continue;
                }

                planParam.setExecuteIndex(null);
                planParam.setDictLotteryType(null);
                planParam.setStatus(null);
                List<Plan> fullPlanList = planService.findList(planParam);
                if (fullPlanList == null || fullPlanList.isEmpty()) {
                    continue;
                }

                BigDecimal totalProfitAmount = BigDecimal.ZERO; // 累积盈利金额
                BigDecimal totalWinAmount = BigDecimal.ZERO; // 累积中奖金额
                BigDecimal totalAmount = BigDecimal.ZERO; // 累积投注金额
                boolean hasPursue = false; // 是否还有追号中计划
                for (Plan plan : fullPlanList) {
                    totalProfitAmount = totalProfitAmount.add(plan.getProfitAmount());
                    totalWinAmount = totalWinAmount.add(plan.getWinAmount());
                    totalAmount = totalAmount.add(plan.getTotalAmount());
                }
                for (Plan plan : fullPlanList) {
                    // 判断是否仍有非此LotteryType的投注未开奖
                    if (plan.getStatus() == PlanStatus.status_0.getValue() && !plan.getDictLotteryType().equals(openNumber.getLotteryType())) {
                        hasPursue = true;
                        break;
                    }
                }
                planConfig.setTotalProfitAmount(totalWinAmount.subtract(totalAmount));
                logger.info("--------------- 开奖. 投注计划配置id: " + planConfig.getId() + ", 累积盈利金额: " + planConfig.getTotalProfitAmount().doubleValue());

                // 是否达到提现盈利率
                // 公式: (中奖金额-投注金额) / 充值金额 >= 提现盈利率
                BigDecimal recharge = planConfig.getRecharge(); // 充值金额
                double cashOutRate = planConfig.getTotalProfitAmount().divide(recharge, 4, BigDecimal.ROUND_FLOOR).doubleValue();
                logger.info("--------------- 开奖. 投注计划配置id: " + planConfig.getId() + ", 盈利率: " + cashOutRate);
                logger.info("--------------- 开奖. 投注计划配置id: " + planConfig.getId() + ", 提现盈利率: " + planConfig.getCashOutRate() + "可提现金额: " + planConfig.getTotalProfitAmount().doubleValue());
                if (cashOutRate >= planConfig.getCashOutRate()) {
                    if (hasPursue) {
                        // 已达到提现盈利率仍有非此LotteryType的投注未开奖
                        planConfig.setIsOccupy(3);// 占用状态, 0停止 1未占用 2已占用 3已达到提现盈利率仍有非此LotteryType的投注未开奖
                    } else {
                        logger.info("--------------- 开奖. 投注计划配置id: " + planConfig.getId() + ", 已达到提现盈利率, 停止计划");

                        // 达到提现盈利率后, 停止计划
                        planConfig.setIsOccupy(0);// 占用状态, 0停止 1未占用 2已占用 3已达到提现盈利率仍有未开奖的投注

                        // 备份此配置的bs_plan记录 至 bs_plan_history表
                        planMapper.insertFromCopy(planConfig.getId());
                        logger.info("--------------- 开奖. 投注计划配置id: " + planConfig.getId() + ", 备份此配置的bs_plan记录 至 bs_plan_history表");

                        // 删除此配置的计划记录
                        Plan plan = new Plan();
                        plan.setPlanConfigId(planConfig.getId());
                        planMapper.delete(plan);
                        logger.info("--------------- 开奖. 投注计划配置id: " + planConfig.getId() + ", 删除此配置的计划记录");
                    }
                } else {
                    // 中奖后/总期数追完后, 重新开始
                    if (!hasPursue) {
                        if (isWin || isComplete) {
                            planConfig.setIsOccupy(1);// 占用状态, 0禁用 1否 2是
                            logger.info("--------------- 开奖. 投注计划配置id: " + planConfig.getId() + ", 已中奖/总期数已追完, 重新开始, 恢复占用状态: 未占用");
                        }
                    }
                }

                planConfigService.saveOrUpdate(planConfig);
            }
        }
    }

    @Transactional(readOnly=false)
    public void orderProcess(OpenNumber openNumber) throws RemoteException, Exception {
        logger.info("---------- 投注开关. start.");
        String pollSwitch = sysParameterMapper.getValueByCode(ParameterCode.ORDER_SWITCH); // 投注开关, 0关 1开
        if (StringUtils.isEmpty(pollSwitch) || pollSwitch.equals(Constant.STRING_ZERO)) {
            logger.warn("---------- 投注. 投注开关: 未开启! end.");
            return;
        }
        logger.info("--------------- 投注. 投注开关: 已开启");
        logger.info("---------- 投注开关. next->");

        logger.info("-------------------- 根据投注计划配置生成投注单. start.");
        logger.info("---------- 获取继续投注的计划配置. start");
        // 获取未占用的计划配置
        Map<String,Object> planConfigMap = new HashMap<>();
        planConfigMap.put("inIsOccupy", "1,2");
        planConfigMap.put("dictLotteryType", openNumber.getLotteryType());
        List<PlanConfig> planConfigList = planConfigService.findListByMap(planConfigMap);
        if (planConfigList == null || planConfigList.isEmpty()) {
            logger.warn("---------- 未获取到继续投注的投注计划配置! end");
            logger.info("-------------------- 根据投注计划配置生成投注单. end.");
            return;
        }
        logger.info("--------------- 投注. 继续投注的计划配置数: " + planConfigList.size());
        logger.info("---------- 获取继续投注的计划配置. next->");

        OrderModel orderModel = new OrderModel();
        orderModel.setLotteryType(openNumber.getLotteryType());

        for (PlanConfig planConfig : planConfigList) {
            if (planConfig.getIsOccupy() == 1) { // 未占用, 生成新计划
                Integer executeIndex = planConfig.getExecuteIndex(); // 计划执行序号, 新计划累加
                if (planConfig.getExecuteIndex() == null) {
                    executeIndex = 0;
                } else {
                    executeIndex ++;
                }
                planConfig.setExecuteIndex(executeIndex);
                planConfigService.saveOrUpdate(planConfig);

                createNewPlan(orderModel, planConfig, openNumber, executeIndex);

            } else { // 已占用, 继续投注
                logger.info("---------- 获取追号中的投注计划. start.");
                // 获取追号中的投注计划
                Plan planParam = new Plan();
                planParam.setPlanConfigId(planConfig.getId());
                planParam.setStatus(PlanStatus.status_0.getValue()); // 追号中
                planParam.setDictLotteryType(openNumber.getLotteryType());
                List<Plan> planList = planService.findList(planParam);
                if (planList == null || planList.isEmpty()) {
                    logger.warn("---------- 未获取到追号中的投注计划!. next->");
                    createNewPlan(orderModel, planConfig, openNumber, planConfig.getExecuteIndex());
                } else {
                    logger.info("--------------- 投注. 投注计划数: " + planList.size());
                    logger.info("---------- 获取追号中的投注计划. next->");

                    for (Plan plan : planList) {
                        if (planList == null || planList.isEmpty()) {
                            logger.warn("---------- 未获取到追号中的投注计划!. next->");
                        } else {
                            List<OrderNumberVO> orderNumberList = getOrderNumberList(orderModel, plan, planConfig);

                            order(orderModel, orderNumberList, openNumber);
                        }
                    }
                }

            }
        }
        logger.info("-------------------- 根据投注计划配置生成投注单. end.");

    }

    /**
     * 新增计划
     * @param orderModel
     * @param planConfig
     * @param openNumber
     * @param executeIndex
     * @throws Exception
     */
    private void createNewPlan(OrderModel orderModel, PlanConfig planConfig, OpenNumber openNumber, Integer executeIndex) throws Exception {
        logger.info("---------- 新增计划. start.");
        logger.info("--------------- 投注. 计划执行序号: " + executeIndex);
        // 新增投注计划
        Plan plan = new Plan();
        plan.setPlanConfigId(planConfig.getId());
        plan.setStatus(0); // 中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖
        plan.setDictLotteryType(openNumber.getLotteryType());
        plan.setExecuteIndex(executeIndex);
        plan.setStageCount(0);
        plan.setTotalStageCount(0);
        plan.setWinAmount(BigDecimal.ZERO);
        plan.setProfitAmount(BigDecimal.ZERO);
        planService.saveOrUpdate(plan);
        logger.info("--------------- 投注. 投注计划id: " + plan.getId());
        logger.info("---------- 新增计划. next->");

        List<OrderNumberVO> orderNumberList = getOrderNumberList(orderModel, plan, planConfig);

        order(orderModel, orderNumberList, openNumber);
    }

    /**
     * 获取投注号码
     * @param orderModel
     * @param plan
     * @param planConfig
     * @return
     */
    private List<OrderNumberVO> getOrderNumberList(OrderModel orderModel, Plan plan, PlanConfig planConfig) {
        logger.info("---------- 获取投注号码. start.");
        // 投注号码列表
        orderModel.setPlan(plan);
        orderModel.setPlanConfig(planConfig);
        orderModel.setSysParameterMapper(sysParameterMapper);
        orderModel.setPlayCategoryMapper(playCategoryMapper);
        List<OrderNumberVO> orderNumberList = orderModel.getOrderNumber();
        if (orderNumberList == null || orderNumberList.isEmpty()) {
            logger.warn("---------- 未获取到投注号码, 投注计划配置id: "+planConfig.getId()+". end.");
            logger.info("-------------------- 根据投注计划配置生成投注单. end.");
            return null;
        }
        logger.info("--------------- 投注. 投注号码注数: " + orderNumberList.size());
        logger.info("---------- 获取投注号码. next->");
        return orderNumberList;
    }

    /**
     * 投注
     * @param orderModel
     * @param orderNumberList
     * @param openNumber
     * @throws Exception
     */
    private void order(OrderModel orderModel, List<OrderNumberVO> orderNumberList, OpenNumber openNumber) throws Exception {
        // 生成投注单
        orderModel.setUserBalanceMapper(userBalanceMapper);
        orderModel.setOrderService(orderService);
        orderModel.setUserBalanceService(userBalanceService);
        orderModel.setUserBalanceRecordService(userBalanceRecordService);
        orderModel.setPlanService(planService);
        orderModel.setPlanConfigService(planConfigService);
        orderModel.process(orderNumberList, openNumber);
    }

}