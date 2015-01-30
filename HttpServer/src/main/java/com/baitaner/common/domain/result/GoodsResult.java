package com.baitaner.common.domain.result;

import com.baitaner.common.domain.base.Goods;

/**
 * Created by zliu on 15/1/28.
 */
public class GoodsResult extends Result{

    private static final long serialVersionUID = -618632203449585043L;
    private Goods payload;

    public Goods getPayload() {
        return payload;
    }

    public void setPayload(Goods payload) {
        this.payload = payload;
    }
}
