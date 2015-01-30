package com.baitaner.common.domain.result;

import com.baitaner.common.domain.response.IndentListResponse;

/**
 * Created by zliu on 15/1/28.
 */
public class IndentListResult extends Result{

    private static final long serialVersionUID = 8783702480892218052L;
    private IndentListResponse payload;

    public IndentListResponse getPayload() {
        return payload;
    }

    public void setPayload(IndentListResponse payload) {
        this.payload = payload;
    }
}
