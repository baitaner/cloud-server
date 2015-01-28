/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.Group;
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
}
