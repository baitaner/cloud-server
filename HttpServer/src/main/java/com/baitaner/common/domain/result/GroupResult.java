package com.baitaner.common.domain.result;

import com.baitaner.common.domain.base.Group;

/**
 * Created by zliu on 15/1/28.
 */
public class GroupResult extends Result{

    private static final long serialVersionUID = -3108994207258757911L;
    private Group payload;

    public Group getPayload() {
        return payload;
    }

    public void setPayload(Group payload) {
        this.payload = payload;
    }
}
