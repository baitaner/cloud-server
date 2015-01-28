package com.baitaner.common.exception;

import com.baitaner.common.constant.ErrorCodeConfig;

public class DeniedAccessException extends ServerException {


    private static final long serialVersionUID = 5292032277163262164L;

    public DeniedAccessException(){
		super(ErrorCodeConfig.DENIED_ACCESS);
	}
	public DeniedAccessException(int errorCode){
		super(errorCode);
	}

}
