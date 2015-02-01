package com.baitaner.common.domain.result;

import com.baitaner.common.domain.response.GoodsResponse;

/**
 * Created by zliu on 15/1/28.
 */
public class GoodsResult extends Result{

    private static final long serialVersionUID = -618632203449585043L;
    private GoodsResponse payload;

    public GoodsResponse getPayload() {
        return payload;
    }

    public void setPayload(GoodsResponse payload) {
        this.payload = payload;
    }
}
