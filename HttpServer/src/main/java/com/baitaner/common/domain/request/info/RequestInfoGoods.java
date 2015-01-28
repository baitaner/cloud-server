package com.baitaner.common.domain.request.info;

import com.baitaner.common.domain.base.GoodsPhoto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class RequestInfoGoods implements Serializable{

    private static final long serialVersionUID = 5956921849145655938L;
    private String goodsId;
    private String title;
    private String description;
    private Integer total;
    private List<GoodsPhoto> photoList;

    @Override
    public String toString() {
        return "InfoGoods{" +
                "goodsId='" + goodsId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", photoList=" + photoList +
                '}';
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
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

    public List<GoodsPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<GoodsPhoto> photoList) {
        this.photoList = photoList;
    }
}
