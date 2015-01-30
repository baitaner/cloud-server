package com.baitaner.common.domain.response;

import com.baitaner.common.domain.base.Group;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class GroupListResponse implements Serializable{

    private static final long serialVersionUID = -3015538171814460841L;
    private List<Group> groupList;
    private Integer total;

    @Override
    public String toString() {
        return "GroupListResponse{" +
                "groupList=" + groupList +
                ", total=" + total +
                '}';
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
