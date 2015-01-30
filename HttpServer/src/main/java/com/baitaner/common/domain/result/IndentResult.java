package com.baitaner.common.domain.result;

import com.baitaner.common.domain.response.IndentResponse;

/**
 * Created by zliu on 15/1/28.
 */
public class IndentResult extends Result{

    private static final long serialVersionUID = 4664210088711527680L;
    private IndentResponse payload;

    @Override
    public String toString() {
        return "IndentResult{" +
                "payload=" + payload +
                '}';
    }

    public IndentResponse getPayload() {
        return payload;
    }

    public void setPayload(IndentResponse payload) {
        this.payload = payload;
    }
}
