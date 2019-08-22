package org.yjcycc.lottery.model;

import org.yjcycc.lottery.analysis.number.Number;
import org.yjcycc.lottery.analysis.number.NumberConstructor;
import org.yjcycc.lottery.analysis.vo.CompareOpenVO;

public class OpenModel {

    public CompareOpenVO compare(CompareOpenVO vo) {
        Number number = NumberConstructor.getConstructor(vo.getPlayCategory().getClassName());
        number.compare(vo);
        return vo;
    }

}
