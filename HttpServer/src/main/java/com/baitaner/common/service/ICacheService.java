package com.baitaner.common.service;

import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.user.BindGroup;

public interface ICacheService {

	/**
	 * 用戶基本信息 缓存 //user:#userid#={value} json
	 **/
	public abstract User getUser(long userId);

	public abstract void deleteUser(long userId);

	public abstract void putUser(User user);

	//商品缓存
	Goods getGoods(long goodsId);

	void deleteGoods(long goodsId);

	void putGoods(Goods goods);

	/**
	 * 用户session 缓存 【master 分配】 //user_session:#userid#={value}
	 */
	public abstract Long getUserSession(String session);

	public abstract void putUserSession(long userId, String session);

	public abstract void deleteUserSession(String session);

	void putPublishList(Long groupId, Long goodsId);

	//临时密码缓存
	void putTempPassword(long userId, String password);

	String getTempPassword(long userId);

	void deleteTempPassword(Long userId);

	//公司认证验证码
	void putGroupAuth(BindGroup bindGroup, String rcode);

	BindGroup getGroupAuth(String rcode);

	void deleteGroupAuth(String rcode);

	//校验码
	void putCheckCode(Long userId, String code);

	Long getCheckCode(String rcode);

	void deleteCheckCode(String rcode);
}