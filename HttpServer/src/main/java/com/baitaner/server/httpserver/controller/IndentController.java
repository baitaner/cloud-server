package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.Indent;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateIndent;
import com.baitaner.common.domain.result.IDResult;
import com.baitaner.common.domain.result.IndentListResult;
import com.baitaner.common.domain.result.IndentResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.enums.UserEnums;
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
@RequestMapping(value = "/indent")
public class IndentController {
    private static final String TAG = IndentController.class.getSimpleName();
    @Autowired
    private IUserService userService;
    @Autowired
    private IIndentService indentService;
    @Autowired
    private IGoodsService goodsService;


    @RequestMapping(method = RequestMethod.POST,
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String create(
            @RequestHeader String SESSION_KEY,
            @RequestBody RequestCreateIndent createIndent
    ) {
        IDResult response = new IDResult();
        if(SESSION_KEY==null ||createIndent==null){
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
            Goods goods = goodsService.getGoodsOnly(createIndent.getGoodsId());
            if(!goods.getGroupId().equals(user.getGroupId())
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = indentService.saveIndent(user, createIndent);
        }
        return JsonUtil.object2String(response);
    }


    /**
     * 取消订单 由卖家来做。确认订单 由买家来做
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
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            User user = userService.getUserOnly(userId);
            Indent indent = indentService.getIndentOnly(indentId);
            Goods goods = goodsService.getGoodsOnly(indent.getGoodsId());
            if(!goods.getUserId().equals(userId)
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }

            response = indentService.cancelIndent(indentId);
        }
        return JsonUtil.object2String(response);
    }

    /**
     * 取消订单 由卖家来做。确认订单 由买家来做
     * @param SESSION_KEY
     * @param indentId
     * @return
     */
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
            Long userId = userService.auth(SESSION_KEY);
            if(userId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
            User user = userService.getUserOnly(userId);
            Indent indent = indentService.getIndentOnly(indentId);
            if(!indent.getUserId().equals(userId)
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
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
            value = "/get/{indentId}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody
    String getIndent(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long indentId
    ) {
        IndentResult response = new IndentResult();
        if(SESSION_KEY==null ||indentId==null){
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
            Indent indent = indentService.getIndentOnly(indentId);
            Goods goods = goodsService.getGoodsOnly(indent.getGoodsId());

            if(!indent.getUserId().equals(userId)
                    && !goods.getUserId().equals(userId)
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
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
            @RequestParam(defaultValue = "0") Integer index,
            @RequestParam(defaultValue = "0") Integer limit
    ) {
        IndentListResult response = new IndentListResult();
        if(SESSION_KEY==null ||limit==null){
            response.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            response.setMsg("Invalid params");
        }else{
            Long loginId = userService.auth(SESSION_KEY);
            if(loginId==null){
                response.setErrorCode(ErrorCodeConfig.USER_NOT_AUTHORIZED);
                response.setMsg("User no login");
                return JsonUtil.object2String(response);
            }
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
    String getFromGoods(
            @RequestHeader String SESSION_KEY,
            @PathVariable Long goodsId,
            @RequestParam(defaultValue = "0") Integer index,
            @RequestParam(defaultValue = "0") Integer limit
    ) {
        IndentListResult response = new IndentListResult();
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
            Goods goods = goodsService.getGoodsOnly(goodsId);
            if(!goods.getUserId().equals(userId)
                    && user.getRole()!= UserEnums.ROLE.ADMIN
                    ){
                response.setErrorCode(ErrorCodeConfig.NO_PERMISSION);
                response.setMsg("No permission");
                return JsonUtil.object2String(response);
            }
            response = indentService.findIndentByGoods(goodsId,index, limit);
        }
        return JsonUtil.object2String(response);
    }
}