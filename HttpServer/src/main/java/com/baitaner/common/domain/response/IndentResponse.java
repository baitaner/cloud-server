package com.baitaner.common.domain.response;

import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.GoodsPhoto;
import com.baitaner.common.domain.base.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class IndentResponse implements Serializable{
    private static final long serialVersionUID = 1236362875090649995L;
    private Long indentId;
    private Goods goods;
    private List<GoodsPhoto> photoList;
    private User user;
    private String description;
    private Integer status;
    private Integer buyCount;
    private Timestamp buyTime;

    @Override
    public String toString() {
        return "IndentResponse{" +
                "indentId=" + indentId +
                ", goods=" + goods +
                ", photoList=" + photoList +
                ", user=" + user +
                ", description=" + description +
                ", status=" + status +
                ", buyCount=" + buyCount +
                ", buyTime=" + buyTime +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<GoodsPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<GoodsPhoto> photoList) {
        this.photoList = photoList;
    }

    public Long getIndentId() {
        return indentId;
    }

    public void setIndentId(Long indentId) {
        this.indentId = indentId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Timestamp getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Timestamp buyTime) {
        this.buyTime = buyTime;
    }
}
