package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.user.*;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.domain.result.UserListResult;
import com.baitaner.common.domain.result.UserLoginResult;
import com.baitaner.common.domain.result.UserResult;
import com.baitaner.common.service.IUserService;
import com.baitaner.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. User: zliu Date: 14-2-28 Time: 下午8:09 To change
 * this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final String TAG = UserController.class.getSimpleName();
	@Autowired
	private IUserService userService;

    /**
     “username”:””,
     “password”:””, //加密后，md5
     “resume”:”"
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String register(
            @RequestBody UserRegister userRegister) {
        Result result = new Result();
        if(userRegister==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("Invalid params");
        }else{
            result = userService.register(userRegister);
        }
        return JsonUtil.object2String(result);
    }

    /**
     *
     * @param userLogin
     *    username
     *    password   :md5加密
     * @return  UserLoginResult
     */

    @RequestMapping(method = RequestMethod.POST,
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
      String login(
            @RequestBody UserLogin userLogin) {
        UserLoginResult response = new UserLoginResult();
        if(userLogin==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            response = userService.loginUser(userLogin.getUsername(),userLogin.getPassword());
        }
        return JsonUtil.object2String(response);
	}

    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String logout(
            @RequestHeader String SESSION_KEY) {
        Result response = new Result();
        if(SESSION_KEY==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            response = userService.logoutUser(SESSION_KEY);
        }
        return JsonUtil.object2String(response);
    }

    /**
     *
     * @param SESSION_KEY
     * @param bindGroup
     * @return   Result
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/bind",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String bind(
            @RequestHeader String SESSION_KEY,
            @RequestBody BindGroup bindGroup) {
        Result response = new Result();
        if(SESSION_KEY==null || bindGroup==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = userService.bind(user,bindGroup);
        }
        return JsonUtil.object2String(response);
    }

    /**
     *
     * @param SESSION_KEY
     * @param bindGroup
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/unbind",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String unbind(
            @RequestHeader String SESSION_KEY,
            @RequestBody BindGroup bindGroup) {
        Result response = new Result();
        if(SESSION_KEY==null || bindGroup==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = userService.unbind(user,bindGroup.getGroupId());
        }
        return JsonUtil.object2String(response);
    }

    /**
     *
     * @param SESSION_KEY
     * @param editUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/edit",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String edit(
            @RequestHeader String SESSION_KEY,
            @RequestBody EditUser editUser) {
        Result response = new Result();
        if(SESSION_KEY==null || editUser==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = userService.update(user.getId(),user);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 防止密频繁重置码重置,每分钟获取一次，有效期5分钟
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/password/code",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String passwordCode() {
        return JsonUtil.object2String(userService.passwordCode());
    }

    /**
     *
     * @param find
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/password/find",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String passwordFind(
            @RequestBody PasswordFind find
    ) {
        Result response = new Result();
        if(find==null ){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = userService.findPassword(find.getFindCode(),find.getEmail());
        }
        return JsonUtil.object2String(response);
    }

    /**
     *
     * @param resetPassword
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/password/reset",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String resetPassword(
            @RequestHeader String SESSION_KEY,
            @RequestBody ResetPassword resetPassword
    ) {
        Result response = new Result();
        if(resetPassword==null ||SESSION_KEY==null ){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = userService.updatePassword(user,resetPassword);
        }
        return JsonUtil.object2String(response);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/get",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String getUser(
            @RequestHeader String SESSION_KEY
    ) {
        UserResult response = new UserResult();
        if(SESSION_KEY==null ){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = userService.getUser(user.getId());
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 获取公司所有人员
     * @param SESSION_KEY
     * @param limit
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/group/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String userOfGroup(
            @RequestHeader String SESSION_KEY,
            @RequestParam Long groupId,
            @RequestParam Integer index,
            @RequestParam Integer limit
    ) {
        UserListResult response = new UserListResult();
        if(SESSION_KEY==null ||limit==null||groupId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = userService.findUserFromGroup(groupId,limit);
        }
        return JsonUtil.object2String(response);
    }
}