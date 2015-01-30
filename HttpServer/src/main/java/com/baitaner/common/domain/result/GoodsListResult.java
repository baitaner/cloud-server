package com.baitaner.common.domain.result;

import com.baitaner.common.domain.response.GoodsListResponse;

/**
 * Created by zliu on 15/1/28.
 */
public class GoodsListResult extends Result{

    private static final long serialVersionUID = 6199853549631049204L;
    private GoodsListResponse payload;

    public GoodsListResponse getPayload() {
        return payload;
    }

    public void setPayload(GoodsListResponse payload) {
        this.payload = payload;
    }
}
