package com.baitaner.common.domain.result;

import com.baitaner.common.domain.base.User;

/**
 * Created by zliu on 15/1/28.
 */
public class UserResult extends Result{

    private static final long serialVersionUID = 3132931612380454233L;
    private User payload;

    public User getPayload() {
        return payload;
    }

    public void setPayload(User payload) {
        this.payload = payload;
    }
}
