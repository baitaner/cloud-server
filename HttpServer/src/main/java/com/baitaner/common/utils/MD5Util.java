/**
 * 
 */
package com.baitaner.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author starry
 *
 */
public class MD5Util {
	
	private static final Logger LOG = LoggerFactory.getLogger(MD5Util.class);

	/**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
	public static String md5Hash(String data) {
		try {
			return DigestUtils.md5Hex(data);
		} catch (Exception e) {
			LOG.error("fail to calculte md5 hash for \""+data+"\"", e);
			return null;
		}
	}
}
