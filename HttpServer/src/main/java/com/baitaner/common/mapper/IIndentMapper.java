/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.Indent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jerry
 *
 */
public interface IIndentMapper {
	Integer delete(Long id);
	Integer insert(Indent indent);
	Integer update(Indent indent);
	Indent findById(Long id);
	List<Indent> findByUserId(
			@Param("userId") Long userId,
			@Param("index") int index,
			@Param("limit") int limit
	);
}
