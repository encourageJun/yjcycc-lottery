package org.yjcycc.lottery.utils;

import org.apache.commons.lang.StringUtils;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.constant.dict.LotteryType;
import org.yjcycc.tools.common.util.DateUtil;

public class OpenNumberUtil {
    private final static String STAGE_NUM_PRE_EMPTY = "";
    private final static String STAGE_NUM_PRE_0 = "0";
    private final static String STAGE_NUM_PRE_00 = "00";
    private final static String STAGE_NUM_PRE_000 = "000";

    /**
     *
     * @param currStageDate
     * @param day 增量, 获取上一期传值-1, 获取下一期传值1
     * @param currStageIndex
     * @param increment 增量, 获取上一期传值-1, 获取下一期传值1
     * @param lotteryTypeLabel
     * @return
     */
    public static String getTargetStage(String currStageDate, int day, Integer currStageIndex, int increment, String lotteryTypeLabel) {
        LotteryType lotteryType = LotteryType.getByLabel(lotteryTypeLabel);
        if (lotteryType == null) {
            return null;
        }
        int targetStageIndex = calcTargetStageIndex(currStageIndex + increment, lotteryType);
        String targetStageNum = null;
        if (lotteryType.getLabel().equals(LotteryType.TYPE_3_MINUTES.getLabel())) {
            targetStageNum = combineStageNumThree(targetStageIndex);
        } else if (lotteryType.getLabel().equals(LotteryType.TYPE_1_MINUTES.getLabel())) {
            targetStageNum = combineStageNumFour(targetStageIndex);
        }
        String targetStageDate = currStageDate;
        if (currStageIndex - targetStageIndex != 1) {
            // 日期变化
            targetStageDate = calcTargetStageDate(currStageDate, day);
        }
        if (StringUtils.isEmpty(targetStageDate) || StringUtils.isEmpty(targetStageNum)) {
            return null;
        }
        return targetStageDate + Constant.STAGE_SEPARATOR + targetStageNum;
    }

    private static int calcTargetStageIndex(Integer targetStageIndex, LotteryType lotteryType) {
        int limitStageCount = lotteryType.getValue();
        if (targetStageIndex == 0) {
            targetStageIndex = limitStageCount;
        } else if (targetStageIndex > limitStageCount) {
            targetStageIndex = 1;
        }
        return targetStageIndex;
    }

    /**
     * 当前日期减一天
     * @param currStageDate
     * @return
     */
    private static String calcTargetStageDate(String currStageDate, int day) {
        try {
            return DateUtil.dateToStr(DateUtil.addDay(DateUtil.strToDate(currStageDate, DateUtil.yyyyMMdd), day));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String combineStageNumThree(Integer stageIndex) {
        if (stageIndex < 10) {
            return STAGE_NUM_PRE_00 + stageIndex;
        } else if (stageIndex >= 10 && stageIndex < 100) {
            return STAGE_NUM_PRE_0 + stageIndex;
        } else if (stageIndex >= 100 && stageIndex < 1000) {
            return STAGE_NUM_PRE_EMPTY + stageIndex;
        } else {
            return STAGE_NUM_PRE_EMPTY;
        }
    }

    private static String combineStageNumFour(Integer targetStageIndex) {
        if (targetStageIndex < 10) {
            return STAGE_NUM_PRE_000 + targetStageIndex;
        } else if (targetStageIndex >= 10 && targetStageIndex < 100) {
            return STAGE_NUM_PRE_00 + targetStageIndex;
        } else if (targetStageIndex >= 100 && targetStageIndex < 1000) {
            return STAGE_NUM_PRE_0 + targetStageIndex;
        } else if (targetStageIndex >= 1000 && targetStageIndex < 10000) {
            return STAGE_NUM_PRE_EMPTY + targetStageIndex;
        } else {
            return STAGE_NUM_PRE_EMPTY;
        }
    }

    public static String getCurrentStageDate(String currentStage) {
        if (StringUtils.isEmpty(currentStage)) {
            return null;
        }
        return currentStage.split("-")[0];
    }

    public static String getCurrentStageNum(String currentStage) {
        if (StringUtils.isEmpty(currentStage)) {
            return null;
        }
        return currentStage.split("-")[0];
    }

}
