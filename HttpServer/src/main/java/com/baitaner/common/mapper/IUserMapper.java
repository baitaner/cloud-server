/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author jerry
 *

 *
 */
public interface IUserMapper {

	Integer insert(User user);
	
	User findById(Long id);
	
	Integer update(User user);
		
	User findByEmail(String email);
	List<User> findByGroup(
			@Param("groupId") Long groupId,
			@Param("index") int index,
			@Param("limit") int limit
	);
	User findByLoginName(String loginName);
	Long findByGroupSize(Long groupId);

}
