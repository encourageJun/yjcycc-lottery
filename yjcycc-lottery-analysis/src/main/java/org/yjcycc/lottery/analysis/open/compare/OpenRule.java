package org.yjcycc.lottery.analysis.open.compare;

public class OpenRule {

    public boolean isWin(String combineNumber, String openNumber) {
        String[] numbers = combineNumber.split(",");
        boolean winFlag = true;
        for (String num : numbers) {
            if (!openNumber.contains(num)) {
                winFlag = false;
            }
        }
        return winFlag;
    }

}
