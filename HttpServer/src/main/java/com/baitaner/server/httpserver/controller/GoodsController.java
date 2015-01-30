package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateGoods;
import com.baitaner.common.domain.result.GoodsListResult;
import com.baitaner.common.domain.result.GoodsResult;
import com.baitaner.common.domain.result.Result;
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
        Result response = new Result();
        if(SESSION_KEY==null ||createGoods==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = goodsService.save(user,createGoods);
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
            User user = new User();
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
            User user = new User();
            response = goodsService.publish(goodsId);
        }
        return JsonUtil.object2String(response);
    }

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
            User user = new User();
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
            User user = new User();
            response = goodsService.delete(goodsId);
        }
        return JsonUtil.object2String(response);
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/{goodsId}",
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
            User user = new User();
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
            @RequestParam Integer index,
            @RequestParam Integer limit,
            @RequestParam Integer status,
            @RequestParam Integer isLock
    ) {
         GoodsListResult response = new GoodsListResult();
        if(SESSION_KEY==null ||limit==null || status==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = goodsService.findGoodsFromUser(userId, status, isLock,index,limit);
        }
        return JsonUtil.object2String(response);
    }


    /**
     * 获取公司的信息， 根据不同状态，和锁状态获取
     * @param SESSION_KEY
     * @param groupId
     * @param limit
     * @param status
     * @param isLock
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/group/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String getFromGroup(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long groupId,
            @RequestParam Integer index,
            @RequestParam Integer limit,
            @RequestParam Integer status,
            @RequestParam Integer isLock
    ) {
        GoodsListResult response = new GoodsListResult();
        if(SESSION_KEY==null ||limit==null || status==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = goodsService.findGoodsFromGroup(groupId, status, isLock,index, limit);
        }
        return JsonUtil.object2String(response);
    }

}