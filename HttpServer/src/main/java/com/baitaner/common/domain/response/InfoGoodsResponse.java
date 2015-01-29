package com.baitaner.common.domain.response;

import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.GoodsPhoto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class InfoGoodsResponse implements Serializable{
    private static final long serialVersionUID = 3765343430378874822L;
    private Goods goods;
    private List<GoodsPhoto> photoList;

    @Override
    public String toString() {
        return "InfoGoodsResponse{" +
                "goods=" + goods +
                ", photoList=" + photoList +
                '}';
    }

    public List<GoodsPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<GoodsPhoto> photoList) {
        this.photoList = photoList;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
