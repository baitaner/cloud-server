package com.baitaner.common.exception;

import com.baitaner.common.constant.ErrorCodeConfig;

public class UserException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -691801359292489272L;
	/**
	 * 
	 */
	private Integer errorCode;
	public UserException(){
		this.errorCode = ErrorCodeConfig.NO_RECORD_DB;
	}
	public UserException(int errorCode){
		this.errorCode = errorCode;
	}
	public int getErrorCdoe(){
		return this.errorCode;
	}
}
