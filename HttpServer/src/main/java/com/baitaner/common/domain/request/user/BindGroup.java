package com.baitaner.common.domain.request.user;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/28.
 */
public class BindGroup implements Serializable {

    private static final long serialVersionUID = 7678816163661850476L;

    private Long groupId;
    private Long userId;
    private String email;

    @Override
    public String toString() {
        return "BindGroup{" +
                "groupId=" + groupId +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
