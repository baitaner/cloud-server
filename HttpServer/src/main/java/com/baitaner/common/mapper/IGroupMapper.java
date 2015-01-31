/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author jerry
 *

 *
 */
public interface IGroupMapper {

	Integer insert(Group group);

	Group findById(Long id);
	Integer update(Group group);
	Group findByName(String name);
	Group findByEmail(String email);
	Long findSize();
	List<Group> find(
			@Param("index") int index,
			@Param("limit") int limit
	);
}
