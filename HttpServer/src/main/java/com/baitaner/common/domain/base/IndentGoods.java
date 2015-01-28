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

CREATE TABLE `bt_indent_goods` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`indent_id` bigint(20) NOT NULL,
`goods_id` bigint(20) NOT NULL,
`buy_count` int(11) NOT NULL DEFAULT '0',
`buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 *
 */
public class IndentGoods extends BasePojo {
	private static final long serialVersionUID = 8845842948515517801L;
	private Long indentId;
    private Long goodsId;
    private Integer buyCount;
    private Timestamp buyTime;

	public Long getIndentId() {
		return indentId;
	}

	public void setIndentId(Long indentId) {
		this.indentId = indentId;
	}

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

	public Timestamp getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Timestamp buyTime) {
		this.buyTime = buyTime;
	}
}
