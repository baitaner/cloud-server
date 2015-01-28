package com.baitaner.common.domain.weixin;

import com.baitaner.common.enums.weixin.MsgTypeEnum;

import java.io.Serializable;

/**
 * Created by zliu on 15/1/15.
 */
public class TextMsgVo implements Serializable{
    private static final long serialVersionUID = -2191013061927955805L;
    private String ToUserName;
    private String FromUserName;
    private Long  CreateTime;
    private MsgTypeEnum MsgType;
    private Long MsgId;
    private String Content;

    @Override
    public String toString() {
        return "TextMsgVo{" +
                "ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", MsgType='" + MsgType + '\'' +
                ", MsgId=" + MsgId +
                ", Content='" + Content + '\'' +
                '}';
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public MsgTypeEnum getMsgType() {
        return MsgType;
    }

    public void setMsgType(MsgTypeEnum msgType) {
        MsgType = msgType;
    }

    public Long getMsgId() {
        return MsgId;
    }

    public void setMsgId(Long msgId) {
        MsgId = msgId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
