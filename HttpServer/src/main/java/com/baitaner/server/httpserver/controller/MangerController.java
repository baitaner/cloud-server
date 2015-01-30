package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.result.GoodsListResult;
import com.baitaner.common.domain.result.IndentListResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.service.IGoodsService;
import com.baitaner.common.service.IIndentService;
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
@RequestMapping(value = "/manager")
public class MangerController {
    private static final String TAG = MangerController.class.getSimpleName();
	@Autowired
	private IUserService userService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IIndentService indentService;
    /**
     *
     * @param SESSION_KEY
     * @param goodsId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/goods/lock/{goodsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String lockGoods(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null||goodsId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            response = goodsService.lock(goodsId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     *
     * @param SESSION_KEY
     * @param goodsId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/goods/unlock/{goodsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String unlockGoods(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null||goodsId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            response = goodsService.unlock(goodsId);
        }
        return JsonUtil.object2String(response);
    }


    /**
     *  获取所有公司信息的信息，根据公司区分
     * @param SESSION_KEY
     * @param groupId
     * @param isLock
     * @param limit
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/goods/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String goodsFromGroupAndLock(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long groupId,
            @RequestParam Integer isLock,
            @RequestParam Integer index,
            @RequestParam Integer limit
    ) {
        GoodsListResult response = new GoodsListResult();
        if(SESSION_KEY==null ||limit==null||groupId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = goodsService.findGoodsFromGroup(groupId, null, isLock, index,limit);
        }
        return JsonUtil.object2String(response);
    }

    /**
     获取某个用户的所有订单，用户根据SessionKey 来区分用户，并区分是否有权限获取（只有公司管理员和管理员能看到）     * @param SESSION_KEY
     * @param groupId
     * @param status
     * @param limit
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/indent/{groupId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String indentFromGroupAndStatus(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long groupId,
            @RequestParam Integer status,
            @RequestParam Integer index,
            @RequestParam Integer limit
    ) {
        IndentListResult response = new IndentListResult();
        if(SESSION_KEY==null ||limit==null||groupId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = indentService.findIndentByGroupAndStatus(groupId, status,index, limit);
        }
        return JsonUtil.object2String(response);
    }
}