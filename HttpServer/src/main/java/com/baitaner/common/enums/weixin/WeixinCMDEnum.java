package com.baitaner.common.enums.weixin;

/**
 * Created by zliu on 15/1/15.
 */
public enum WeixinCMDEnum {
    BIND("bind"),
    UNBIND("unbind"),
    SET("set"),
    GET("get");
    private String name;
    WeixinCMDEnum(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
