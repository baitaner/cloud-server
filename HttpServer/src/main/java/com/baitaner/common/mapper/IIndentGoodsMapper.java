/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.IndentGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author jerry
 *

 *
 */
public interface IIndentGoodsMapper {
	Integer delete(Long id);
	Integer insert(IndentGoods goods);
	Integer update(IndentGoods goods);
	IndentGoods findById(Long id);
	List<IndentGoods> findByIndentId(
			@Param("indentId") Long indentId,
			@Param("limit") int limit);
}
