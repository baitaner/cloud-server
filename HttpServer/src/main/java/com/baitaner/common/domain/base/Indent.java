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

CREATE TABLE `bt_indent` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`info_id` bigint(20) NOT NULL,
`user_id` bigint(20) NOT NULL,
`status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：新增订单  正在进行交易 2：取消订单 3：订单结束 交易完成',
`buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 *
 */
public class Indent extends BasePojo {
	private static final long serialVersionUID = -755818255196445741L;
	private Long goodsId;
    private Long userId;
    private Integer status;
    private Integer buyCount;
    private Timestamp buyTime;
    private Timestamp updateTime;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Timestamp buyTime) {
		this.buyTime = buyTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
