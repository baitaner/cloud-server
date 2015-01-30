package com.baitaner.common.domain.request.goods;

import com.baitaner.common.domain.base.GoodsPhoto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class RequestCreateGoods implements Serializable{
    private static final long serialVersionUID = -1789516973758342964L;
    private Long goodsId;
    private String title;
    private String description;
    private Integer total;
    private Long expireTime;  //秒
    private List<GoodsPhoto> photoList;

    @Override
    public String toString() {
        return "RequestCreateGoods{" +
                "goodsId=" + goodsId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", expireTime=" + expireTime +
                ", photoList=" + photoList +
                '}';
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public List<GoodsPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<GoodsPhoto> photoList) {
        this.photoList = photoList;
    }
}
