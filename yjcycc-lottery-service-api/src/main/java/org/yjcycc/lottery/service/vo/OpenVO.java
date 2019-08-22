package org.yjcycc.lottery.service.vo;

import org.yjcycc.lottery.entity.OpenNumber;

import java.util.List;

public class OpenVO {

    private List<OpenNumber> openList;


    public List<OpenNumber> getOpenList() {
        return openList;
    }

    public void setOpenList(List<OpenNumber> openList) {
        this.openList = openList;
    }

}
