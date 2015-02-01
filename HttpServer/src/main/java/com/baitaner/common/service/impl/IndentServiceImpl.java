package com.baitaner.common.service.impl;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.Indent;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateIndent;
import com.baitaner.common.domain.response.IndentListResponse;
import com.baitaner.common.domain.response.IndentResponse;
import com.baitaner.common.domain.result.IndentListResult;
import com.baitaner.common.domain.result.IndentResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.enums.IndentEnums;
import com.baitaner.common.mapper.IGoodsMapper;
import com.baitaner.common.mapper.IGoodsPhotoMapper;
import com.baitaner.common.mapper.IIndentMapper;
import com.baitaner.common.mapper.IUserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IIndentService;
import com.baitaner.common.utils.ResultUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("indentService")
public class IndentServiceImpl implements IIndentService {
    private static final Logger logger = Logger
            .getLogger(IndentServiceImpl.class.getSimpleName());

    @Autowired
    private ICacheService cacheService;

    @Autowired
    private IIndentMapper indentMapper;

    @Autowired
    private IGoodsMapper goodsMapper;

    @Autowired
    private IGoodsPhotoMapper goodsPhotoMapper;

    @Autowired
    private IUserMapper userMapper;

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result saveIndent(User user,RequestCreateIndent createIndent){
//判断user是否为空，以及id是否存在
        Result result = new Result();
        if(user==null || user.getId()==null||createIndent==null||user.getGroupId()==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Goods goods = goodsMapper.findById(createIndent.getGoodsId());
        if(goods==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST GOODS");
            return result;
        }
        //产生订单
        Indent indent = new Indent();
        indent.setBuyTime(new Timestamp(System.currentTimeMillis()));
        indent.setGoodsId(createIndent.getGoodsId());
        indent.setUserId(user.getId());
        indent.setBuyCount(createIndent.getBuyCount());
        indent.setStatus(IndentEnums.STATUS.NEW);
        indentMapper.insert(indent);
        //修改goods中的售出数量
        Integer sellCount=goods.getSellCount()+createIndent.getBuyCount();
        if(sellCount>goods.getTotal()){
            result.setErrorCode(ErrorCodeConfig.BEYOND_MAX_VALUE);
            result.setMsg("Beyond the maximum");
            return result;
        }
        goods.setSellCount(goods.getSellCount()+createIndent.getBuyCount());
        goodsMapper.update(goods);
        return ResultUtils.getSuccess();
    }

    @Override
    public Result cancelIndent(Long indentId){
        Result result = new Result();
        if(indentId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Indent indent = indentMapper.findById(indentId);
        if(indent==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST INDENT");
            return result;
        }
        indent.setStatus(IndentEnums.STATUS.CANCELED);
        indent.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        indentMapper.update(indent);

        //todo 获取缓存信息
        Goods goods = goodsMapper.findById(indent.getGoodsId());
        if(goods!=null){
            goods.setSellCount(goods.getSellCount()-indent.getBuyCount());
            if(goods.getSellCount()<0){
                goods.setSellCount(0);
            }
            goodsMapper.update(goods);
        }

        return ResultUtils.getSuccess();
    }
    @Override
    public Result confirmIndent(Long indentId) {
        Result result = new Result();
        if(indentId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Indent indent = indentMapper.findById(indentId);
        if(indent==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST INDENT");
            return result;
        }
        indent.setStatus(IndentEnums.STATUS.COMPLETED);
        indent.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        indentMapper.update(indent);
        return ResultUtils.getSuccess();
    }

    @Override
    public IndentResult getIndent(Long indentId){
        IndentResult result = new IndentResult();
        if(indentId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Indent indent = indentMapper.findById(indentId);
        if(indent==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST INDENT");
            return result;
        }
        //todo 获取缓存信息
        Goods goods = goodsMapper.findById(indent.getGoodsId());
        if(goods==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST GOODS");
            return result;
        }
        User user = userMapper.findById(indent.getUserId());
        if(user==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST USER");
            return result;
        }
        IndentResponse ir = new IndentResponse();
        ir.setBuyCount(indent.getBuyCount());
        ir.setBuyTime(indent.getBuyTime());
        ir.setGoods(goods);
        ir.setIndentId(indentId);

        ir.setUser(user);

        result.setMsg("OK");
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setPayload(ir);
        return result;

    }

    @Override
    public IndentListResult findIndentByUser(Long userId,Integer index,Integer limit){
        IndentListResult result = new IndentListResult();
        if(userId==null
                ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        if(index==null){
            index=0;
        }
        if(limit==null){
            limit=5;
        }
        IndentListResponse ilr = new IndentListResponse();
        List<Indent> indentList = indentMapper.findByUserId(userId,index,limit);
        List<IndentResponse> indentResponseList = new ArrayList<IndentResponse>();
        for(Indent i:indentList){
            IndentResponse indentResponse = new IndentResponse();
            //todo 获取缓存
            Goods goods = goodsMapper.findById(i.getGoodsId());

            indentResponse.setGoods(goods);
            indentResponse.setBuyTime(i.getBuyTime());
            indentResponse.setBuyCount(i.getBuyCount());
            indentResponse.setIndentId(i.getId());
            indentResponseList.add(indentResponse);
        }
        ilr.setIndentList(indentResponseList);
        ilr.setTotal(indentMapper.findByUserIdSize(userId));
        result.setMsg("OK");
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setPayload(ilr);
        return result;
    }

    @Override
    public IndentListResult findIndentByGoods(Long goodsId, Integer index,Integer limit) {

        IndentListResult result = new IndentListResult();
        if(goodsId==null
                ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        if(index==null){
            index=0;
        }
        if(limit==null){
            limit=5;
        }
        IndentListResponse ilr = new IndentListResponse();
        List<Indent> indentList = indentMapper.findByGoods(goodsId, index, limit);
        List<IndentResponse> indentResponseList = new ArrayList<IndentResponse>();
        for(Indent i:indentList){
            IndentResponse indentResponse = new IndentResponse();
            //todo 获取缓存
            Goods goods = goodsMapper.findById(i.getGoodsId());

            indentResponse.setGoods(goods);
            indentResponse.setBuyTime(i.getBuyTime());
            indentResponse.setBuyCount(i.getBuyCount());
            indentResponse.setIndentId(i.getId());
            indentResponseList.add(indentResponse);
        }
        ilr.setIndentList(indentResponseList);
        ilr.setTotal(indentMapper.findByGoodsSize(goodsId));
        result.setMsg("OK");
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setPayload(ilr);
        return result;

    }

    @Override
    public IndentListResult findIndentByGroupAndStatus(Long goodsId, Integer status,Integer index, Integer limit) {
        IndentListResult result = new IndentListResult();
        if(goodsId==null
                ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        if(index==null){
            index=0;
        }
        if(limit==null){
            limit=5;
        }
        if(status==null){
            status=IndentEnums.STATUS.NEW;
        }
        IndentListResponse ilr = new IndentListResponse();
        List<Indent> indentList = indentMapper.findByGoodsAndStatus(goodsId,status,index,limit);
        List<IndentResponse> indentResponseList = new ArrayList<IndentResponse>();
        for(Indent i:indentList){
            IndentResponse indentResponse = new IndentResponse();
            //todo 获取缓存
            Goods goods = goodsMapper.findById(i.getGoodsId());

            indentResponse.setGoods(goods);
            indentResponse.setBuyTime(i.getBuyTime());
            indentResponse.setBuyCount(i.getBuyCount());
            indentResponse.setIndentId(i.getId());
            indentResponseList.add(indentResponse);
        }
        ilr.setIndentList(indentResponseList);
        ilr.setTotal(indentMapper.findByGoodsAndStatusSize(goodsId,status));
        result.setMsg("OK");
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setPayload(ilr);
        return result;
    }
}
