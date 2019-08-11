package org.yjcycc.lottery.entity;

public class OpenNumber {

    /**
     * 期号
     */
    private String stage;

    /**
     * 期号-前缀日期
     */
    private String stageDate;

    /**
     * 期号-后缀序号
     */
    private String stageNum;

    /**
     * 期号-数字序号
     */
    private Integer stageIndex;

    /**
     * 开奖号码
     */
    private String openNumber;

    /**
     * 开奖号码-数组
     */
    private String[] openNumberArr;

    /**
     * 开奖号码-前三
     */
    private String pre3Number;

    /**
     * 开奖号码-前三数组
     */
    private String[] pre3NumberArr;

    /**
     * 开奖号码-前二
     */
    private String pre2Number;

    /**
     * 开奖号码-前二数组
     */
    private String[] pre2NumberArr;

    /**
     * 开奖号码-第一位
     */
    private String number1;

    /**
     * 开奖号码-第二位
     */
    private String number2;

    /**
     * 开奖号码-第三位
     */
    private String number3;

    /**
     * 开奖号码-第四位
     */
    private String number4;

    /**
     * 开奖号码-第五位
     */
    private String number5;

    /**
     * 当期连出数
     */
    private Integer currentContinuedInCount;

    /**
     * 当期连漏数
     */
    private Integer currentContinuedOutCount;

    /**
     * 当期斜连数
     */
    private Integer currentContinuedInclinedCount;

    public OpenNumber() {}

    public OpenNumber(String stage, String openNumber) {
        this.stage = stage;
        String[] stageArr = stage.split("-");
        this.stageDate = stageArr[0];
        this.stageNum = stageArr[1];
        this.stageIndex = Integer.parseInt(this.stageNum);

        this.openNumber = openNumber;
        this.openNumberArr = openNumber.split(",");
        this.pre3Number = openNumber.substring(0, 8);
        this.pre3NumberArr = this.pre3Number.split(",");
        this.pre2Number = openNumber.substring(0, 5);
        this.pre2NumberArr = this.pre2Number.split(",");
        this.number1 = openNumberArr[0];
        this.number2 = openNumberArr[1];
        this.number3 = openNumberArr[2];
        this.number4 = openNumberArr[3];
        this.number5 = openNumberArr[4];
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStageDate() {
        return stageDate;
    }

    public void setStageDate(String stageDate) {
        this.stageDate = stageDate;
    }

    public String getStageNum() {
        return stageNum;
    }

    public void setStageNum(String stageNum) {
        this.stageNum = stageNum;
    }

    public Integer getStageIndex() {
        return stageIndex;
    }

    public void setStageIndex(Integer stageIndex) {
        this.stageIndex = stageIndex;
    }

    public String getOpenNumber() {
        return openNumber;
    }

    public void setOpenNumber(String openNumber) {
        this.openNumber = openNumber;
    }

    public String[] getOpenNumberArr() {
        return openNumberArr;
    }

    public void setOpenNumberArr(String[] openNumberArr) {
        this.openNumberArr = openNumberArr;
    }

    public String getPre3Number() {
        return pre3Number;
    }

    public void setPre3Number(String pre3Number) {
        this.pre3Number = pre3Number;
    }

    public String[] getPre3NumberArr() {
        return pre3NumberArr;
    }

    public void setPre3NumberArr(String[] pre3NumberArr) {
        this.pre3NumberArr = pre3NumberArr;
    }

    public String getPre2Number() {
        return pre2Number;
    }

    public void setPre2Number(String pre2Number) {
        this.pre2Number = pre2Number;
    }

    public String[] getPre2NumberArr() {
        return pre2NumberArr;
    }

    public void setPre2NumberArr(String[] pre2NumberArr) {
        this.pre2NumberArr = pre2NumberArr;
    }

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getNumber3() {
        return number3;
    }

    public void setNumber3(String number3) {
        this.number3 = number3;
    }

    public String getNumber4() {
        return number4;
    }

    public void setNumber4(String number4) {
        this.number4 = number4;
    }

    public String getNumber5() {
        return number5;
    }

    public void setNumber5(String number5) {
        this.number5 = number5;
    }

    public Integer getCurrentContinuedInCount() {
        return currentContinuedInCount;
    }

    public void setCurrentContinuedInCount(Integer currentContinuedInCount) {
        this.currentContinuedInCount = currentContinuedInCount;
    }

    public Integer getCurrentContinuedOutCount() {
        return currentContinuedOutCount;
    }

    public void setCurrentContinuedOutCount(Integer currentContinuedOutCount) {
        this.currentContinuedOutCount = currentContinuedOutCount;
    }

    public Integer getCurrentContinuedInclinedCount() {
        return currentContinuedInclinedCount;
    }

    public void setCurrentContinuedInclinedCount(Integer currentContinuedInclinedCount) {
        this.currentContinuedInclinedCount = currentContinuedInclinedCount;
    }
}
