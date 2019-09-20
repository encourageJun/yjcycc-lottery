package org.yjcycc.lottery.model;

import org.apache.log4j.Logger;
import org.yjcycc.lottery.analysis.number.NumberConstructor;
import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.constant.dict.BalanceType;
import org.yjcycc.lottery.constant.dict.PlanStatus;
import org.yjcycc.lottery.entity.*;
import org.yjcycc.lottery.mapper.PlanMapper;
import org.yjcycc.lottery.service.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 对奖过程
 */
public class OpenCompareModel {

    private Logger logger = Logger.getLogger(OpenCompareModel.class);

    private OpenNumber newOpenNumber;

    private PlanMapper planMapper;

    private IOpenNumberExtService openNumberExtService;
    private IOpenNumberService openNumberService;
    private IOrderService orderService;
    private IPlanService planService;
    private IPlanConfigService planConfigService;
    private IUserBalanceService userBalanceService;
    private IUserBalanceRecordService userBalanceRecordService;


    /**
     * 对奖过程
     * @throws Exception
     */
    public void process() throws Exception {
        // 获取待对奖的投注单
        List<Order> orderList = orderService.getOrderList(newOpenNumber.getStage(), newOpenNumber.getLotteryType());
        if (orderList == null || orderList.isEmpty()) {
            logger.info("---------- 对奖. 未获取到待对奖的投注单! end.");
            return;
        }
        logger.info("--------------- 对奖. 投注单数: " + orderList.size());

        // 订单对奖
        for (Order order : orderList) {
            logger.info("---------- 对奖. start.");
            calcWinAmount(order);
            setStatus(order);
            orderService.saveOrUpdate(order);
            logger.info("---------- 对奖. next->");

            if (order.getWinAmount().compareTo(BigDecimal.ZERO) > 0) {
                logger.info("---------- 对奖. 更新余额. start.");
                updateBalance(order);
                logger.info("---------- 对奖. 更新余额. next->");

                logger.info("---------- 对奖. 新增余额变化记录. start.");
                saveBalanceRecord(order);
                logger.info("---------- 对奖. 新增余额变化记录. end.");
            }
        }

        // 获取投注计划配置
        List<PlanConfig> planConfigList = planConfigService.getPlanConfigList("2,3", newOpenNumber.getLotteryType());
        if (planConfigList == null || planConfigList.isEmpty()) {
            logger.info("---------- 未获取到待对奖的投注单! end.");
            return;
        }
        logger.info("--------------- 对奖. 投注计划配置数: " + planConfigList.size());

        // 投注计划配置 对奖
        for (PlanConfig planConfig : planConfigList) {
            // 获取追号中的计划
            List<Plan> planningList = planService.getPlanList(planConfig.getId(), PlanStatus.status_0.getValue(), newOpenNumber.getLotteryType());
            if (planningList == null || planningList.isEmpty()) {
                continue;
            }
            logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 追号中的计划数: " + planningList.size());

            // 更新投注计划
            for (Plan plan : planningList) {
                logger.info("---------- 对奖. 更新投注计划. start.");
                updatePlan(plan);
                logger.info("---------- 对奖. 更新投注计划. next->");
            }

            boolean isWin = false; // 是否中奖
            boolean isComplete = false; // 总期数是否已追完
            for (Plan plan : planningList) {
                if (plan.getStatus() == PlanStatus.status_2.getValue()) {
                    isWin = true;
                    logger.info("--------------- 对奖. 投注计划id: " + plan.getId() + ", 已中奖.");
                    break;
                }
                if (plan.getStatus() == PlanStatus.status_1.getValue() && plan.getStageCount() >= plan.getTotalStageCount().intValue()) {
                    isComplete = true;
                    logger.info("--------------- 对奖. 投注计划id: " + plan.getId() + ", 已追完.");
                    break;
                }
            }

            // 更新投注计划配置
            logger.info("---------- 对奖. 更新投注计划配置. start.");
            updatePlanConfig(planConfig, isWin, isComplete);
            logger.info("---------- 对奖. 更新投注计划配置. next->");

        }
    }

