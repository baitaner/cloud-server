package com.baitaner.common.domain.request.goods;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/28.
 */
public class RequestCreateIndent implements Serializable{
    private static final long serialVersionUID = -1789516973758342964L;
    private Long goodsId;
    private Integer buyCount;

    @Override
    public String toString() {
        return "RequestCreateIndent{" +
                "goodsId=" + goodsId +
                ", buyCount=" + buyCount +
                '}';
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }
}
