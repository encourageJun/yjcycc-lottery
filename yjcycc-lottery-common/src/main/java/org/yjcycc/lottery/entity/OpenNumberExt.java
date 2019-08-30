package org.yjcycc.lottery.entity;

import org.apache.ibatis.type.Alias;
import org.yjcycc.lottery.constant.NumberConstant;
import org.yjcycc.tools.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 开奖号码(OpenNumberExt)实体类
 *
 * @author makejava
 * @since 2019-08-18 05:08:11
 */
@Alias("OpenNumberExt")
public class OpenNumberExt extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -45722266620188712L;
    // 号码01当期连出数
    private Integer continuedInCount_01 = 0;
    // 号码02当期连出数
    private Integer continuedInCount_02 = 0;
    // 号码03当期连出数
    private Integer continuedInCount_03 = 0;
    // 号码04当期连出数
    private Integer continuedInCount_04 = 0;
    // 号码05当期连出数
    private Integer continuedInCount_05 = 0;
    // 号码06当期连出数
    private Integer continuedInCount_06 = 0;
    // 号码07当期连出数
    private Integer continuedInCount_07 = 0;
    // 号码08当期连出数
    private Integer continuedInCount_08 = 0;
    // 号码09当期连出数
    private Integer continuedInCount_09 = 0;
    // 号码10当期连出数
    private Integer continuedInCount_10 = 0;
    // 号码11当期连出数
    private Integer continuedInCount_11 = 0;
    // 号码01当期连漏数
    private Integer continuedOutCount_01 = 0;
    // 号码02当期连漏数
    private Integer continuedOutCount_02 = 0;
    // 号码03当期连漏数
    private Integer continuedOutCount_03 = 0;
    // 号码04当期连漏数
    private Integer continuedOutCount_04 = 0;
    // 号码05当期连漏数
    private Integer continuedOutCount_05 = 0;
    // 号码06当期连漏数
    private Integer continuedOutCount_06 = 0;
    // 号码07当期连漏数
    private Integer continuedOutCount_07 = 0;
    // 号码08当期连漏数
    private Integer continuedOutCount_08 = 0;
    // 号码09当期连漏数
    private Integer continuedOutCount_09 = 0;
    // 号码10当期连漏数
    private Integer continuedOutCount_10 = 0;
    // 号码11当期连漏数
    private Integer continuedOutCount_11 = 0;
    // 号码01当期左斜连数
    private Integer continuedLeftInclinedCount_01 = 0;
    // 号码02当期左斜连数
    private Integer continuedLeftInclinedCount_02 = 0;
    // 号码03当期左斜连数
    private Integer continuedLeftInclinedCount_03 = 0;
    // 号码04当期左斜连数
    private Integer continuedLeftInclinedCount_04 = 0;
    // 号码05当期左斜连数
    private Integer continuedLeftInclinedCount_05 = 0;
    // 号码06当期左斜连数
    private Integer continuedLeftInclinedCount_06 = 0;
    // 号码07当期左斜连数
    private Integer continuedLeftInclinedCount_07 = 0;
    // 号码08当期左斜连数
    private Integer continuedLeftInclinedCount_08 = 0;
    // 号码09当期左斜连数
    private Integer continuedLeftInclinedCount_09 = 0;
    // 号码10当期左斜连数
    private Integer continuedLeftInclinedCount_10 = 0;
    // 号码11当期左斜连数
    private Integer continuedLeftInclinedCount_11 = 0;
    // 号码01当期右斜连数
    private Integer continuedRightInclinedCount_01 = 0;
    // 号码02当期右斜连数
    private Integer continuedRightInclinedCount_02 = 0;
    // 号码03当期右斜连数
    private Integer continuedRightInclinedCount_03 = 0;
    // 号码04当期右斜连数
    private Integer continuedRightInclinedCount_04 = 0;
    // 号码05当期右斜连数
    private Integer continuedRightInclinedCount_05 = 0;
    // 号码06当期右斜连数
    private Integer continuedRightInclinedCount_06 = 0;
    // 号码07当期右斜连数
    private Integer continuedRightInclinedCount_07 = 0;
    // 号码08当期右斜连数
    private Integer continuedRightInclinedCount_08 = 0;
    // 号码09当期右斜连数
    private Integer continuedRightInclinedCount_09 = 0;
    // 号码10当期右斜连数
    private Integer continuedRightInclinedCount_10 = 0;
    // 号码11当期右斜连数
    private Integer continuedRightInclinedCount_11 = 0;

    public OpenNumberExt() {}

    public OpenNumberExt(OpenNumberExt previousExt, String openNumber) {
        if (previousExt == null) {
            previousExt = new OpenNumberExt();
        }
        
        if (openNumber.contains(NumberConstant.NUMBER_01)) {
            this.setContinuedInCount_01(previousExt.getContinuedInCount_01() + 1);
            this.setContinuedOutCount_01(0);
            if (previousExt.getContinuedLeftInclinedCount_02() > 0) {
                this.continuedLeftInclinedCount_01 = previousExt.getContinuedLeftInclinedCount_02() + 1;
            }
            this.continuedRightInclinedCount_01 = 1;
        } else {
            this.setContinuedInCount_01(0);
            this.setContinuedOutCount_01(previousExt.getContinuedOutCount_01() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_02)) {
            this.setContinuedInCount_02(previousExt.getContinuedInCount_02() + 1);
            this.setContinuedOutCount_02(0);
            if (previousExt.getContinuedLeftInclinedCount_03() > 0) {
                this.continuedLeftInclinedCount_02 = previousExt.getContinuedLeftInclinedCount_03() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_01() > 0) {
                this.continuedRightInclinedCount_02 = previousExt.getContinuedRightInclinedCount_01() + 1;
            }
        } else {
            this.setContinuedInCount_02(0);
            this.setContinuedOutCount_02(previousExt.getContinuedOutCount_02() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_03)) {
            this.setContinuedInCount_03(previousExt.getContinuedInCount_03() + 1);
            this.setContinuedOutCount_03(0);
            if (previousExt.getContinuedLeftInclinedCount_04() > 0) {
                this.continuedLeftInclinedCount_03 = previousExt.getContinuedLeftInclinedCount_04() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_02() > 0) {
                this.continuedRightInclinedCount_03 = previousExt.getContinuedRightInclinedCount_02() + 1;
            }
        } else {
            this.setContinuedInCount_03(0);
            this.setContinuedOutCount_03(previousExt.getContinuedOutCount_03() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_04)) {
            this.setContinuedInCount_04(previousExt.getContinuedInCount_04() + 1);
            this.setContinuedOutCount_04(0);
            if (previousExt.getContinuedLeftInclinedCount_05() > 0) {
                this.continuedLeftInclinedCount_04 = previousExt.getContinuedLeftInclinedCount_05() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_03() > 0) {
                this.continuedRightInclinedCount_04 = previousExt.getContinuedRightInclinedCount_03() + 1;
            }
        } else {
            this.setContinuedInCount_04(0);
            this.setContinuedOutCount_04(previousExt.getContinuedOutCount_04() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_05)) {
            this.setContinuedInCount_05(previousExt.getContinuedInCount_05() + 1);
            this.setContinuedOutCount_05(0);
            if (previousExt.getContinuedLeftInclinedCount_06() > 0) {
                this.continuedLeftInclinedCount_05 = previousExt.getContinuedLeftInclinedCount_06() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_04() > 0) {
                this.continuedRightInclinedCount_05 = previousExt.getContinuedRightInclinedCount_04() + 1;
            }
        } else {
            this.setContinuedInCount_05(0);
            this.setContinuedOutCount_05(previousExt.getContinuedOutCount_05() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_06)) {
            this.setContinuedInCount_06(previousExt.getContinuedInCount_06() + 1);
            this.setContinuedOutCount_06(0);
            if (previousExt.getContinuedLeftInclinedCount_07() > 0) {
                this.continuedLeftInclinedCount_06 = previousExt.getContinuedLeftInclinedCount_07() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_05() > 0) {
                this.continuedRightInclinedCount_06 = previousExt.getContinuedRightInclinedCount_05() + 1;
            }
        } else {
            this.setContinuedInCount_06(0);
            this.setContinuedOutCount_06(previousExt.getContinuedOutCount_06() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_07)) {
            this.setContinuedInCount_07(previousExt.getContinuedInCount_07() + 1);
            this.setContinuedOutCount_07(0);
            if (previousExt.getContinuedLeftInclinedCount_08() > 0) {
                this.continuedLeftInclinedCount_07 = previousExt.getContinuedLeftInclinedCount_08() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_06() > 0) {
                this.continuedRightInclinedCount_07 = previousExt.getContinuedRightInclinedCount_06() + 1;
            }
        } else {
            this.setContinuedInCount_07(0);
            this.setContinuedOutCount_07(previousExt.getContinuedOutCount_07() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_08)) {
            this.setContinuedInCount_08(previousExt.getContinuedInCount_08() + 1);
            this.setContinuedOutCount_08(0);
            if (previousExt.getContinuedLeftInclinedCount_09() > 0) {
                this.continuedLeftInclinedCount_08 = previousExt.getContinuedLeftInclinedCount_09() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_07() > 0) {
                this.continuedRightInclinedCount_08 = previousExt.getContinuedRightInclinedCount_07() + 1;
            }
        } else {
            this.setContinuedInCount_08(0);
            this.setContinuedOutCount_08(previousExt.getContinuedOutCount_08() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_09)) {
            this.setContinuedInCount_09(previousExt.getContinuedInCount_09() + 1);
            this.setContinuedOutCount_09(0);
            if (previousExt.getContinuedLeftInclinedCount_10() > 0) {
                this.continuedLeftInclinedCount_09 = previousExt.getContinuedLeftInclinedCount_10() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_08() > 0) {
                this.continuedRightInclinedCount_09 = previousExt.getContinuedRightInclinedCount_08() + 1;
            }
        } else {
            this.setContinuedInCount_09(0);
            this.setContinuedOutCount_09(previousExt.getContinuedOutCount_09() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_10)) {
            this.setContinuedInCount_10(previousExt.getContinuedInCount_10() + 1);
            this.setContinuedOutCount_10(0);
            if (previousExt.getContinuedLeftInclinedCount_11() > 0) {
                this.continuedLeftInclinedCount_10 = previousExt.getContinuedLeftInclinedCount_11() + 1;
            }
            if (previousExt.getContinuedRightInclinedCount_09() > 0) {
                this.continuedRightInclinedCount_10 = previousExt.getContinuedRightInclinedCount_09() + 1;
            }
        } else {
            this.setContinuedInCount_10(0);
            this.setContinuedOutCount_10(previousExt.getContinuedOutCount_10() + 1);
        }

        if (openNumber.contains(NumberConstant.NUMBER_11)) {
            this.setContinuedInCount_11(previousExt.getContinuedInCount_11() + 1);
            this.setContinuedOutCount_11(0);
            this.continuedLeftInclinedCount_11 = 1;
            if (previousExt.getContinuedRightInclinedCount_10() > 0) {
                this.continuedRightInclinedCount_11 = previousExt.getContinuedRightInclinedCount_10() + 1;
            }
        } else {
            this.setContinuedInCount_11(0);
            this.setContinuedOutCount_11(previousExt.getContinuedOutCount_11() + 1);
        }
    }


    public Integer getContinuedInCount_01() {
        return continuedInCount_01;
    }

    public void setContinuedInCount_01(Integer continuedInCount_01) {
        this.continuedInCount_01 = continuedInCount_01;
    }

    public Integer getContinuedInCount_02() {
        return continuedInCount_02;
    }

    public void setContinuedInCount_02(Integer continuedInCount_02) {
        this.continuedInCount_02 = continuedInCount_02;
    }

    public Integer getContinuedInCount_03() {
        return continuedInCount_03;
    }

    public void setContinuedInCount_03(Integer continuedInCount_03) {
        this.continuedInCount_03 = continuedInCount_03;
    }

    public Integer getContinuedInCount_04() {
        return continuedInCount_04;
    }

    public void setContinuedInCount_04(Integer continuedInCount_04) {
        this.continuedInCount_04 = continuedInCount_04;
    }

    public Integer getContinuedInCount_05() {
        return continuedInCount_05;
    }

    public void setContinuedInCount_05(Integer continuedInCount_05) {
        this.continuedInCount_05 = continuedInCount_05;
    }

    public Integer getContinuedInCount_06() {
        return continuedInCount_06;
    }

    public void setContinuedInCount_06(Integer continuedInCount_06) {
        this.continuedInCount_06 = continuedInCount_06;
    }

    public Integer getContinuedInCount_07() {
        return continuedInCount_07;
    }

    public void setContinuedInCount_07(Integer continuedInCount_07) {
        this.continuedInCount_07 = continuedInCount_07;
    }

    public Integer getContinuedInCount_08() {
        return continuedInCount_08;
    }

    public void setContinuedInCount_08(Integer continuedInCount_08) {
        this.continuedInCount_08 = continuedInCount_08;
    }

    public Integer getContinuedInCount_09() {
        return continuedInCount_09;
    }

    public void setContinuedInCount_09(Integer continuedInCount_09) {
        this.continuedInCount_09 = continuedInCount_09;
    }

    public Integer getContinuedInCount_10() {
        return continuedInCount_10;
    }

    public void setContinuedInCount_10(Integer continuedInCount_10) {
        this.continuedInCount_10 = continuedInCount_10;
    }

    public Integer getContinuedInCount_11() {
        return continuedInCount_11;
    }

    public void setContinuedInCount_11(Integer continuedInCount_11) {
        this.continuedInCount_11 = continuedInCount_11;
    }

    public Integer getContinuedOutCount_01() {
        return continuedOutCount_01;
    }

    public void setContinuedOutCount_01(Integer continuedOutCount_01) {
        this.continuedOutCount_01 = continuedOutCount_01;
    }

    public Integer getContinuedOutCount_02() {
        return continuedOutCount_02;
    }

    public void setContinuedOutCount_02(Integer continuedOutCount_02) {
        this.continuedOutCount_02 = continuedOutCount_02;
    }

    public Integer getContinuedOutCount_03() {
        return continuedOutCount_03;
    }

    public void setContinuedOutCount_03(Integer continuedOutCount_03) {
        this.continuedOutCount_03 = continuedOutCount_03;
    }

    public Integer getContinuedOutCount_04() {
        return continuedOutCount_04;
    }

    public void setContinuedOutCount_04(Integer continuedOutCount_04) {
        this.continuedOutCount_04 = continuedOutCount_04;
    }

    public Integer getContinuedOutCount_05() {
        return continuedOutCount_05;
    }

    public void setContinuedOutCount_05(Integer continuedOutCount_05) {
        this.continuedOutCount_05 = continuedOutCount_05;
    }

    public Integer getContinuedOutCount_06() {
        return continuedOutCount_06;
    }

    public void setContinuedOutCount_06(Integer continuedOutCount_06) {
        this.continuedOutCount_06 = continuedOutCount_06;
    }

    public Integer getContinuedOutCount_07() {
        return continuedOutCount_07;
    }

    public void setContinuedOutCount_07(Integer continuedOutCount_07) {
        this.continuedOutCount_07 = continuedOutCount_07;
    }

    public Integer getContinuedOutCount_08() {
        return continuedOutCount_08;
    }

    public void setContinuedOutCount_08(Integer continuedOutCount_08) {
        this.continuedOutCount_08 = continuedOutCount_08;
    }

    public Integer getContinuedOutCount_09() {
        return continuedOutCount_09;
    }

    public void setContinuedOutCount_09(Integer continuedOutCount_09) {
        this.continuedOutCount_09 = continuedOutCount_09;
    }

    public Integer getContinuedOutCount_10() {
        return continuedOutCount_10;
    }

    public void setContinuedOutCount_10(Integer continuedOutCount_10) {
        this.continuedOutCount_10 = continuedOutCount_10;
    }

    public Integer getContinuedOutCount_11() {
        return continuedOutCount_11;
    }

    public void setContinuedOutCount_11(Integer continuedOutCount_11) {
        this.continuedOutCount_11 = continuedOutCount_11;
    }

    public Integer getContinuedLeftInclinedCount_01() {
        return continuedLeftInclinedCount_01;
    }

    public void setContinuedLeftInclinedCount_01(Integer continuedLeftInclinedCount_01) {
        this.continuedLeftInclinedCount_01 = continuedLeftInclinedCount_01;
    }

    public Integer getContinuedLeftInclinedCount_02() {
        return continuedLeftInclinedCount_02;
    }

    public void setContinuedLeftInclinedCount_02(Integer continuedLeftInclinedCount_02) {
        this.continuedLeftInclinedCount_02 = continuedLeftInclinedCount_02;
    }

    public Integer getContinuedLeftInclinedCount_03() {
        return continuedLeftInclinedCount_03;
    }

    public void setContinuedLeftInclinedCount_03(Integer continuedLeftInclinedCount_03) {
        this.continuedLeftInclinedCount_03 = continuedLeftInclinedCount_03;
    }

    public Integer getContinuedLeftInclinedCount_04() {
        return continuedLeftInclinedCount_04;
    }

    public void setContinuedLeftInclinedCount_04(Integer continuedLeftInclinedCount_04) {
        this.continuedLeftInclinedCount_04 = continuedLeftInclinedCount_04;
    }

    public Integer getContinuedLeftInclinedCount_05() {
        return continuedLeftInclinedCount_05;
    }

    public void setContinuedLeftInclinedCount_05(Integer continuedLeftInclinedCount_05) {
        this.continuedLeftInclinedCount_05 = continuedLeftInclinedCount_05;
    }

    public Integer getContinuedLeftInclinedCount_06() {
        return continuedLeftInclinedCount_06;
    }

    public void setContinuedLeftInclinedCount_06(Integer continuedLeftInclinedCount_06) {
        this.continuedLeftInclinedCount_06 = continuedLeftInclinedCount_06;
    }

    public Integer getContinuedLeftInclinedCount_07() {
        return continuedLeftInclinedCount_07;
    }

    public void setContinuedLeftInclinedCount_07(Integer continuedLeftInclinedCount_07) {
        this.continuedLeftInclinedCount_07 = continuedLeftInclinedCount_07;
    }

    public Integer getContinuedLeftInclinedCount_08() {
        return continuedLeftInclinedCount_08;
    }

    public void setContinuedLeftInclinedCount_08(Integer continuedLeftInclinedCount_08) {
        this.continuedLeftInclinedCount_08 = continuedLeftInclinedCount_08;
    }

    public Integer getContinuedLeftInclinedCount_09() {
        return continuedLeftInclinedCount_09;
    }

    public void setContinuedLeftInclinedCount_09(Integer continuedLeftInclinedCount_09) {
        this.continuedLeftInclinedCount_09 = continuedLeftInclinedCount_09;
    }

    public Integer getContinuedLeftInclinedCount_10() {
        return continuedLeftInclinedCount_10;
    }

    public void setContinuedLeftInclinedCount_10(Integer continuedLeftInclinedCount_10) {
        this.continuedLeftInclinedCount_10 = continuedLeftInclinedCount_10;
    }

    public Integer getContinuedLeftInclinedCount_11() {
        return continuedLeftInclinedCount_11;
    }

    public void setContinuedLeftInclinedCount_11(Integer continuedLeftInclinedCount_11) {
        this.continuedLeftInclinedCount_11 = continuedLeftInclinedCount_11;
    }

    public Integer getContinuedRightInclinedCount_01() {
        return continuedRightInclinedCount_01;
    }

    public void setContinuedRightInclinedCount_01(Integer continuedRightInclinedCount_01) {
        this.continuedRightInclinedCount_01 = continuedRightInclinedCount_01;
    }

    public Integer getContinuedRightInclinedCount_02() {
        return continuedRightInclinedCount_02;
    }

    public void setContinuedRightInclinedCount_02(Integer continuedRightInclinedCount_02) {
        this.continuedRightInclinedCount_02 = continuedRightInclinedCount_02;
    }

    public Integer getContinuedRightInclinedCount_03() {
        return continuedRightInclinedCount_03;
    }

    public void setContinuedRightInclinedCount_03(Integer continuedRightInclinedCount_03) {
        this.continuedRightInclinedCount_03 = continuedRightInclinedCount_03;
    }

    public Integer getContinuedRightInclinedCount_04() {
        return continuedRightInclinedCount_04;
    }

    public void setContinuedRightInclinedCount_04(Integer continuedRightInclinedCount_04) {
        this.continuedRightInclinedCount_04 = continuedRightInclinedCount_04;
    }

    public Integer getContinuedRightInclinedCount_05() {
        return continuedRightInclinedCount_05;
    }

    public void setContinuedRightInclinedCount_05(Integer continuedRightInclinedCount_05) {
        this.continuedRightInclinedCount_05 = continuedRightInclinedCount_05;
    }

    public Integer getContinuedRightInclinedCount_06() {
        return continuedRightInclinedCount_06;
    }

    public void setContinuedRightInclinedCount_06(Integer continuedRightInclinedCount_06) {
        this.continuedRightInclinedCount_06 = continuedRightInclinedCount_06;
    }

    public Integer getContinuedRightInclinedCount_07() {
        return continuedRightInclinedCount_07;
    }

    public void setContinuedRightInclinedCount_07(Integer continuedRightInclinedCount_07) {
        this.continuedRightInclinedCount_07 = continuedRightInclinedCount_07;
    }

    public Integer getContinuedRightInclinedCount_08() {
        return continuedRightInclinedCount_08;
    }

    public void setContinuedRightInclinedCount_08(Integer continuedRightInclinedCount_08) {
        this.continuedRightInclinedCount_08 = continuedRightInclinedCount_08;
    }

    public Integer getContinuedRightInclinedCount_09() {
        return continuedRightInclinedCount_09;
    }

    public void setContinuedRightInclinedCount_09(Integer continuedRightInclinedCount_09) {
        this.continuedRightInclinedCount_09 = continuedRightInclinedCount_09;
    }

    public Integer getContinuedRightInclinedCount_10() {
        return continuedRightInclinedCount_10;
    }

    public void setContinuedRightInclinedCount_10(Integer continuedRightInclinedCount_10) {
        this.continuedRightInclinedCount_10 = continuedRightInclinedCount_10;
    }

    public Integer getContinuedRightInclinedCount_11() {
        return continuedRightInclinedCount_11;
    }

    public void setContinuedRightInclinedCount_11(Integer continuedRightInclinedCount_11) {
        this.continuedRightInclinedCount_11 = continuedRightInclinedCount_11;
    }

}