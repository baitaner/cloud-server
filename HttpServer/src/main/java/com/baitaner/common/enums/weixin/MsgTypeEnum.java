package com.baitaner.common.enums.weixin;

/**
 * Created by zliu on 15/1/15.
 */
public enum  MsgTypeEnum {
    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    VIDEO("video");
    private String name;
    MsgTypeEnum(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
