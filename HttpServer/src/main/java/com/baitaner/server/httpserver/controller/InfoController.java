package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.info.RequestCreateInfo;
import com.baitaner.common.domain.result.InfoListResult;
import com.baitaner.common.domain.result.InfoResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.service.IInfoService;
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
@RequestMapping(value = "/info")
public class InfoController {
    private static final String TAG = UserController.class.getSimpleName();
    @Autowired
    private IUserService userService;
    @Autowired
    private IInfoService infoService;

    /**
     *
     * @param SESSION_KEY
     * @param createInfo
     *  private Long infoId;
        private String title;
        private String expireDate;
        private List<RequestInfoGoods> infoGoodsList;
            private String goodsId;
            private String title;
            private String description;
            private Integer total;
            private List<GoodsPhoto> photoList;
                private Long goodsId;
                private String photoUrl;
                private Timestamp createTime;
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String create(
            @RequestHeader String SESSION_KEY,
            @RequestBody RequestCreateInfo createInfo
    ) {
        Result response = new Result();
        if(SESSION_KEY==null ||createInfo==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = infoService.saveInfo(createInfo);
        }
        return JsonUtil.object2String(response);
    }

    /**
     *
     * @param SESSION_KEY
     * @param infoId
     * @param createInfo
     * @return
     */

    @RequestMapping(method = RequestMethod.POST,
            value = "/edit/{infoId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String edit(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long infoId,
            @RequestBody RequestCreateInfo createInfo
    ) {
        Result response = new Result();
        if(SESSION_KEY==null ||createInfo==null ||infoId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            createInfo.setInfoId(infoId);
            response = infoService.updateInfo(infoId, createInfo);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 发布某个信息
     * @param SESSION_KEY
     * @param infoId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/publish/{infoId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String publish(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long infoId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null||infoId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = infoService.publish(infoId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 取消某个信息
     * @param SESSION_KEY
     * @param infoId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/cancel/{infoId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String cancel(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long infoId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null||infoId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = infoService.cancel(infoId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 删除某个信息，注意权限检查， 只有在未发布的时候才允许删除
     * @param SESSION_KEY
     * @param infoId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/delete/{infoId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String delete(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long infoId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null||infoId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = infoService.delete(infoId);
        }
        return JsonUtil.object2String(response);
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/{infoId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String get(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long infoId
    ) {
        InfoResult response = new InfoResult();
        if(SESSION_KEY==null ||infoId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = infoService.getInfo(infoId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 获取当前用户某个状态的info列表
     * @param SESSION_KEY
     * @return
     */

    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String getFromUser(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long userId,
            @RequestParam Integer limit,
            @RequestParam Integer infoStatus,
            @RequestParam Integer isLock
    ) {
         InfoListResult response = new InfoListResult();
        if(SESSION_KEY==null ||limit==null || infoStatus==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = infoService.findInfoFromUser(userId, infoStatus, isLock, limit);
        }
        return JsonUtil.object2String(response);
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/group/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String getFromGroup(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long groupId,
            @RequestParam Integer limit,
            @RequestParam Integer infoStatus,
            @RequestParam Integer isLock
    ) {
        InfoListResult response = new InfoListResult();
        if(SESSION_KEY==null ||limit==null || infoStatus==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = infoService.findInfoFromGroup(groupId, infoStatus, isLock, limit);
        }
        return JsonUtil.object2String(response);
    }
}