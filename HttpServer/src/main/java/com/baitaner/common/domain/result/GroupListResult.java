package com.baitaner.common.domain.result;

import com.baitaner.common.domain.response.GroupListResponse;

/**
 * Created by zliu on 15/1/28.
 */
public class GroupListResult extends Result{

    private static final long serialVersionUID = 1500142455738213071L;
    private GroupListResponse payload;

    public GroupListResponse getPayload() {
        return payload;
    }

    public void setPayload(GroupListResponse payload) {
        this.payload = payload;
    }
}
