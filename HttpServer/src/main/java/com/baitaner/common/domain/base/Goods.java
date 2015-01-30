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

CREATE TABLE `bt_info` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`title` varchar(255) NOT NULL,
`description` TEXT DEFAULT NULL,
`user_id` BIGINT(20) NOT NULL,
`group_id` BIGINT(20) NOT NULL COMMENT '考虑到更换公司的信息',
`status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：未发布（新建）2：发布 3：取消 4：完成 5：结束',
`previous_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：未发布（新建）2：发布 3：取消 4：完成 5：结束',
`is_lock` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1: 未锁定  2：锁定',
`expire_time` timestamp NULL DEFAULT NULL,
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
`publish_time` timestamp NULL DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 *
 */
public class Goods extends BasePojo {
	private static final long serialVersionUID = -8411947584379994924L;
	private String title;
    private String description;
    private Long userId;
    private Long groupId;
    private Integer status;
    private Integer previousStatus;
    private Integer isLock;
    private Integer total;
    private Integer sellCount;
    private Timestamp expireTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp publishTime;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(Integer previousStatus) {
		this.previousStatus = previousStatus;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public Timestamp getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Timestamp expireTime) {
		this.expireTime = expireTime;
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

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}
}