    /**
     * 计算中奖金额
     * @param order
     */
    public void calcWinAmount(Order order) {
        CompareOpenVO vo = new CompareOpenVO(order.getPlayCategory(), order.getNumber(), newOpenNumber.getOpenNumber());
        NumberConstructor.getConstructor(vo.getPlayCategory().getClassName()).compare(vo);
        int doubleCount = order.getDoubleCount();
        int amountModel = order.getDictAmountModel();
        BigDecimal winAmount = vo.getWinAmount().multiply(new BigDecimal(doubleCount)).divide(new BigDecimal(amountModel)).setScale(BigDecimal.ROUND_FLOOR, 4);

        order.setWinAmount(winAmount);
        logger.info("--------------- 对奖. 投注单id: " + order.getId() + ", 中奖金额: " + order.getWinAmount().doubleValue());
        order.setWinCount(vo.getWinCount());
        logger.info("--------------- 对奖. 投注单id: " + order.getId() + ", 中奖注数: " + vo.getWinCount());
    }

    /**
     * 设置投注状态
     * @param order
     */
    public void setStatus(Order order) {
        Integer status = 0; // 对奖状态, 0
        if (order.getWinAmount().compareTo(BigDecimal.ZERO) > 0) {
            status = 2; // 已中奖
            logger.info("--------------- 对奖. 投注单id: " + order.getId() + ", 已中奖");
        } else {
            status = 1; // 未中奖
            logger.info("--------------- 对奖. 投注单id: " + order.getId() + ", 未中奖");
        }
        order.setStatus(status);
    }

    /**
     * 更新余额
     * @param order
     * @throws Exception
     */
    public void updateBalance(Order order) throws Exception {
        // 更新余额
        Plan plan = planService.getById(order.getPlanId());
        PlanConfig planConfig = plan.getPlanConfig();
        BigDecimal balance = order.getWinAmount().add(planConfig.getBalance()).setScale(BigDecimal.ROUND_FLOOR, 4);
        logger.info("--------------- 对奖. 余额id: " + planConfig.getId() + ", 原余额: " + planConfig.getBalance().doubleValue() + ", 现余额: " + balance.doubleValue());
        planConfig.setBalance(balance);
        planConfigService.saveOrUpdate(planConfig);
    }

    /**
     * 新增余额变动记录
     * @param order
     * @throws Exception
     */
    public void saveBalanceRecord(Order order) throws Exception {
        // 新增余额变动记录
        UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
        userBalanceRecord.setOrderId(order.getId());
        userBalanceRecord.setType(BalanceType.BALANCE_TYPE_2.getValue());
        userBalanceRecord.setAmount(order.getWinAmount());
        userBalanceRecordService.saveOrUpdate(userBalanceRecord);
        logger.info("--------------- 对奖. 记录id: " + userBalanceRecord.getId() + ", 变动金额: " + order.getWinAmount().doubleValue() + ", 变动类型: " + BalanceType.BALANCE_TYPE_2.getName());
    }

    /**
     * 更新计划
     * @param plan
     * @throws Exception
     */
    public void updatePlan(Plan plan) throws Exception {
        BigDecimal totalAmount = BigDecimal.ZERO; // 计划投注总额
        BigDecimal totalWinAmount = BigDecimal.ZERO; // 计划中奖总额
        List<Order> oList = orderService.getOrderListByPlanId(plan.getId());
        if (oList != null && !oList.isEmpty()) {
            for (Order order : oList) {
                totalAmount = totalAmount.add(order.getAmount());
                totalWinAmount = totalWinAmount.add(order.getWinAmount());
            }
        }
        // 更新投注计划
        if (totalWinAmount.compareTo(BigDecimal.ZERO) > 0) { // 中奖
            plan.setWinAmount(totalWinAmount);
            plan.setProfitAmount(totalWinAmount.subtract(totalAmount).setScale(BigDecimal.ROUND_FLOOR, 4));
            plan.setStatus(2); // 中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖
            logger.info("--------------- 对奖. 投注计划id: " + plan.getId() + ", 已中奖, 中奖金额: " + totalWinAmount.doubleValue() + ", 盈利金额: " + plan.getProfitAmount().doubleValue());
        } else { // 未中奖
            if (plan.getStageCount() == plan.getTotalStageCount().intValue()) { // 最后一期更新为未中奖
                plan.setStatus(1); // 中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖
                logger.info("--------------- 对奖. 投注计划id: " + plan.getId() + ", 最后一期, 未中奖, 累积投注金额: " + plan.getTotalAmount().doubleValue());
            } else {
                logger.info("--------------- 对奖. 投注计划id: " + plan.getId() + ", 未中奖, 继续投注, 累积投注金额: " + plan.getTotalAmount().doubleValue());
            }
        }
        planService.saveOrUpdate(plan);
    }

