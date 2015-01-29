package com.baitaner.common.domain.result;

import com.baitaner.common.domain.base.Info;

import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public class InfoListResult extends Result{

    private static final long serialVersionUID = -3748203261991345618L;
    private List<Info> infos;
    private Integer total;

    @Override
    public String toString() {
        return "InfoListResult{" +
                "infos=" + infos +
                ", total=" + total +
                '}';
    }

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
