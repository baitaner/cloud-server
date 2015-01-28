/**
 * 
 */
package com.baitaner.common.constant;

/**
 * 
 * @author starry
 * 
 */
public interface ErrorCodeConfig extends
		ServerErrorCodeConfig, UserErrorCodeConfig {

    final int SUCCESS = 0;

	final int SERVER_ERROR = 299999;

	final int DATABASE_ERROR = 300000;

}
