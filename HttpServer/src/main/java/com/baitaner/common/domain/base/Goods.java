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

CREATE TABLE `bt_info_goods` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`title` varchar(255) NOT NULL,
`description` TEXT DEFAULT NULL,
`total` int(11) NOT NULL DEFAULT '0',
`sell_count` int(11) NOT NULL DEFAULT '0',
`info_id` BIGINT(20) NOT NULL,
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 *
 */
public class Goods extends BasePojo {
	private static final long serialVersionUID = -4131856062490713578L;
	private String title;
    private String description;
    private Integer total;
    private Integer sellCount;
    private Long infoId;
    private Timestamp createTime;
    private Timestamp updateTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
