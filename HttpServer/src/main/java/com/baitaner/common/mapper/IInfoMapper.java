/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.Info;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author jerry
 *

 *
 */
public interface IInfoMapper {
	Integer insert(Info info);
	Info findById(Long id);
	Integer update(Info info);
	List<Info> findByUserIdAndStatus(
			@Param("userId") Long userId,
			@Param("status") int status,
			@Param("limit") int limit);
	List<Info> findByGroupIdAndStatus(
			@Param("groupId") Long groupId,
			@Param("status") int status,
			@Param("limit") int limit);
}
