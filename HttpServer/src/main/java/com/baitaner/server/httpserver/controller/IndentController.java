package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateIndent;
import com.baitaner.common.domain.result.IndentListResult;
import com.baitaner.common.domain.result.IndentResult;
import com.baitaner.common.domain.result.Result;
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
@RequestMapping(value = "/indent")
public class IndentController {
    private static final String TAG = IndentController.class.getSimpleName();
    @Autowired
    private IUserService userService;
    @Autowired
    private IIndentService indentService;


    @RequestMapping(method = RequestMethod.POST,
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String create(
            @RequestHeader String SESSION_KEY,
            @RequestBody RequestCreateIndent createIndent
    ) {
        Result response = new Result();
        if(SESSION_KEY==null ||createIndent==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = indentService.saveIndent(user, createIndent);
        }
        return JsonUtil.object2String(response);
    }


    /**
     * 取消某个订单
     * @param SESSION_KEY
     * @param indentId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/cancel/{indentId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String cancel(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long indentId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null ||indentId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = indentService.cancelIndent(indentId);
        }
        return JsonUtil.object2String(response);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/confirm/{indentId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String confirm(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long indentId
    ) {
        Result response = new Result();
        if(SESSION_KEY==null ||indentId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = indentService.confirmIndent(indentId);
        }
        return JsonUtil.object2String(response);
    }


    /**
     * 获取订单信息
     * @param SESSION_KEY
     * @param indentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/{indentId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String getFromUser(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long indentId
    ) {
        IndentResult response = new IndentResult();
        if(SESSION_KEY==null ||indentId==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = indentService.getIndent(indentId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 获取用户下的订单列表
     * @param SESSION_KEY
     * @param userId
     * @param limit
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
            @RequestParam Integer limit
    ) {
        IndentListResult response = new IndentListResult();
        if(SESSION_KEY==null ||limit==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = indentService.findIndentByUser(userId,index, limit);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 获取某个物品下所有的订单
     * @param SESSION_KEY
     * @param goodsId
     * @param limit
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/goods/{goodsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String getFromInfo(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId,
            @RequestParam Integer index,
            @RequestParam Integer limit
    ) {
        IndentListResult response = new IndentListResult();
        if(SESSION_KEY==null ||limit==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            User user = new User();
            response = indentService.findIndentByGoods(goodsId,index, limit);
        }
        return JsonUtil.object2String(response);
    }
}