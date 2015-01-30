package com.baitaner.common.domain.result;

import com.baitaner.common.domain.response.UserListResponse;

/**
 * Created by zliu on 15/1/28.
 */
public class UserListResult extends Result{

    private static final long serialVersionUID = 1500142455738213071L;
    private UserListResponse payload;

    public UserListResponse getPayload() {
        return payload;
    }

    public void setPayload(UserListResponse payload) {
        this.payload = payload;
    }
}
