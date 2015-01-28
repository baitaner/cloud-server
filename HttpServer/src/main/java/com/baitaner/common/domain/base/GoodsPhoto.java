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

CREATE TABLE `bt_info_goods_photo` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
`goods_id` bigint(20) NOT NULL,
`photo_url` varchar(255) NOT NULL,
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
PRIMARY KEY (`id`,`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 *
 */
public class GoodsPhoto extends BasePojo {
	private static final long serialVersionUID = 3994846666573644795L;
	private Long goodsId;
    private String photoUrl;
    private Timestamp createTime;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
