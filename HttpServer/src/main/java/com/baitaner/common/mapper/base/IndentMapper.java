/**
 * 
 */
package com.baitaner.common.mapper.base;

import com.baitaner.common.domain.base.Indent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jerry
 *
 */
public interface IndentMapper {
	Integer delete(Long id);
	Integer insert(Indent indent);
	Integer update(Indent indent);
	Indent findById(Long id);
	List<Indent> findByUserId(
			@Param("userId") Long userId,
			@Param("index") int index,
			@Param("limit") int limit
	);
	Long findByUserIdSize(
			@Param("userId") Long userId
			);
	List<Indent> findByGoods(
			@Param("goodsId") Long goodsId,
			@Param("index") int index,
			@Param("limit") int limit
	);
	Long findByGoodsSize(
			@Param("goodsId") Long goodsId
	);
	List<Indent> findByGoodsAndStatus(
			@Param("goodsId") Long goodsId,
			@Param("status") Integer status,
			@Param("index") int index,
			@Param("limit") int limit
	);
	Long findByGoodsAndStatusSize(
			@Param("goodsId") Long goodsId,
			@Param("status") Integer status
	);
}
