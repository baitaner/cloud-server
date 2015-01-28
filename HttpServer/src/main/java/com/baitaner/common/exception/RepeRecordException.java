package com.baitaner.common.exception;

import com.baitaner.common.constant.ErrorCodeConfig;

public class RepeRecordException extends ServerException {

	private String type;

	public RepeRecordException() {
		super(ErrorCodeConfig.ALREADY_EXISTS);
	}

	public RepeRecordException(String type) {
		this();
		this.type = type;
	}

	public RepeRecordException(int errorCode, String type) {
		super(errorCode);
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
