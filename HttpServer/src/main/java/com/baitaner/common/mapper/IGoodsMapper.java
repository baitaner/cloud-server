/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author jerry
 *

 *
 */
public interface IGoodsMapper {
	Integer delete(Long id);
	Integer insert(Goods goods);
	Integer update(Goods goods);
	Integer subGoods(
			@Param("id") Long id,
			@Param("count") int count);
	Goods findById(Long id);
	List<Goods> findByInfoId(
			@Param("infoId") Long infoId,
			@Param("limit") int limit);
}
