/**
 * 
 */
package com.baitaner.common.mapper;

import com.baitaner.common.domain.base.User;

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
	User findByLoginName(String loginName);

}
