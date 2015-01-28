package com.baitaner.common.service;

import com.baitaner.common.domain.MailInfo;

import javax.mail.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-5-15
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public interface IMailService {
    boolean sendHtmlMail(MailInfo mailInfo) throws MessagingException;
}
