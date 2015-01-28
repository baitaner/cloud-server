/**
 * 
 */
package com.baitaner.common.exception;

/**
 *
 * @author starry
 *
 */
public class EncryptException extends ServerException {

	private static final long serialVersionUID = -7620104147348398562L;
	
	public EncryptException(Integer errorCode) {
		super(errorCode);
	}
	
	public EncryptException(Integer errorCode, Exception cause) {
		super(errorCode, cause);
	}

}
