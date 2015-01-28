/**
 * 
 */
package com.baitaner.common.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 *
 * @author starry
 *
 */
public class BasePojo implements Serializable {

	private static final long serialVersionUID = -8127187834763570914L;

	protected Long id; // auto_increment

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
