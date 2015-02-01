package com.baitaner.common.domain.result;

import com.baitaner.common.domain.response.UserListResponse;

/**
 * Created by zliu on 15/1/28.
 */
public class MessageListResult extends Result{

    private static final long serialVersionUID = 747247064299645893L;
    private UserListResponse payload;

    public UserListResponse getPayload() {
        return payload;
    }

    public void setPayload(UserListResponse payload) {
        this.payload = payload;
    }
}
