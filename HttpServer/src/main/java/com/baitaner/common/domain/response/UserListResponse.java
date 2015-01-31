package com.baitaner.common.domain.response;

import com.baitaner.common.domain.base.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class UserListResponse implements Serializable{

    private static final long serialVersionUID = -3015538171814460841L;
    private List<User> userList;
    private Long total;

    @Override
    public String toString() {
        return "UserListResponse{" +
                "userList=" + userList +
                ", total=" + total +
                '}';
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
