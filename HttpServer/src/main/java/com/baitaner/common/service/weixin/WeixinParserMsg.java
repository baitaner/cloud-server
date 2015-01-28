package com.baitaner.common.service.weixin;

import com.baitaner.common.enums.weixin.MsgTypeEnum;
import com.baitaner.common.domain.weixin.TextMsgVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;

/**
 * Created by zliu on 15/1/15.
 */
public class WeixinParserMsg {
    /**
     <xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[fromUser]]></FromUserName>
         <CreateTime>1348831860</CreateTime>
         <MsgType><![CDATA[text]]></MsgType>
         <Content><![CDATA[this is a test]]></Content>
         <MsgId>1234567890123456</MsgId>
     </xml>
     * @param msg
     * @return
     */
    private static SAXReader reader = new SAXReader();
    public static TextMsgVo parserTextMsg(String msg){
        TextMsgVo textMsgVo = new TextMsgVo();
        Document document = null;
        try {
            document = reader.read(new ByteArrayInputStream(msg.getBytes()));
            Node node = document.selectSingleNode("/xml/ToUserName");
            textMsgVo.setToUserName(node.getText());
            node = document.selectSingleNode("/xml/FromUserName");
            textMsgVo.setFromUserName(node.getText());
            node = document.selectSingleNode("/xml/CreateTime");
            textMsgVo.setCreateTime(Long.parseLong(node.getText()));
            node = document.selectSingleNode("/xml/MsgType");
            textMsgVo.setMsgType(MsgTypeEnum.valueOf(node.getText().toUpperCase()));
            node = document.selectSingleNode("/xml/Content");
            textMsgVo.setContent(node.getText());
            node = document.selectSingleNode("/xml/MsgId");
            textMsgVo.setMsgId(Long.parseLong(node.getText()));
            return textMsgVo;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
