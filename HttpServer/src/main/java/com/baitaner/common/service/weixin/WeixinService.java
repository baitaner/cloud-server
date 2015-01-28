package com.baitaner.common.service.weixin;

import org.springframework.stereotype.Service;

/**
 * Created by zliu on 15/1/15.
 */
@Service("weixinService")
public class WeixinService implements IWeixinService {

    /**
     * 根据msg解析
     * @param msg
     *     bind:email,password
     *     unbind:email,password
     *     set:[cool,heat,mode],20
     *     get:status
     * @return
     */
    @Override
    public String parserMsg(String userId, String msg){
        return "";
    }
}
