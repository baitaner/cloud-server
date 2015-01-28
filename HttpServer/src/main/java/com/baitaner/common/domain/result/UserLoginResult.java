package com.baitaner.common.domain.result;

import com.baitaner.common.domain.response.AuthResponse;

/**
 * Created by zliu on 15/1/28.
 */
public class UserLoginResult extends Result{
    private static final long serialVersionUID = -1635558917554020587L;

    private AuthResponse payload;

    public AuthResponse getPayload() {
        return payload;
    }

    public void setPayload(AuthResponse payload) {
        this.payload = payload;
    }
}
