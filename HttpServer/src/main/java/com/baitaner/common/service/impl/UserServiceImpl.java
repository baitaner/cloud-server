package com.baitaner.common.service.impl;

import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.constant.DateConstant;
import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.MailInfo;
import com.baitaner.common.domain.base.Group;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.user.*;
import com.baitaner.common.domain.response.AuthResponse;
import com.baitaner.common.domain.response.UserListResponse;
import com.baitaner.common.domain.result.*;
import com.baitaner.common.enums.UserEnums;
import com.baitaner.common.mapper.base.GroupMapper;
import com.baitaner.common.mapper.base.UserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IMailService;
import com.baitaner.common.service.IUserService;
import com.baitaner.common.utils.ResultUtils;
import com.baitaner.common.utils.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-4-30
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private static final Logger logger = Logger
            .getLogger(UserServiceImpl.class.getSimpleName());
    @Autowired
    private ICacheService cacheService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private IMailService mailService;

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result register(UserRegister register) {
        Result result = new Result();
        if(register==null){
           result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID PARAMS");
            return result;
        }
        if(!register.getResume().equalsIgnoreCase(register.getPassword())){
            result.setErrorCode(ErrorCodeConfig.WRONG_USER_PASSWORD);
            result.setMsg("WRONG_USER_PASSWORD");
            return result;
        }
        User user = userMapper.findByLoginName(register.getUsername());
        if(user!=null){
            result.setErrorCode(ErrorCodeConfig.USER_ALREADY_EXIST);
            result.setMsg("USER_ALREADY_EXIST");
            return result;
        }
        user = new User();
        user.setLoginName(register.getUsername());
        user.setPassword(register.getPassword());
        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        user.setRole(UserEnums.ROLE.NORMAL);
        //暂时不考虑再次确认问题
        user.setIsAuth(UserEnums.AUTH.YES);
        user.setUserName(register.getUsername());
        //如果加入公司后则改为JOIN
        user.setStatus(UserEnums.STATUS.NO_JOIN);
        userMapper.insert(user);
        cacheService.putUser(user);
        return ResultUtils.getSuccess();
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updatePassword(Long userId, ResetPassword resetPassword) {
        //判断user是否为空，以及id是否存在
        Result result = new Result();
        if(userId==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        User user = getUserOnly(userId);
        if(user!=null){
            if(user.getPassword().equals(resetPassword.getOld())){
                user.setPassword(resetPassword.getPassword());
                userMapper.update(user);
                cacheService.putUser(user);
                return ResultUtils.getSuccess();
            } else{
                result.setErrorCode(ErrorCodeConfig.WRONG_USER_PASSWORD);
                result.setMsg("WRONG_USER_PASSWORD");
                return result;
            }
        }
        result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
        result.setMsg("NOT EXIST USER");
        return result;
    }


    @Override
    public UserResult getUser(Long userId) {
        UserResult userResult = new UserResult();
        User user = getUserOnly(userId);
        if(user==null) {
            userResult.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            userResult.setMsg("NOT EXIST USER");
        } else{
            userResult.setErrorCode(ErrorCodeConfig.SUCCESS);
            userResult.setMsg("ok");
            user.setPassword(null);
            userResult.setPayload(user);
        }
        return userResult;
    }

    @Override
    public UserLoginResult loginUser(String name, String password) {
        UserLoginResult result = new UserLoginResult();
        if(name==null||password==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        User user = userMapper.findByLoginName(name);
        if(user==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST USER");
            return result;
        }
        if(user.getPassword().equals(password)){
            result.setErrorCode(ErrorCodeConfig.SUCCESS);
            result.setMsg("ok");
            AuthResponse authResponse = new AuthResponse();
            authResponse.setExpire(DateConstant.ONE_DAY);
            authResponse.setSessionKey(SessionUtil.generateSessionId());
            authResponse.setUserId(user.getId());
            //写入缓存
            cacheService.putUserSession(user.getId(),authResponse.getSessionKey());
            result.setPayload(authResponse);
            return result;
        }else {
            //对比临时缓存密码对不对
            if(cacheService.getTempPassword(user.getId()).equals(password)){
                cacheService.deleteTempPassword(user.getId());
                result.setErrorCode(ErrorCodeConfig.SUCCESS);
                result.setMsg("ok");
                AuthResponse authResponse = new AuthResponse();
                authResponse.setExpire(DateConstant.ONE_DAY);
                authResponse.setSessionKey(SessionUtil.generateSessionId());
                authResponse.setUserId(user.getId());
                //todo 增加提示修改密码
                //写入缓存
                cacheService.putUserSession(user.getId(),authResponse.getSessionKey());
                result.setPayload(authResponse);
                return result;
            }
        }
        result.setErrorCode(ErrorCodeConfig.WRONG_USER_PASSWORD);
        result.setMsg("WRONG_USER_PASSWORD");
        return result;
    }

    @Override
    public Result logoutUser(String session) {
        //删除缓存的session
        cacheService.deleteUserSession(session);
        return ResultUtils.getSuccess();
    }

    @Override
    public Result bind(Long userId, BindGroup bindGroup){
        Result result = new Result();
        if(userId==null
                ||bindGroup==null
                ||bindGroup.getEmail()==null
                ||bindGroup.getGroupId()==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        User user = getUserOnly(userId);

        Group group = groupMapper.findById(bindGroup.getGroupId());
        if(group==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST GROUP");
            return result;
        }
        //小写匹配
        if(!bindGroup.getEmail().toLowerCase().contains(group.getEmailPostfix().toLowerCase())){
            result.setErrorCode(ErrorCodeConfig.GROUP_EMAIL_ERROR);
            result.setMsg("GROUP_EMAIL_ERROR");
            return result;
        }
        //产生rcode码写入缓存
        bindGroup.setUserId(user.getId());
        String rcode = SessionUtil.getBindCode();
        cacheService.putGroupAuth(bindGroup,rcode);
        // 发送邮件
        sendCodeEmail(bindGroup.getEmail(), rcode);
        return ResultUtils.getSuccess();
    }

    /**
     * 确认绑定用户
     * @param vbind
     * @return
     */
    @Override
    public Result vBind(Long userId, VerifyBind vbind){
        Result result = new Result();
        if(userId==null
                ||vbind==null
                ||vbind.getRcode()==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        User user = getUserOnly(userId);
        //对比rcode是否正确
        BindGroup bindGroup = cacheService.getGroupAuth(vbind.getRcode());
        if(bindGroup!=null && bindGroup.getUserId()!=null){
            cacheService.deleteGroupAuth(vbind.getRcode());
            user = getUserOnly(bindGroup.getUserId());
            if(user==null){
                result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
                result.setMsg("NOT EXIST USER");
                return result;
            }
            user.setGroupEmail(bindGroup.getEmail());
            user.setGroupId(bindGroup.getGroupId());
            user.setStatus(UserEnums.STATUS.JOIN);
            userMapper.update(user);
            //更新缓存
            cacheService.putUser(user);
            return ResultUtils.getSuccess();
        }
        result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
        result.setMsg("Auth code error!");
        return result;
    }

    @Override
    public Result unbind(Long userId, Long groupId) {
        Result result = new Result();
        if(userId==null
                ||groupId==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        User user = getUserOnly(userId);
        if(user==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST USER");
            return result;
        }
        user.setGroupEmail(null);
        user.setGroupId(null);
        user.setStatus(UserEnums.STATUS.NO_JOIN);
        userMapper.update(user);
        cacheService.putUser(user);
        return ResultUtils.getSuccess();

    }

    @Override
    public Result update(Long userId, EditUser editUser) {
        Result result = new Result();
        if(userId==null
                ||editUser==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        User user = getUserOnly(userId);
        if(user==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST USER");
            return result;
        }
        if(editUser.getBrief()!=null){
            user.setUserName(editUser.getBrief());
        }
        if(editUser.getIcon()!=null){
            user.setIcon(editUser.getIcon());
        }
        userMapper.update(user);
        cacheService.putUser(user);
        return ResultUtils.getSuccess();

    }

    @Override
    public PasswordFindCodeResult passwordCode() {

        PasswordFindCodeResult result = new PasswordFindCodeResult();
        // 验证码获取（有效期）
        String rcode = SessionUtil.getTmpCode();
        // 写入缓存
        cacheService.putCheckCode(0l,rcode);
        result.setPayload(rcode);
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("OK");
        return result;

    }

    @Override
    public Result findPassword(String rcode, String email) {
        Result result = new Result();
        if(rcode==null
                ||email==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }

        // 判断rcode 和缓存对比是否有效
        Long r = cacheService.getCheckCode(rcode);
        if(r==null){
            result.setErrorCode(ErrorCodeConfig.TEMP_CODE_TIMEOUT);
            result.setMsg("rcode invalid");
            return result;
        }
        cacheService.deleteCheckCode(rcode);
        //获取临时密码
        String password = SessionUtil.getTmpPassword();
        // 临时密码写入缓存（有效期）
        User user = userMapper.findByEmail(email);
        if(user==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST USER");
            return result;
        }
        cacheService.putTempPassword(user.getId(),password);
        // 把临时密码发送邮箱
        String mail = null;
        if(user.getEmail()==null||"".equals(user.getEmail())){
            mail = user.getGroupEmail();
        } else{
            mail = user.getEmail();
        }
        sendCodeEmail(mail,password);
        return ResultUtils.getSuccess();

    }
    @Override
    public UserListResult findUserFromGroup(Long groupId,Integer index, Integer limit) {
        UserListResult result = new UserListResult();
        if(groupId==null
                ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        if(index==null){
            index = 0;
        }
        if(limit==null){
            limit= ConstConfig.GET_INFO_MAX;
        }
        UserListResponse userListResponse = new UserListResponse();
        if(limit>0) {
            List<User> userList = userMapper.findByGroup(groupId, index, limit);
            userListResponse.setUserList(userList);
        }
        userListResponse.setTotal(userMapper.findByGroupSize(groupId));
        result.setPayload(userListResponse);
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("OK");
        return result;
    }

    @Override
    public Long auth(String session) {
        return cacheService.getUserSession(session);
    }

    @Override
    public User getUserOnly(Long userId){
        try {
            User user = cacheService.getUser(userId);
            if (user == null) {
                user = userMapper.findById(userId);
                if(user!=null){
                    cacheService.putUser(user);
                }
            }
            return user;
        }catch (Exception ex){

        }
        return null;
    }

    //发送邮件通知用户
    private boolean sendCodeEmail(String email, String rcode) {
        //根据rcode生成链接
        String subject = "Welcome Baitaner Register Test System";
        MailInfo mailInfo = this.getMailInfo(subject);
        if(mailInfo==null){
            return false;
        }

        String htmlhead = "\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<title>Register Page</title>" +
                "<body style='font-family:\"Microsoft YaHei\",微软雅黑,\"Microsoft JhengHei\",华文细黑,STHeiti,MingLiu}'>" +
                "<div> Bind code:"+rcode+"</div><br/><br/><br/>" +
                "</body>" +
                "</html>";
        mailInfo.setContent(htmlhead);
        mailInfo.setToAddress(email);
        //发送邮件到email
        try {
            return mailService.sendHtmlMail(mailInfo);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }
    //发送邮件通知用户
    private boolean sendTempPasswordEmail(String email, String password) {
        //根据rcode生成链接
        String subject = "Welcome Baitaner Register Test System";
        MailInfo mailInfo = this.getMailInfo(subject);
        if(mailInfo==null){
            return false;
        }

        String htmlhead = "\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<title>Register Page</title>" +
                "<body style='font-family:\"Microsoft YaHei\",微软雅黑,\"Microsoft JhengHei\",华文细黑,STHeiti,MingLiu}'>" +
                "<div> Temp password: "+password+" </div><br/><br/><br/>" +
                "</body>" +
                "</html>";
        mailInfo.setContent(htmlhead);
        mailInfo.setToAddress(email);
        //发送邮件到email
        try {
            return mailService.sendHtmlMail(mailInfo);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    private MailInfo getMailInfo(String subject){
        MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost("smtp.126.com");
        mailInfo.setFromAddress("loganlz@126.com");
        mailInfo.setUserName("loganlz");
        mailInfo.setPassword("wangyanan@841216");

        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        try {
            mailInfo.setSubject("=?GB2312?B?" + enc.encode(subject.getBytes("GB2312")) + "?=");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
        return mailInfo;
    }
}
