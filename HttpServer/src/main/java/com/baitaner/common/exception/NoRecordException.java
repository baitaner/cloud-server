package com.baitaner.common.exception;

import com.baitaner.common.constant.ErrorCodeConfig;

public class NoRecordException extends ServerException {

	private String type;

	public NoRecordException() {
		super(ErrorCodeConfig.NO_RECORD_DB);
	}

	public NoRecordException(String type) {
		this.type = type;
	}

	public NoRecordException(int errorCode, String type) {
		super(errorCode);
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
