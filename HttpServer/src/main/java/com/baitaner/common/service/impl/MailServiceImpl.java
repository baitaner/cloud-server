package com.baitaner.common.service.impl;

import com.baitaner.common.domain.MailInfo;
import com.baitaner.common.service.IMailService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 13-6-17
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
@Service("mailService")
public class MailServiceImpl implements IMailService {
    private static final Logger logger = Logger.getLogger(MailServiceImpl.class
            .getSimpleName());
    /**
     * 以HTML格式发送邮件
     * @param mailInfo 待发送的邮件信息
     */
    @Override
    public boolean sendHtmlMail(MailInfo mailInfo) throws MessagingException {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        //如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中 ，可以发送多个， 用分号隔开
            String[] tos = mailInfo.getToAddress().split(";");
            InternetAddress[] toAddes = new InternetAddress[tos.length];
            for(int i=0;i<tos.length;i++){
                toAddes[i]=new InternetAddress(tos[i]);
            }
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipients(Message.RecipientType.TO, toAddes);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象

//            Multipart mainPart = new MimeMultipart();
//            // 创建一个包含HTML内容的MimeBodyPart
//            BodyPart html = new MimeBodyPart();
//            // 设置HTML内容
//            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
//            mainPart.addBodyPart(html);


            // 将MiniMultipart对象设置为邮件内容
            try {
                mailMessage.setContent(getMultipart(mailInfo.getContent(),mailInfo.getImages(),""));
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                logger.warn(e.toString());
            }
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            logger.error(ex);
            throw ex;
        }
    }

    //获取复杂邮件Multipart对象
    private Multipart getMultipart(String body,String[] bodyImg,String attachPath) throws Exception
    {
        Multipart multi=new MimeMultipart("mixed");
        multi.addBodyPart(createContent(body,bodyImg));
        if(null!=attachPath && !"".equals(attachPath))
        {
            multi.addBodyPart(createAttachment(new File(attachPath)));
        }
        return multi;
    }

    //创建正文
    private BodyPart createContent(String body,String[] bodyImg) throws Exception
    {
        BodyPart content=new MimeBodyPart();
        Multipart relate=new MimeMultipart("related");
        relate.addBodyPart(creatHtmlBody(body));
        if(null!=bodyImg && !"".equals(bodyImg) && bodyImg.length>0)
        {
            for(int i=0;i<bodyImg.length;i++)
            {
                if(bodyImg[i]!=null){
                    relate.addBodyPart(createImagePart(new File(bodyImg[i])));
                }
            }

        }
        content.setContent(relate);
        return content;
    }

    //创建图片
    private BodyPart createImagePart(File file) throws Exception
    {
        String name=file.getName();
        MimeBodyPart image=new MimeBodyPart();
        DataSource ds=new FileDataSource(file);
        image.setDataHandler(new DataHandler(ds));
        image.setFileName(name);
        image.setContentID(name);
        return image;
    }

    //创建html消息
    private BodyPart creatHtmlBody(String body) throws Exception
    {
        BodyPart html=new MimeBodyPart();
        html.setContent(body, "text/html;charset=gbk");
        return html;
    }

    //创建附件
    private BodyPart createAttachment(File file) throws Exception
    {
        BodyPart attach=new MimeBodyPart();
        DataSource ds=new FileDataSource(file);
        attach.setDataHandler(new DataHandler(ds));
        attach.setFileName(ds.getName());
        return attach;
    }
}

