package com.baitaner.common.service.weixin;

/**
 * Created by zliu on 15/1/15.
 */
public interface IWeixinService {
    String parserMsg(String userId, String msg);
}
