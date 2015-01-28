package com.baitaner.common.service;

import com.baitaner.common.domain.base.User;

public interface ICacheService {

	/**
	 * 用戶基本信息 缓存 //user:#userid#={value} json
	 **/
	public abstract User getUser(long userId);

	public abstract void deleteUser(long userId);

	public abstract void putUser(User user);

	/**
	 * 用户session 缓存 【master 分配】 //user_session:#userid#={value}
	 */
	public abstract String getUserSession(String session);

	public abstract void putUserSession(long userId, String session);

	public abstract void deleteUserSession(long userId);

    void clearUserSession(Long userId);
}