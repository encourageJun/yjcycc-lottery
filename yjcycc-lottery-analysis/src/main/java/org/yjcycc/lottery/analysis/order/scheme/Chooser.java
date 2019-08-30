package org.yjcycc.lottery.analysis.order.scheme;

import org.yjcycc.lottery.analysis.number.Number;
import org.yjcycc.lottery.analysis.number.NumberConstructor;
import org.yjcycc.lottery.analysis.vo.OrderNumberVO;
import org.yjcycc.lottery.constant.Constant;
import org.yjcycc.lottery.constant.dict.PursueSchemeDict;
import org.yjcycc.lottery.entity.PlayCategory;
import org.yjcycc.lottery.entity.PursueScheme;
import org.yjcycc.lottery.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Chooser {

    public List<OrderNumberVO> getOrderNumber(PursueScheme pursueScheme, String danNumber, String tuoNumber) {
        PlayCategory playCategory = pursueScheme.getPlayCategory();
        PursueSchemeDict dict = PursueSchemeDict.getByValue(pursueScheme.getDictPursueScheme());
        switch (dict) {
            case scheme_1 :
                return combine(playCategory, danNumber, tuoNumber);
            case scheme_2 :
            case scheme_3 :
            case scheme_4 :
                return single(playCategory, danNumber, tuoNumber);
            default :
                return null;
        }
    }

    private List<OrderNumberVO> combine(PlayCategory playCategory, String danNumber, String tuoNumber) {
        List<OrderNumberVO> orderNumberList = new ArrayList<>();
        String[] danNumbers = StringUtils.stringToArray(danNumber, Constant.VERTICAL_SEPARATOR);
        String[] tuoNumbers = StringUtils.stringToArray(tuoNumber, Constant.VERTICAL_SEPARATOR);
        if (danNumbers != null && danNumbers.length > 0 && tuoNumbers != null && tuoNumbers.length > 0) {
            for (String dan : danNumbers) {
                for (String tuo : tuoNumbers) {
                    String orderNumber = dan + Constant.SEPARATOR + tuo;
                    List<OrderNumberVO> list = single(playCategory, orderNumber, tuoNumber);
                    orderNumberList.addAll(list);
                }
            }
        }
        return orderNumberList;
    }

    private List<OrderNumberVO> single(PlayCategory playCategory, String danNumber, String tuoNumber) {
        List<OrderNumberVO> orderNumberList = new ArrayList<>();
        Number number = NumberConstructor.getConstructor(playCategory.getClassName(), danNumber, tuoNumber, playCategory.getSingleCount());
        OrderNumberVO vo = new OrderNumberVO();
        vo.setOrderNumber(number.getOrderNumber());
        vo.setOrderCombineSize(number.getOrderCombineSize(vo.getOrderNumber()));
        vo.setPlayCategory(playCategory);
        orderNumberList.add(vo);
        return orderNumberList;
    }

}
