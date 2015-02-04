package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateGoods;
import com.baitaner.common.domain.result.GoodsListResult;
import com.baitaner.common.domain.result.GoodsResult;
import com.baitaner.common.domain.result.IDResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.enums.GoodsEnums;
import com.baitaner.common.enums.UserEnums;
import com.baitaner.common.service.IGoodsService;
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
@RequestMapping(value = "/goods")
public class GoodsController {
    private static final String TAG = GoodsController.class.getSimpleName();
    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;

    /**
     *
     * @param SESSION_KEY
     * @param createGoods
     *  private Long goodsId;
        private String title;
        private String expireDate;
        private List<RequestGoodsGoods> goodsGoodsList;
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
            @RequestBody RequestCreateGoods createGoods
    ) {
        IDResult response = new IDResult();
        if(SESSION_KEY==null ||createGoods==null){
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
            response = goodsService.save(user.getId(),user.getGroupId(),createGoods);
        }
        return JsonUtil.object2String(response);
    }

    /**
     *
     * @param SESSION_KEY
     * @param goodsId
     * @param createGoods
     * @return
     */

    @RequestMapping(method = RequestMethod.POST,
            value = "/edit/{goodsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String edit(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId,
            @RequestBody RequestCreateGoods createGoods
    ) {
        Result response = new Result();
        if(SESSION_KEY==null ||createGoods==null ||goodsId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            Goods goods= goodsService.getGoodsOnly(goodsId);
            if(goods==null){
                response.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
                response.setMsg("No Goods");
                return JsonUtil.object2String(response);
            }
            User user = userService.getUserOnly(userId);
            if(!goods.getUserId().equals(userId) && user.getRole()!= UserEnums.ROLE.ADMIN){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            createGoods.setGoodsId(goodsId);
            response = goodsService.update(goodsId, createGoods);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 发布某个信息
     * @param SESSION_KEY
     * @param goodsId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/publish/{goodsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String publish(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null||goodsId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            Goods goods= goodsService.getGoodsOnly(goodsId);
            User user = userService.getUserOnly(userId);
            if(!goods.getUserId().equals(userId)
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = goodsService.publish(goodsId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 完成某个信息
     * @param SESSION_KEY
     * @param goodsId
     * @return
     */
//    @RequestMapping(method = RequestMethod.POST,
//            value = "/complete/{goodsId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//
//    public @ResponseBody
//    String complete(
//            @RequestHeader String SESSION_KEY,
//            @PathVariable Long goodsId
//    ) {
//        Result response = new Result();
//        if(SESSION_KEY==null||goodsId==null){
//            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
//            response.setMsg("Invalid params");
//        }else{
//            Long userId = userService.auth(SESSION_KEY);
//            if(userId==null){
//                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
//                response.setMsg("User no login");
//                return JsonUtil.object2String(response);
//            }
//            Goods goods= goodsService.getGoodsOnly(goodsId);
//            User user = userService.getUserOnly(userId);
//            if(!goods.getUserId().equals(userId)
//                    && user.getRole()!= UserEnums.ROLE.ADMIN
//                    ){
//                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
//                response.setMsg("No permission");
//                return JsonUtil.object2String(response);
//            }
//            response = goodsService.complete(goodsId);
//        }
//        return JsonUtil.object2String(response);
//    }


    /**
     * 取消某个信息
     * @param SESSION_KEY
     * @param goodsId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/cancel/{goodsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String cancel(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null||goodsId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            Goods goods= goodsService.getGoodsOnly(goodsId);
            User user = userService.getUserOnly(userId);
            if(!goods.getUserId().equals(userId)
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = goodsService.cancel(goodsId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 删除某个信息，注意权限检查， 只有在未发布的时候才允许删除
     * @param SESSION_KEY
     * @param goodsId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/delete/{goodsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String delete(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null||goodsId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            Goods goods= goodsService.getGoodsOnly(goodsId);
            User user = userService.getUserOnly(userId);
            if(!goods.getUserId().equals(userId)
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = goodsService.delete(goodsId);
        }
        return JsonUtil.object2String(response);
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/get/{goodsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String get(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId
    ) {
        GoodsResult response = new GoodsResult();
        if(SESSION_KEY==null ||goodsId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            response = goodsService.getGoods(goodsId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 获取当前用户某个状态的goods列表
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
            @RequestParam(defaultValue = "0") Integer index,
            @RequestParam(defaultValue = "0") Integer limit,
            @RequestParam Integer status,
            @RequestParam Integer isLock
    ) {
         GoodsListResult response = new GoodsListResult();
        if(SESSION_KEY==null ||limit==null || status==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long loginId = userService.auth(SESSION_KEY);
            if(loginId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            User user = userService.getUserOnly(loginId);
            if(!loginId.equals(userId)
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = goodsService.findGoodsFromUser(userId, status, isLock,index,limit);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 获取公司的信息， 根据不同状态，和锁状态获取
     * @param SESSION_KEY
     * @param groupId
     * @param limit
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/group/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String getGroupPublish(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long groupId,
            @RequestParam(defaultValue = "0") Integer index,
            @RequestParam(defaultValue = "0") Integer limit
    ) {
        GoodsListResult response = new GoodsListResult();
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
            response = goodsService.findGoodsFromGroup(groupId, GoodsEnums.STATUS.PUBLISHED, GoodsEnums.IS_LOCK.UN_LOCK,index, limit);
        }
        return JsonUtil.object2String(response);
    }
}