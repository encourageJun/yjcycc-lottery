package org.yjcycc.lottery.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjcycc.lottery.analysis.number.Number;
import org.yjcycc.lottery.analysis.number.NumberConstructor;
import org.yjcycc.lottery.analysis.vo.CompareOpenVO;
import org.yjcycc.lottery.analysis.vo.OrderNumberVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.constant.NumberConstant;
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
import java.util.List;

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
    private IOpenNumberExtService openNumberExtService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IPlanService planService;
    @Resource
    private IUserBalanceService userBalanceService;
    @Resource
    private IUserBalanceRecordService userBalanceRecordService;


    @Resource
    private void initMapper(OpenNumberMapper openNumberMapper) {
        this.setBaseMapper(openNumberMapper);
    }

    @Override
    public void open(OpenVO openVO) throws RemoteException {
        if (openVO == null || openVO.getOpenList() == null || openVO.getOpenList().isEmpty()) {
            return;
        }

        for(OpenNumber openNumber : openVO.getOpenList()) {
            if (openNumber == null || openNumber.getLotteryType() == null
                    || StringUtils.isEmpty(openNumber.getStage()) || StringUtils.isEmpty(openNumber.getOpenNumber())) {
                continue;
            }

            OpenNumber openNumberParam = new OpenNumber();
            openNumberParam.setStage(openNumber.getStage());
            openNumber = baseMapper.get(openNumberParam);
            if (openNumber != null) {
                logger.warn("期号:"+openNumber.getStage()+"已开奖!");
                return;
            }

            OpenNumber newOpenNumber = new OpenNumber(openNumber.getStage(), openNumber.getOpenNumber(), openNumber.getLotteryType());

            String currentStage = newOpenNumber.getStage();
            String previousStage = newOpenNumber.getPreviousStage();
            String nextStage = newOpenNumber.getNextStage();
            if (StringUtils.isEmpty(previousStage) || StringUtils.isEmpty(nextStage)) {
                logger.warn("获取期号:"+currentStage+"前后期号为空!");
                continue;
            }

            OpenNumber previousOpenNumber = new OpenNumber();
            previousOpenNumber.setStage(previousStage);
            previousOpenNumber.setLotteryType(openNumber.getLotteryType());
            previousOpenNumber = baseMapper.get(previousOpenNumber);
            OpenNumberExt newOpenNumberExt = new OpenNumberExt(previousOpenNumber.getExt(), openNumber.getOpenNumber());
            newOpenNumber.setExt(newOpenNumberExt);

            // 开奖
            try {
                openProcess(newOpenNumber);
            } catch (Exception e) {
                logger.error("保存开奖号码失败, 期号:" + currentStage);
            }

            // 投注
            try {
                orderProcess(newOpenNumber);
            } catch (Exception e) {
                logger.error("投注失败, 期号:" + nextStage);
            }

            // 统计(异步)

        }
    }

    @Transactional(readOnly=false)
    public void openProcess(OpenNumber openNumber) throws RemoteException {

        // 保存开奖号码
        this.saveOrUpdate(openNumber);
        openNumberExtService.saveOrUpdate(openNumber.getExt());

        Order orderParam = new Order();
        orderParam.setStage(openNumber.getStage());
        orderParam.setLotteryType(openNumber.getLotteryType());
        List<Order> orderList = orderMapper.findList(orderParam);
        if (orderList == null || orderList.isEmpty()) {
            logger.info("未获取到待开奖的投注单!");
            return;
        }
        for (Order order : orderList) {
            // 对奖
            CompareOpenVO vo = new CompareOpenVO(order.getPlayCategory(), order.getNumber(), openNumber.getOpenNumber());
            new OpenModel().compare(vo);
            int doubleCount = order.getDoubleCount();
            int amountModel = order.getDictAmountModel();
            BigDecimal winAmount = vo.getWinAmount().multiply(new BigDecimal(doubleCount)).divide(new BigDecimal(amountModel)).setScale(BigDecimal.ROUND_FLOOR, 4);

            Integer status = 0; // 开奖状态, 0
            if (winAmount.compareTo(BigDecimal.ZERO) > 0) {
                status = 2; // 已中奖
            } else {
                status = 1; // 未中奖
            }

            // 更新投注单
            order.setStatus(status);
            order.setWinCount(vo.getWinCount());
            order.setWinAmount(winAmount);
            orderService.saveOrUpdate(order);

            // 更新投注计划
            Plan plan = order.getPlan();
            plan.setStatus(status);
            if (winAmount.compareTo(BigDecimal.ZERO) > 0) {
                plan.setWinAmount(winAmount);
                plan.setProfitAmount(winAmount.subtract(plan.getTotalAmount()).setScale(BigDecimal.ROUND_FLOOR, 4));
            }
            planService.saveOrUpdate(plan);

            if (winAmount.compareTo(BigDecimal.ZERO) > 0) {
                // 更新余额
                UserBalance userBalance = userBalanceMapper.getById(1L);
                userBalance.setBalance(winAmount.add(userBalance.getBalance()).setScale(BigDecimal.ROUND_FLOOR, 4));
                userBalanceService.saveOrUpdate(userBalance);
                // 新增余额变化记录
                UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
                userBalanceRecord.setOrderId(order.getId());
                userBalanceRecord.setType(BalanceType.BALANCE_TYPE_2.getValue());
                userBalanceRecord.setAmount(winAmount);
                userBalanceRecordService.saveOrUpdate(userBalanceRecord);
            }
        }
    }

    @Transactional(readOnly=false)
    public void orderProcess(OpenNumber openNumber) throws RemoteException {
        String pollSwitch = sysParameterMapper.getValueByCode(ParameterCode.ORDER_SWITCH); // 投注开关, 0关 1开
        if (StringUtils.isEmpty(pollSwitch) || pollSwitch.equals(Constant.STRING_ZERO)) {
            return;
        }

        // 获取追号中的投注计划
        Plan planParam = new Plan();
        planParam.setStatus(PlanStatus.status_0.getValue()); // 追号中
        List<Plan> planList = planService.findList(planParam);
        if (planList == null || planList.isEmpty()) {
            logger.info("未获取到追号中的投注单!");
            return;
        }
        for (Plan plan : planList) {

            // 投注计划配置
            PlanConfig planConfig = plan.getPlanConfig();
            if (planConfig == null) {
                logger.warn("未获取到投注计划配置!");
                continue;
            }
            // 金额模式
            Integer amountModel = planConfig.getDictAmountModel();

            OrderModel orderModel = new OrderModel();
            orderModel.setPlan(plan);
            orderModel.setPlanConfig(planConfig);
            orderModel.setSysParameterMapper(sysParameterMapper);
            // 投注号码列表
            List<OrderNumberVO> orderNumberList = orderModel.getOrderNumber();

            // 当期计划投注总额
            BigDecimal orderAmount = BigDecimal.ZERO;
            int doubleCount = 0;

            for (OrderNumberVO vo : orderNumberList) {
                BigDecimal singleOrderAmount = NumberConstant.BASE_AMOUNT.multiply(new BigDecimal(vo.getOrderCombineSize()))
                        .divide(new BigDecimal(amountModel)).setScale(4, BigDecimal.ROUND_FLOOR);
                orderAmount = orderAmount.add(singleOrderAmount);
                doubleCount = doubleCount + vo.getDoubleCount();

                Order order = new Order();
                order.setPlanId(plan.getId());
                order.setPlayCategoryId(vo.getPlayCategory().getId());
                order.setLotteryType(openNumber.getLotteryType());
                order.setStatus(OrderStatus.status_0.getValue());
                order.setDictAmountModel(amountModel);
                order.setStage(openNumber.getStage());
                order.setStageDate(openNumber.getStageDate());
                order.setStageNum(openNumber.getStageNum());
                order.setStageIndex(openNumber.getStageIndex());
                order.setDoubleCount(vo.getDoubleCount());
                order.setNumber(vo.getOrderNumber());
                order.setAmount(singleOrderAmount);
                orderService.saveOrUpdate(order);

                // 更新余额
                UserBalance userBalance = userBalanceMapper.getById(1L);
                userBalance.setBalance(userBalance.getBalance().subtract(order.getAmount()).setScale(4, BigDecimal.ROUND_FLOOR));
                userBalanceService.saveOrUpdate(userBalance);
                // 新增余额变化记录
                UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
                userBalanceRecord.setOrderId(order.getId());
                userBalanceRecord.setType(BalanceType.BALANCE_TYPE_1.getValue());
                userBalanceRecord.setAmount(order.getAmount());
                userBalanceRecordService.saveOrUpdate(userBalanceRecord);
            }

            // 更新计划
            plan.setDoubleCount(doubleCount);
            plan.setStageCount(plan.getStageCount() + 1);
            plan.setAmount(orderAmount);
            plan.setTotalAmount(plan.getTotalAmount().add(orderAmount));
            planService.saveOrUpdate(plan);

        }

    }

}