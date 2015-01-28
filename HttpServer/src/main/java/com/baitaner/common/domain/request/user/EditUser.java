package com.baitaner.common.domain.request.user;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/28.
 */
public class EditUser implements Serializable{
    private static final long serialVersionUID = -6143217465080692611L;
    private String icon;
    private String brief;

    @Override
    public String toString() {
        return "EditUser{" +
                "icon='" + icon + '\'' +
                ", brief='" + brief + '\'' +
                '}';
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
