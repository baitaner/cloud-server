package com.baitaner.common.service.impl;

import com.baitaner.common.constant.DateConstant;
import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Group;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.user.*;
import com.baitaner.common.domain.response.AuthResponse;
import com.baitaner.common.domain.response.UserListResponse;
import com.baitaner.common.domain.result.*;
import com.baitaner.common.enums.UserEnums;
import com.baitaner.common.mapper.IGroupMapper;
import com.baitaner.common.mapper.IUserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IGroupService;
import com.baitaner.common.service.IUserService;
import com.baitaner.common.utils.ResultUtils;
import com.baitaner.common.utils.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private IUserMapper userMapper;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IGroupMapper groupMapper;

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
        return ResultUtils.getSuccess();
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updatePassword(User user, ResetPassword resetPassword) {
        //判断user是否为空，以及id是否存在
        Result result = new Result();
        if(user==null || user.getId()==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        user = userMapper.findById(user.getId());
        if(user!=null){
            if(user.getPassword().equals(resetPassword.getOld())){
                user.setPassword(resetPassword.getPassword());
                userMapper.update(user);
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
         //todo 缓存获取
        //cache.get

        User user = userMapper.findById(userId);
        UserResult userResult = new UserResult();
        if(user==null) {
            userResult.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            userResult.setMsg("NOT EXIST USER");
        } else{
            userResult.setErrorCode(ErrorCodeConfig.SUCCESS);
            userResult.setMsg("ok");
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
            //todo 写入缓存

            result.setPayload(authResponse);
            return result;
        }else {
            //todo 对比临时缓存密码对不对
        }
        result.setErrorCode(ErrorCodeConfig.WRONG_USER_PASSWORD);
        result.setMsg("WRONG_USER_PASSWORD");
        return result;
    }

    @Override
    public Result logoutUser(String session) {
        //todo 删除缓存的session
        return ResultUtils.getSuccess();
    }

    @Override
    public Result bind(User user, BindGroup bindGroup){
        Result result = new Result();
        if(user==null
                ||user.getId()==null
                ||bindGroup==null
                ||bindGroup.getEmail()==null
                ||bindGroup.getGroupId()==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }

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
        //todo 把bindGroup写入缓存保留）
        //todo 产生rcode码写入缓存
        //todo 发送邮件
        return ResultUtils.getSuccess();
    }

    /**
     * 确认绑定用户
     * @param user
     * @param vbind
     * @return
     */
    @Override
    public Result vBind(User user, VerifyBind vbind){
        Result result = new Result();
        if(user==null
                ||user.getId()==null
                ||vbind==null
                ||vbind.getRcode()==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        //todo 对比rcode是否正确
        //todo 获取bindGroup
        BindGroup bindGroup  = new BindGroup();

        //todo 从缓存取user
        user = userMapper.findById(user.getId());
        if(user==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST USER");
            return result;
        }
        user.setGroupEmail(bindGroup.getEmail());
        user.setGroupId(bindGroup.getGroupId());
        user.setStatus(UserEnums.STATUS.JOIN);
        userMapper.update(user);
        //todo 更新缓存

        return ResultUtils.getSuccess();
    }

    @Override
    public Result unbind(User user, Long groupId) {
        Result result = new Result();
        if(user==null
                ||user.getId()==null
                ||groupId==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        user = userMapper.findById(user.getId());
        if(user==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST USER");
            return result;
        }
        user.setGroupEmail(null);
        user.setGroupId(null);
        user.setStatus(UserEnums.STATUS.NO_JOIN);
        userMapper.update(user);
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
        User user = userMapper.findById(userId);
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
        return ResultUtils.getSuccess();

    }

    @Override
    public PasswordFindCodeResult passwordCode() {

        PasswordFindCodeResult result = new PasswordFindCodeResult();
        // 验证码获取（有效期）
        String rcode = SessionUtil.getTmpCode();
        //todo 写入缓存
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

        //todo 判断rcode 和缓存对比是否有效
        //获取临时密码
        String password = SessionUtil.getTmpPassword();
        //todo 临时密码写入缓存（有效期）
        //todo 把临时密码发送邮箱
        return ResultUtils.getSuccess();

    }
    @Override
    public UserListResult findUserFromGroup(Long groupId,Integer index, Integer limit) {
        UserListResult result = new UserListResult();
        if(groupId==null
                ||index==null
                ||limit==null
                ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        List<User> userList = userMapper.findByGroup(groupId,index,limit);
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.setUserList(userList);
        userListResponse.setTotal(userMapper.findByGroupSize(groupId));
        result.setPayload(userListResponse);
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("OK");
        return result;
    }
}
