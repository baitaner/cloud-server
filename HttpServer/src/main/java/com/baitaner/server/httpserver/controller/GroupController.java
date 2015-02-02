package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Group;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.result.GroupListResult;
import com.baitaner.common.domain.result.GroupResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.enums.UserEnums;
import com.baitaner.common.service.IGroupService;
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
@RequestMapping(value = "/group")
public class GroupController {
    private static final String TAG = GroupController.class.getSimpleName();
    @Autowired
    private IUserService userService;
    @Autowired
    private IGroupService groupService;


    @RequestMapping(method = RequestMethod.POST,
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String create(
            @RequestHeader String SESSION_KEY,
            @RequestBody Group group
    ) {
        Result response = new Result();
        if(SESSION_KEY==null ||group==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            User user = userService.getUserOnly(userId);
            if(user.getRole()!= UserEnums.ROLE.ADMIN) {
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = groupService.saveGroup(group);

        }
        return JsonUtil.object2String(response);
    }

    /**
     * 修改公司
     * @param SESSION_KEY
     * @param groupId
     * @param group
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/edit/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String edit(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long groupId,
            @RequestBody Group group
    ) {
        Result response = new Result();
        if(SESSION_KEY==null ||groupId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            User user = userService.getUserOnly(userId);
            if(user.getRole()!= UserEnums.ROLE.ADMIN) {
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = groupService.updateGroup(groupId, group);
        }
        return JsonUtil.object2String(response);
    }


    /**
     * 获取公司信息
     * @param SESSION_KEY
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/get/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String get(
            @RequestHeader String SESSION_KEY,
            @RequestParam Long groupId
    ) {
        GroupResult response = new GroupResult();
        if(SESSION_KEY==null ||groupId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            response = groupService.getGroup(groupId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 获取所有公司信息
     * @param SESSION_KEY
     * @param limit
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String list(
            @RequestHeader String SESSION_KEY,
            @RequestParam Integer index,
            @RequestParam Integer limit
    ) {
        GroupListResult response = new GroupListResult();
        if(SESSION_KEY==null ||limit==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            User user = userService.getUserOnly(userId);
            if(user.getRole()!= UserEnums.ROLE.ADMIN) {
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = groupService.find(index,limit);
        }
        return JsonUtil.object2String(response);
    }


}