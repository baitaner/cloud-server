package com.baitaner.common.domain.response;

import com.baitaner.common.domain.base.Goods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class GoodsListResponse implements Serializable{

    private static final long serialVersionUID = 6199853549631049204L;
    private List<Goods> goodsList;
    private Integer total;

    @Override
    public String toString() {
        return "GoodsListResult{" +
                "goodsList=" + goodsList +
                ", total=" + total +
                '}';
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
