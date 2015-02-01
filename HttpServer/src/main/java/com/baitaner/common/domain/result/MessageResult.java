package com.baitaner.common.domain.result;

import com.baitaner.common.domain.history.Message;

/**
 * Created by zliu on 15/1/28.
 */
public class MessageResult extends Result{

    private static final long serialVersionUID = 4664210088711527680L;
    private Message payload;

    @Override
    public String toString() {
        return "IndentResult{" +
                "payload=" + payload +
                '}';
    }

    public Message getPayload() {
        return payload;
    }

    public void setPayload(Message payload) {
        this.payload = payload;
    }
}