    /**
     * 更新计划配置
     * @param planConfig
     * @param isWin
     * @param isComplete
     * @throws Exception
     */
    public void updatePlanConfig(PlanConfig planConfig, boolean isWin, boolean isComplete) throws Exception {
        List<Plan> fullPlanList = planService.findList(new Plan());
        if (fullPlanList == null || fullPlanList.isEmpty()) {
            return;
        }

        // 计算累积盈利金额
        BigDecimal totalProfitAmount = BigDecimal.ZERO; // 累积盈利金额
        BigDecimal totalWinAmount = BigDecimal.ZERO; // 累积中奖金额
        BigDecimal totalAmount = BigDecimal.ZERO; // 累积投注金额
        for (Plan plan : fullPlanList) {
            totalProfitAmount = totalProfitAmount.add(plan.getProfitAmount());
            totalWinAmount = totalWinAmount.add(plan.getWinAmount());
            totalAmount = totalAmount.add(plan.getTotalAmount());
        }
        planConfig.setTotalProfitAmount(totalWinAmount.subtract(totalAmount));
        logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 累积盈利金额: " + planConfig.getTotalProfitAmount().doubleValue());

        // 是否还有追号中计划
        boolean hasPursue = false;
        for (Plan plan : fullPlanList) {
            // 判断是否仍有非此LotteryType的投注未开奖
            if (plan.getStatus() == PlanStatus.status_0.getValue() && !plan.getDictLotteryType().equals(newOpenNumber.getLotteryType())) {
                hasPursue = true;
                break;
            }
        }
        logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 是否有追号中计划: " + hasPursue);

        // 投注计划配置是否占用
        // 是否达到提现盈利率, 公式: (中奖金额-投注金额) / 充值金额 >= 提现盈利率
        BigDecimal recharge = planConfig.getRecharge(); // 充值金额
        double cashOutRate = planConfig.getTotalProfitAmount().divide(recharge, 4, BigDecimal.ROUND_FLOOR).doubleValue();
        logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 盈利率: " + cashOutRate);
        logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 提现盈利率: " + planConfig.getCashOutRate() + "可提现金额: " + planConfig.getTotalProfitAmount().doubleValue());
        if (cashOutRate >= planConfig.getCashOutRate()) {
            if (hasPursue) {
                // 已达到提现盈利率仍有非此LotteryType的投注未开奖
                planConfig.setIsOccupy(3);// 占用状态, 0停止 1未占用 2已占用 3已达到提现盈利率仍有非此LotteryType的投注未开奖
            } else {
                logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 已达到提现盈利率, 停止计划");

                // 达到提现盈利率后, 停止计划
                planConfig.setIsOccupy(0);// 占用状态, 0停止 1未占用 2已占用 3已达到提现盈利率仍有未开奖的投注

                // 备份此配置的bs_plan记录 至 bs_plan_history表
                planMapper.insertFromCopy(planConfig.getId());
                logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 备份此配置的bs_plan记录 至 bs_plan_history表");

                // 删除此配置的计划记录
                Plan plan = new Plan();
                plan.setPlanConfigId(planConfig.getId());
                planMapper.delete(plan);
                logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 删除此配置的计划记录");
            }
        } else {
            // 中奖后/总期数追完后, 重新开始
            if (!hasPursue) {
                if (isWin || isComplete) {
                    planConfig.setIsOccupy(1);// 占用状态, 0禁用 1否 2是
                    logger.info("--------------- 对奖. 投注计划配置id: " + planConfig.getId() + ", 已中奖/总期数已追完, 重新开始, 恢复占用状态: 未占用");
                }
            }
        }
        planConfigService.saveOrUpdate(planConfig);
    }

    public OpenNumber getNewOpenNumber() {
        return newOpenNumber;
    }

    public void setNewOpenNumber(OpenNumber newOpenNumber) {
        this.newOpenNumber = newOpenNumber;
    }

    public IOpenNumberExtService getOpenNumberExtService() {
        return openNumberExtService;
    }

    public void setOpenNumberExtService(IOpenNumberExtService openNumberExtService) {
        this.openNumberExtService = openNumberExtService;
    }

    public IOpenNumberService getOpenNumberService() {
        return openNumberService;
    }

    public void setOpenNumberService(IOpenNumberService openNumberService) {
        this.openNumberService = openNumberService;
    }

    public PlanMapper getPlanMapper() {
        return planMapper;
    }

    public void setPlanMapper(PlanMapper planMapper) {
        this.planMapper = planMapper;
    }

    public IOrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
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
}
