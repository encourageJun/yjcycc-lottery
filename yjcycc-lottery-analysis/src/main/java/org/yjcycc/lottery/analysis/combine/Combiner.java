package org.yjcycc.lottery.analysis.combine;

import org.yjcycc.lottery.analysis.combine.model.NumberCombine;
import org.yjcycc.lottery.constant.Constant;

import java.util.ArrayList;
import java.util.List;

public class Combiner extends NumberCombine {

    /**
     * 获取去除参数外的号码
     * @param number
     * @return
     */
    public List<String> getReverse(String number) {
        List<String> oneNumberCombine = getOneNumberCombine();
        String[] numbers = number.split(Constant.SEPARATOR);
        for (String num : numbers) {
            oneNumberCombine.remove(num);
        }
        return oneNumberCombine;
    }

    public synchronized List<String> calcAnyCombine(int m, int n, String[] sourceNumberArr) {
        String[] numberCombine = new String[m];
        List<String> anyNumberCombine = new ArrayList<>();
        combine(m, n, numberCombine, sourceNumberArr, anyNumberCombine);
        print("Any", anyNumberCombine);
        return anyNumberCombine;
    }

    public synchronized int calcAnyCombineSize(int m, int n, String[] sourceNumberArr) {
        return calcAnyCombine(m, n, sourceNumberArr).size();
    }

    private void print(String type, List<String> targetList) {
        /*System.out.println(type + ": ");
        for (int i = 0; i < targetList.size(); i++) {
            System.out.println((i + 1) + ": " + targetList.get(i));
        }*/
    }

    public static void main(String[] args) {
        Combiner api = new Combiner();
        List<String> numbers = api.getReverse("01,02");
        api.print("after remove", numbers);

        api.print("One", api.getOneNumberCombine());

    }

}
