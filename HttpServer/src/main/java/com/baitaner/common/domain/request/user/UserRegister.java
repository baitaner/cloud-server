package com.baitaner.common.domain.request.user;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/28.
 */
public class UserRegister implements Serializable {

    private static final long serialVersionUID = 5478856671210630725L;

    private String username;
    private String password;
    private String resume;

    @Override
    public String toString() {
        return "UserRegister{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", resume='" + resume + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
