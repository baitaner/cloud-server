package com.baitaner.common.service;

import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.user.*;
import com.baitaner.common.domain.result.*;

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

    Result bind(Long userId,BindGroup bindGroup);

    Result vBind(Long userId, VerifyBind vbind);

    Result unbind(Long userId,Long groupId);
    Result update(Long userId,EditUser user);

    PasswordFindCodeResult passwordCode();

    Result findPassword(String rcode, String email);
    Result updatePassword(Long userId, ResetPassword resetPassword);

    UserResult getUser(Long userId);

    UserListResult findUserFromGroup(Long groupId, Integer index,Integer limit);

    Long auth(String session);

    User getUserOnly(Long userId);
}
