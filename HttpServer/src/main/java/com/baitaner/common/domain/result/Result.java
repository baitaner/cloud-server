package com.baitaner.common.domain.result;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
public class Result implements Serializable{
	private static final long serialVersionUID = 9115886871092320515L;
    private String msg;
    private int errorCode;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
