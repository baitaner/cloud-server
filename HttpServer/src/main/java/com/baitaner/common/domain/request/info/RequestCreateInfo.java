package com.baitaner.common.domain.request.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class RequestCreateInfo implements Serializable{
    private static final long serialVersionUID = -1789516973758342964L;
    private Long infoId;
    private String title;
    private String expireDate;
    private List<RequestInfoGoods> infoGoodsList;

    @Override
    public String toString() {
        return "RequestCreateInfo{" +
                "infoId=" + infoId +
                ", title='" + title + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", infoGoodsList=" + infoGoodsList +
                '}';
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public List<RequestInfoGoods> getInfoGoodsList() {
        return infoGoodsList;
    }

    public void setInfoGoodsList(List<RequestInfoGoods> infoGoodsList) {
        this.infoGoodsList = infoGoodsList;
    }
}
