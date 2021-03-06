/**
 * 
 */
package com.baitaner.common.mapper.base;

import com.baitaner.common.domain.base.GoodsPhoto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jerry
 *
 */
public interface GoodsPhotoMapper {
	Integer delete(Long id);
	Integer deleteByGoodsId(Long goodsId);
	Integer insert(GoodsPhoto photo);
	Integer update(GoodsPhoto photo);
	GoodsPhoto findById(Long id);
	List<GoodsPhoto> findByGoodsId(
			@Param("goodsId") Long goodsId,
			@Param("index") int index,
			@Param("limit") int limit
	);

	Long findByGoodsIdSize();
}
