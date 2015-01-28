package com.baitaner.common.domain.request.user;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/28.
 */
public class ResetPassword implements Serializable {

    private static final long serialVersionUID = 112804706145760551L;
    private String old;
    private String password;
    private String resume;

    @Override
    public String toString() {
        return "PasswordFind{" +
                "old='" + old + '\'' +
                ", password='" + password + '\'' +
                ", resume='" + resume + '\'' +
                '}';
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
