/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.GoodsPhoto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jerry
 *
 */
public interface IGoodsPhotoMapper {
	Integer delete(Long id);
	Integer insert(GoodsPhoto photo);
	Integer update(GoodsPhoto photo);
	GoodsPhoto findById(Long id);
	List<GoodsPhoto> findByGoodsId(
			@Param("goodsId") Long goodsId,
			@Param("index") int index,
			@Param("limit") int limit
	);
}
