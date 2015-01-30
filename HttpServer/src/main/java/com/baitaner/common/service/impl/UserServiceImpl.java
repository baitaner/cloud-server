package com.baitaner.common.service.impl;

import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.user.BindGroup;
import com.baitaner.common.domain.request.user.ResetPassword;
import com.baitaner.common.domain.request.user.UserRegister;
import com.baitaner.common.domain.result.*;
import com.baitaner.common.mapper.IUserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Result register(UserRegister register) {
        return null;
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updatePassword(User user, ResetPassword resetPassword) {
        return null;
    }
    @Override
    public UserResult getUser(Long userId) {
        return null;
    }

    @Override
    public UserLoginResult loginUser(String name, String password) {
        return null;
    }

    @Override
    public Result logoutUser(String session) {
        return null;
    }

    @Override
    public Result bind(User user, BindGroup bindGroup){
        return null;
    }

    @Override
    public Result unbind(User user, Long groupId) {
        return null;
    }

    @Override
    public Result update(Long userId, User user) {
        return null;
    }

    @Override
    public PasswordFindCodeResult passwordCode() {
        return null;
    }

    @Override
    public Result findPassword(String rcode, String email) {
        return null;
    }
    @Override
    public UserListResult findUserFromGroup(Long groupId, Integer limit) {
        return null;
    }
}
