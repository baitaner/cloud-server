package com.baitaner.common.domain.request.user;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/28.
 */
public class PasswordFind implements Serializable {

    private static final long serialVersionUID = 5663379558215501901L;
    private String findCode;
    private String email;

    @Override
    public String toString() {
        return "PasswordFind{" +
                "findCode='" + findCode + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getFindCode() {
        return findCode;
    }

    public void setFindCode(String findCode) {
        this.findCode = findCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
