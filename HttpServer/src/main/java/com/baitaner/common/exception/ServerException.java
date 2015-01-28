/**
 * 
 */
package com.baitaner.common.exception;

import com.baitaner.common.constant.ErrorCodeConfig;

/**
 *
 * @author starry
 *
 */
public class ServerException extends Exception {

	private static final long serialVersionUID = 2852119909114423996L;

	private Integer errorCode;
	
	public ServerException() {
		this(ErrorCodeConfig.SERVER_ERROR);
	}
	
	public ServerException(Integer errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public ServerException(Integer errorCode, Exception cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
