package com.baitaner.common.domain.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class IndentListResponse implements Serializable{

    private static final long serialVersionUID = 8783702480892218052L;
    private List<IndentResponse> indentList;
    private Integer total;

    @Override
    public String toString() {
        return "GoodsListResult{" +
                "indentList=" + indentList +
                ", total=" + total +
                '}';
    }

    public List<IndentResponse> getIndentList() {
        return indentList;
    }

    public void setIndentList(List<IndentResponse> indentList) {
        this.indentList = indentList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
