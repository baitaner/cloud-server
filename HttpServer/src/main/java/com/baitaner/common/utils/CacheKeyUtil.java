package com.baitaner.common.utils;

public class CacheKeyUtil {
	//缓存用户key
	// 内容User对象
	// user_{userId}:User
	private static String USER_NAME = "user";
	public static String getUserKey(long id){
		return createId(USER_NAME,id);
	}

	//物品缓存key
	// 物品Goods对象
	// goods_{goodsId}:Goods
	private static String GOODS_NAME = "goods";
	public static String getGoodsKey(long id){
		return createId(GOODS_NAME,id);
	}
	/*
	   session多点登录
         session_{session}: userId
	 */
	private static String USER_SESSION = "session";
	public static String getUserSessionKey(String session){
		return createId(USER_SESSION,session);
	}
	/*
	公司最新的已经发布的未锁定的前十条
         publish_{groupId}:[goodsId,….]

	 */
	private static String GOODS_PUBLISH_LIST = "publish";
	public static String getPublishKey(Long groupId){
		return createId(GOODS_PUBLISH_LIST,groupId);
	}

	/*
	找回密码，临时密码，有效期10分钟
          tpassword_{userId}:password;

	 */
	private static String TEMP_PASSWORD = "tpassword";
	public static String getTempPasswordKey(Long userId){
		return createId(TEMP_PASSWORD,userId);
	}
	/*
	公司认证：10分钟有效期
          gauth_{rcode}:userId
	 */
	private static String GROUP_AUTH = "gauth";
	public static String getGroupAuthKey(String rcode){
		return createId(GROUP_AUTH,rcode);
	}

	/*
	  找回密码前有个验证码，也许其他地方也会用到
          check_code{rcode}:userId
	 */
	private static String CHECK_CODE = "check_code";
	public static String getCheckCode(String code){
		return createId(CHECK_CODE,code);
	}

	private static String createId(String type,long id){
		if(type!=null && !"".equals(type.trim())){
			return type+"_" + id;
		}
		return ""+id;
	}
	private static String createId(String type,String name){
		return type+"_" + name;
	}
}
