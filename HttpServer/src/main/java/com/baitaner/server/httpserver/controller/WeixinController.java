package com.baitaner.server.httpserver.controller;


import com.baitaner.common.domain.weixin.TextMsgVo;
import com.baitaner.common.enums.weixin.MsgTypeEnum;
import com.baitaner.common.exception.AesException;
import com.baitaner.common.service.weixin.IWeixinService;
import com.baitaner.common.service.weixin.WeixinConfig;
import com.baitaner.common.service.weixin.WeixinParserMsg;
import com.baitaner.common.service.weixin.WeixinTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA. User: zliu Date: 14-2-28 Time: 下午8:09 To change
 * this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/weixin")
public class WeixinController {
    private static final String TAG = WeixinController.class.getSimpleName();

    @Autowired
    private IWeixinService weixinService;
    /**
     {v1}/device/activate   // post
     请求头：SECRET-KEY=DEVICE_SECRET

     请求： {deviceId:厂家分配}
     返回： { code：200，
     result:0,msg:””,data:{
     serverIp:****,serverPort:”",feedId:**,accessKey:**
     }
     time:”"
     }            method = RequestMethod.GET,

     * @param echostr
     * @return
     */
    @RequestMapping(
            value = "/msg")
    public
    @ResponseBody
    String msg(
            @RequestParam String echostr,
            @RequestParam String signature,
            @RequestParam String timestamp,
            @RequestParam String nonce
    ) {
        String signa = null;
        try {
            signa = WeixinTools.getSHA1(WeixinConfig.AUTH_TOKEN, timestamp, nonce, "");
            if(signa.equals(signature)){
                return echostr;
            }
        } catch (AesException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/msg")
    public
    @ResponseBody
    String msgPort(
//            @RequestParam String signature,
//            @RequestParam String timestamp,
//            @RequestParam String nonce
            @RequestBody String xml,
            HttpServletRequest request, HttpServletResponse response
    ) {
        String responseMsg = "<xml>" +
                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%d</CreateTime>" +
                "<MsgType><![CDATA[%s]]></MsgType>" +
                "<Content><![CDATA[%s]]></Content>" +
                "</xml>";
        if(xml!=null && !xml.equals("")){
            TextMsgVo msgVo = WeixinParserMsg.parserTextMsg(xml);
            if(msgVo!=null){
                if(msgVo.getMsgType().equals(MsgTypeEnum.TEXT)){
                    String rMsg = weixinService.parserMsg(msgVo.getFromUserName(), msgVo.getContent());
                    return String.format(responseMsg,msgVo.getFromUserName(),msgVo.getToUserName(),System.currentTimeMillis(),msgVo.getMsgType().getName(),rMsg);
                }
            }
        }
        return "";
    }
}