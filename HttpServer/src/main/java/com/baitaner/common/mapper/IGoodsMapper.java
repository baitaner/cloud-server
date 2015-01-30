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

	Goods findById(Long id);

	Integer update(Goods goods);

	List<Goods> findByUserIdAndStatus(
			@Param("userId") Long userId,
			@Param("status") int status,
			@Param("index") int index,
			@Param("limit") int limit
	);

	List<Goods> findByGroupIdAndStatus(
			@Param("groupId") Long groupId,
			@Param("status") int status,
			@Param("index") int index,
			@Param("limit") int limit
	);

}
