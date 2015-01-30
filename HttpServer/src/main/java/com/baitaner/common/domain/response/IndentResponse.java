package com.baitaner.common.domain.response;

import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.User;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zliu on 15/1/28.
 */
public class IndentResponse implements Serializable{
    private static final long serialVersionUID = 1236362875090649995L;
    private Long indentId;
    private Goods goods;
    private User user;
    private Integer buyCount;
    private Timestamp buyTime;

    @Override
    public String toString() {
        return "IndentResponse{" +
                "indentId=" + indentId +
                ", goods=" + goods +
                ", user=" + user +
                ", buyCount=" + buyCount +
                ", buyTime=" + buyTime +
                '}';
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
