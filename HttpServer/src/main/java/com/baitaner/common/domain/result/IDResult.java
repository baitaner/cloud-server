package com.baitaner.common.domain.result;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
public class IDResult extends Result{
    private static final long serialVersionUID = 5596841504916483205L;
    private Long payload;

    public Long getPayload() {
        return payload;
    }

    public void setPayload(Long payload) {
        this.payload = payload;
    }
}
