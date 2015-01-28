package com.baitaner.server.httpserver.controller;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.info.RequestCreateInfo;
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
}