package com.baitaner.common.service;

import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.user.BindGroup;
import com.baitaner.common.domain.request.user.ResetPassword;
import com.baitaner.common.domain.request.user.UserRegister;
import com.baitaner.common.domain.result.PasswordFindCodeResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.domain.result.UserLoginResult;
import com.baitaner.common.domain.result.UserResult;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-5-3
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
public interface IUserService {
    Result register(UserRegister register);
    UserLoginResult loginUser(String name, String password);
    Result logoutUser( String session);

    Result bind(User user,BindGroup bindGroup);
    Result unbind(User user,Long groupId);
    Result update(Long userId,User user);

    PasswordFindCodeResult passwordCode();
    Result findPassword(String rcode, String email);
    Result updatePassword(User user, ResetPassword resetPassword);

    UserResult getUser(Long userId);

}
