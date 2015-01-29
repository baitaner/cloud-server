package com.baitaner.common.domain.result;

import com.baitaner.common.domain.base.Info;
import com.baitaner.common.domain.response.InfoGoodsResponse;

import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class InfoResult extends Result{

    private static final long serialVersionUID = -618632203449585043L;
    private Info info;
    private List<InfoGoodsResponse> infoGoodsList;

    @Override
    public String toString() {
        return "InfoResult{" +
                "info=" + info +
                ", infoGoodsList=" + infoGoodsList +
                '}';
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<InfoGoodsResponse> getInfoGoodsList() {
        return infoGoodsList;
    }

    public void setInfoGoodsList(List<InfoGoodsResponse> infoGoodsList) {
        this.infoGoodsList = infoGoodsList;
    }
}
