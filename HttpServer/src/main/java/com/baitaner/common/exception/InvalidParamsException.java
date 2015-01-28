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
public class InvalidParamsException extends ServerException {

    private static final long serialVersionUID = 6220492385509023520L;

	public InvalidParamsException() {
		this(ErrorCodeConfig.INVALID_PARAMS);
	}

	public InvalidParamsException(Integer errorCode) {
        super(errorCode);
	}


}
