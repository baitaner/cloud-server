package com.baitaner.common.domain.request.user;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/28.
 */
public class VerifyBind implements Serializable {


    private static final long serialVersionUID = 1555546947077481515L;
    private String rcode;

    @Override
    public String toString() {
        return "VerifyBind{" +
                "rcode='" + rcode + '\'' +
                '}';
    }

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }
}
