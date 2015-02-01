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

	List<Goods> findByUserIdAndStatusAndLock(
			@Param("userId") Long userId,
			@Param("status") int status,
			@Param("isLock") int isLock,
			@Param("index") int index,
			@Param("limit") int limit
	);
	Long findByUserIdAndStatusAndLockSize(
			@Param("userId") Long userId,
			@Param("status") int status,
			@Param("isLock") int isLock
	);

	List<Goods> findByGroupIdAndStatusAndLock(
			@Param("groupId") Long groupId,
			@Param("status") int status,
			@Param("isLock") int isLock,
			@Param("index") int index,
			@Param("limit") int limit
	);
	Long findByGroupIdAndStatusAndLockSize(
			@Param("groupId") Long groupId,
			@Param("status") int status,
			@Param("isLock") int isLock
	);
}
