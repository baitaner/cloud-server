package com.baitaner.common.exception;

import com.baitaner.common.constant.ErrorCodeConfig;

public class DBException extends ServerException {

	private static final long serialVersionUID = -239853582778099639L;

	public DBException(){
		super(ErrorCodeConfig.DATABASE_ERROR);
	}
	
	public DBException(int errorCode){
		super(errorCode);
	}

}
