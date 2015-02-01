package com.baitaner.common.domain.result;

/**
 * Created by zliu on 15/1/28.
 */
public class SyncMessageResult extends Result{

    private static final long serialVersionUID = 4664210088711527680L;
    private Integer payload;

    @Override
    public String toString() {
        return "IndentResult{" +
                "payload=" + payload +
                '}';
    }

    public Integer getPayload() {
        return payload;
    }

    public void setPayload(Integer payload) {
        this.payload = payload;
    }
}
