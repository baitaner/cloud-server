package com.baitaner.common.service.impl;

import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.GoodsPhoto;
import com.baitaner.common.domain.base.Indent;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateIndent;
import com.baitaner.common.domain.response.IndentListResponse;
import com.baitaner.common.domain.response.IndentResponse;
import com.baitaner.common.domain.result.IDResult;
import com.baitaner.common.domain.result.IndentListResult;
import com.baitaner.common.domain.result.IndentResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.enums.IndentEnums;
import com.baitaner.common.mapper.base.GoodsMapper;
import com.baitaner.common.mapper.base.GoodsPhotoMapper;
import com.baitaner.common.mapper.base.IndentMapper;
import com.baitaner.common.mapper.base.UserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IGoodsService;
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
    private IGoodsService goodsService;

    @Autowired
    private IndentMapper indentMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsPhotoMapper goodsPhotoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public IDResult saveIndent(User user,RequestCreateIndent createIndent){
//判断user是否为空，以及id是否存在
        IDResult result = new IDResult();
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
        cacheService.putGoods(goods);
        return ResultUtils.getIDSuccess(goods.getId());
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

        //获取缓存信息
        Goods goods = goodsService.getGoodsOnly(indent.getGoodsId());
        if(goods!=null){
            goods.setSellCount(goods.getSellCount()-indent.getBuyCount());
            if(goods.getSellCount()<0){
                goods.setSellCount(0);
            }
            goodsMapper.update(goods);
            cacheService.putGoods(goods);
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
        Indent indent = getIndentOnly(indentId);
        if(indent==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST INDENT");
            return result;
        }
        if(!indent.getStatus().equals(IndentEnums.STATUS.NEW)) {
            indent.setStatus(IndentEnums.STATUS.COMPLETED);
            indent.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            indentMapper.update(indent);
        } else{
            result.setErrorCode(ErrorCodeConfig.STATUS_ERROR);
            result.setMsg("indent status error");
            return result;
        }
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
        //获取缓存信息
        Goods goods = goodsService.getGoodsOnly(indent.getGoodsId());
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
        ir.setStatus(indent.getStatus());
        ir.setIndentId(indentId);
        ir.setPhotoList(goodsPhotoMapper.findByGoodsId(goods.getId(),0,ConstConfig.GOODS_PHOTO_MAX));

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
        if(limit>0) {
            List<Indent> indentList = indentMapper.findByUserId(userId, index, limit);
            List<IndentResponse> indentResponseList = new ArrayList<IndentResponse>();
            for (Indent i : indentList) {
                IndentResponse indentResponse = new IndentResponse();
                //获取缓存
                Goods goods = goodsService.getGoodsOnly(i.getGoodsId());
                indentResponse.setPhotoList(goodsPhotoMapper.findByGoodsId(goods.getId(), 0, ConstConfig.GOODS_PHOTO_MAX));
                indentResponse.setGoods(goods);
                indentResponse.setStatus(i.getStatus());
                indentResponse.setBuyTime(i.getBuyTime());
                indentResponse.setBuyCount(i.getBuyCount());
                indentResponse.setIndentId(i.getId());
                indentResponseList.add(indentResponse);
            }
            ilr.setIndentList(indentResponseList);
        }
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
        if(limit>0) {
            List<Indent> indentList = indentMapper.findByGoods(goodsId, index, limit);
            List<IndentResponse> indentResponseList = new ArrayList<IndentResponse>();
            for (Indent i : indentList) {
                IndentResponse indentResponse = new IndentResponse();
                //获取缓存
                Goods goods = goodsService.getGoodsOnly(i.getGoodsId());

                indentResponse.setPhotoList(goodsPhotoMapper.findByGoodsId(goods.getId(), 0, ConstConfig.GOODS_PHOTO_MAX));
                indentResponse.setGoods(goods);
                indentResponse.setStatus(i.getStatus());
                indentResponse.setBuyTime(i.getBuyTime());
                indentResponse.setBuyCount(i.getBuyCount());
                indentResponse.setIndentId(i.getId());
                indentResponseList.add(indentResponse);
            }
            ilr.setIndentList(indentResponseList);
        }
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
        if(limit>0) {
            List<Indent> indentList = indentMapper.findByGoodsAndStatus(goodsId, status, index, limit);
            List<IndentResponse> indentResponseList = new ArrayList<IndentResponse>();
            for (Indent i : indentList) {
                IndentResponse indentResponse = new IndentResponse();
                //获取缓存
                Goods goods = goodsService.getGoodsOnly(i.getGoodsId());
                List<GoodsPhoto> photoList = goodsPhotoMapper.findByGoodsId(i.getGoodsId(), 0, ConstConfig.GOODS_PHOTO_MAX);

                indentResponse.setPhotoList(photoList);
                indentResponse.setGoods(goods);
                indentResponse.setStatus(i.getStatus());
                indentResponse.setBuyTime(i.getBuyTime());
                indentResponse.setBuyCount(i.getBuyCount());
                indentResponse.setIndentId(i.getId());
                indentResponseList.add(indentResponse);
            }
            ilr.setIndentList(indentResponseList);
        }
        ilr.setTotal(indentMapper.findByGoodsAndStatusSize(goodsId,status));
        result.setMsg("OK");
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setPayload(ilr);
        return result;
    }

    @Override
    public Indent getIndentOnly(Long indentId){
        return indentMapper.findById(indentId);
    }
}
