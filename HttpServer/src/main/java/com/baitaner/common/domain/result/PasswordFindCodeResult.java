package com.baitaner.common.domain.result;

/**
 * Created by zliu on 15/1/28.
 */
public class PasswordFindCodeResult extends Result{

    private static final long serialVersionUID = 745705963127092866L;
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
