package org.yjcycc.lottery.entity;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.Alias;
import org.yjcycc.lottery.constant.dict.LotteryType;
import org.yjcycc.lottery.utils.OpenNumberUtil;
import org.yjcycc.tools.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 开奖号码(OpenNumber)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("OpenNumber")
public class OpenNumber extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 415821133830120891L;
    //开奖号码扩展id
    private Long extId;
    //投注计划id
    private Long planId;
    //11选5类别, 1:3分11选5 2:1分11选5
    private String lotteryType;
    //期号
    private String stage;
    //期号-前缀日期
    private String stageDate;
    //期号-后缀序号
    private String stageNum;
    //期号-数字序号
    private Integer stageIndex;
    //开奖号码
    private String openNumber;
    //开奖号码-数组
    private String[] openNumberArr;
    //开奖号码-前三
    private String pre3Number;
    //开奖号码-前三数组
    private String[] pre3NumberArr;
    //开奖号码-前二
    private String pre2Number;
    //开奖号码-前二数组
    private String[] pre2NumberArr;
    //开奖号码-第一位
    private String number1;
    //开奖号码-第二位
    private String number2;
    //开奖号码-第三位
    private String number3;
    //开奖号码-第四位
    private String number4;
    //开奖号码-第五位
    private String number5;

    private OpenNumberExt ext;


    public OpenNumber() {}

    public OpenNumber(String stage, String openNumber, String lotteryType) {
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

        this.lotteryType = lotteryType;
    }

    /**
     * 获取上一期期号
     * @return
     */
    public String getPreviousStage() {
        return OpenNumberUtil.getTargetStage(this.getStageDate(), -1, this.getStageIndex(), -1, this.getLotteryType());
    }

    /**
     * 获取下一期期号(期号 < 1000)
     * @return
     */
    public String getNextStage() {
        return OpenNumberUtil.getTargetStage(this.getStageDate(), 1, this.getStageIndex(), 1, this.getLotteryType());
    }

    public Long getExtId() {
        return extId;
    }

    public void setExtId(Long extId) {
        this.extId = extId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
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

        if (!StringUtils.isEmpty(openNumber)) {
            this.openNumberArr = openNumber.split(",");
        }
    }

    public String getPre3Number() {
        return pre3Number;
    }

    public void setPre3Number(String pre3Number) {
        this.pre3Number = pre3Number;

        if (!StringUtils.isEmpty(pre3Number)) {
            this.pre3NumberArr = pre3Number.split(",");
        }
    }

    public String getPre2Number() {
        return pre2Number;
    }

    public void setPre2Number(String pre2Number) {
        this.pre2Number = pre2Number;

        if (!StringUtils.isEmpty(pre2Number)) {
            this.pre2NumberArr = pre2Number.split(",");
        }
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

    public String[] getOpenNumberArr() {
        return openNumberArr;
    }

    public void setOpenNumberArr(String[] openNumberArr) {
        this.openNumberArr = openNumberArr;
    }

    public String[] getPre3NumberArr() {
        return pre3NumberArr;
    }

    public void setPre3NumberArr(String[] pre3NumberArr) {
        this.pre3NumberArr = pre3NumberArr;
    }

    public String[] getPre2NumberArr() {
        return pre2NumberArr;
    }

    public void setPre2NumberArr(String[] pre2NumberArr) {
        this.pre2NumberArr = pre2NumberArr;
    }

    public OpenNumberExt getExt() {
        return ext;
    }

    public void setExt(OpenNumberExt ext) {
        this.ext = ext;
    }
}