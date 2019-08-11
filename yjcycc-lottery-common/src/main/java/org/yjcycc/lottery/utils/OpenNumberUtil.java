package org.yjcycc.lottery.utils;

public class OpenNumberUtil {
    private final static String STAGE_NUM_PRE_EMPTY = "";
    private final static String STAGE_NUM_PRE_0 = "0";
    private final static String STAGE_NUM_PRE_00 = "00";
    private final static String STAGE_NUM_PRE_000 = "000";

    public static String getNextStageThreeNum(Integer stageIndex) {
        int nextIndex = stageIndex + 1;
        if (stageIndex < 10) {
            return STAGE_NUM_PRE_00 + nextIndex;
        } else if (stageIndex >= 10 && stageIndex < 100) {
            return STAGE_NUM_PRE_0 + nextIndex;
        }
        return STAGE_NUM_PRE_EMPTY + nextIndex;
    }

    public static String getNextStageFourNum(Integer stageIndex) {
        int nextIndex = stageIndex + 1;
        if (stageIndex < 10) {
            return STAGE_NUM_PRE_000 + nextIndex;
        } else if (stageIndex >= 10 && stageIndex < 100) {
            return STAGE_NUM_PRE_00 + nextIndex;
        } else if (stageIndex >= 100 && stageIndex < 1000) {
            return STAGE_NUM_PRE_0 + nextIndex;
        }
        return STAGE_NUM_PRE_EMPTY + nextIndex;
    }

}
