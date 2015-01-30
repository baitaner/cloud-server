/**
 * 
 */
package com.baitaner.common.domain.base;

import com.baitaner.common.domain.BasePojo;

import java.sql.Timestamp;

/**
 *
 * @author jerry
 *
 CREATE TABLE `bt_group` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
`city` varchar(255) NOT NULL,
`email_postfix` varchar(100) NOT NULL,
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 *
 */
public class Group extends BasePojo {

	private static final long serialVersionUID = -9207807832538494225L;
	private String name;
	private String city;
	private String emailPostfix;
	private Timestamp createTime;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmailPostfix() {
		return emailPostfix;
	}

	public void setEmailPostfix(String emailPostfix) {
		this.emailPostfix = emailPostfix;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
