package com.baitaner.common.service.impl;

import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.GoodsPhoto;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateGoods;
import com.baitaner.common.domain.response.GoodsListResponse;
import com.baitaner.common.domain.response.GoodsResponse;
import com.baitaner.common.domain.result.GoodsListResult;
import com.baitaner.common.domain.result.GoodsResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.enums.GoodsEnums;
import com.baitaner.common.mapper.IGoodsMapper;
import com.baitaner.common.mapper.IGoodsPhotoMapper;
import com.baitaner.common.mapper.IUserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IGoodsService;
import com.baitaner.common.utils.ResultUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service("goodsService")
public class GoodsServiceImpl implements IGoodsService {
    private static final Logger logger = Logger.getLogger(GoodsServiceImpl.class
            .getSimpleName());

    @Autowired
    private ICacheService cacheService;
    @Autowired
    private IGoodsMapper goodsMapper;

    @Autowired
    private IGoodsPhotoMapper goodsPhotoMapper;

    @Autowired
    private IUserMapper userMapper;

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result save(Long userId,Long groupId, RequestCreateGoods createGoods){
        //判断user是否为空，以及id是否存在
        Result result = new Result();
        if(userId==null||createGoods==null||groupId==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Goods goods = new Goods();
        goods.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goods.setGroupId(groupId);
        goods.setUserId(userId);
        goods.setTotal(createGoods.getTotal());
        goods.setTitle(createGoods.getTitle());
        goods.setStatus(GoodsEnums.STATUS.UN_PUBLISHED);
        goods.setDescription(createGoods.getDescription());
        goods.setExpireTime(new Timestamp(System.currentTimeMillis()+createGoods.getExpireTime()*1000));
        goods.setIsLock(GoodsEnums.IS_LOCK.UN_LOCK);
        goods.setPreviousStatus(GoodsEnums.STATUS.UN_PUBLISHED);
        goods.setSellCount(0);
        int count =goodsMapper.insert(goods);
        if(count>0){
            for(GoodsPhoto photo:createGoods.getPhotoList()){
                photo.setGoodsId(goods.getId());
                goodsPhotoMapper.insert(photo);
            }
        }
        cacheService.putPublishList(goods.getGroupId(),goods.getId());
        return ResultUtils.getSuccess();
    }
    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result update(Long goodsId, RequestCreateGoods createGoods){
        Result result = new Result();
        if(goodsId==null || createGoods==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        //通过缓存获取
        Goods goods = getGoodsOnly(goodsId);
        if(goods==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST GOODS");
            return result;
        }
        if(createGoods.getPhotoList()!=null ){
            goodsPhotoMapper.deleteByGoodsId(goods.getId());
            for(GoodsPhoto photo:createGoods.getPhotoList()){
                photo.setGoodsId(goods.getId());
                goodsPhotoMapper.insert(photo);
            }

        }
        if(createGoods.getDescription()!=null){
            goods.setDescription(createGoods.getDescription());
        }
        if(createGoods.getExpireTime()!=null){
            goods.setExpireTime(new Timestamp(System.currentTimeMillis()+createGoods.getExpireTime()*1000));
        }
        if(createGoods.getTitle()!=null){
            goods.setTitle(createGoods.getTitle());
        }
        if(createGoods.getTotal()!=null){
            goods.setTotal(createGoods.getTotal());
        }
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsMapper.update(goods);

        return ResultUtils.getSuccess();
    }



    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result publish(Long goodsId){
        Result result = new Result();
        if(goodsId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        //获取缓存信息
        Goods goods = getGoodsOnly(goodsId);
        if(goods==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST GOODS");
            return result;
        }
        goods.setStatus(GoodsEnums.STATUS.PUBLISHED);
        goods.setPublishTime(new Timestamp(System.currentTimeMillis()));
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsMapper.update(goods);
        return ResultUtils.getSuccess();
    }
    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result cancel(Long goodsId){
        Result result = new Result();
        if(goodsId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        // 获取缓存信息
        Goods goods = getGoodsOnly(goodsId);
        if(goods==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST GOODS");
            return result;
        }
        goods.setStatus(GoodsEnums.STATUS.CANCELED);
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsMapper.update(goods);
        return ResultUtils.getSuccess();
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result delete(Long goodsId){
        Result result = new Result();
        if(goodsId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        // 清除缓存
        cacheService.deleteGoods(goodsId);
        goodsPhotoMapper.deleteByGoodsId(goodsId);
        goodsMapper.delete(goodsId);
        return ResultUtils.getSuccess();
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result lock(Long goodsId){
        Result result = new Result();
        if(goodsId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        //获取缓存信息
        Goods goods = getGoodsOnly(goodsId);
        if(goods==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST GOODS");
            return result;
        }
        goods.setIsLock(GoodsEnums.IS_LOCK.LOCK);
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsMapper.update(goods);
        return ResultUtils.getSuccess();
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result unlock(Long goodsId){
        Result result = new Result();
        if(goodsId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        //获取缓存信息
        Goods goods = getGoodsOnly(goodsId);
        if(goods==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST GOODS");
            return result;
        }
        goods.setIsLock(GoodsEnums.IS_LOCK.UN_LOCK);
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsMapper.update(goods);
        return ResultUtils.getSuccess();
    }

    @Override
    public GoodsResult getGoods(Long goodsId){
        GoodsResult result = new GoodsResult();
        if(goodsId==null ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Goods goods = goodsMapper.findById(goodsId);
        if(goods==null){
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NO EXIST GOODS");
            return result;
        }
        GoodsResponse goodsResponse = new GoodsResponse();
        goodsResponse.setGoods(goods);
        List<GoodsPhoto> photoList = goodsPhotoMapper.findByGoodsId(goodsId,0, ConstConfig.GOODS_PHOTO_MAX);
        goodsResponse.setPhotoList(photoList);
        result.setPayload(goodsResponse);
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("OK");
        return result;
    }

    @Override
    public GoodsListResult findGoodsFromUser(Long userId, Integer status, Integer isLock, Integer index, Integer limit){
        GoodsListResult result = new GoodsListResult();
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
        if(status==null){
            status=GoodsEnums.STATUS.PUBLISHED;
        }
        if(isLock==null){
            isLock=GoodsEnums.IS_LOCK.UN_LOCK;
        }
        GoodsListResponse glr = new GoodsListResponse();
        //todo 取缓存的的数据
        List<Goods> goodsList = goodsMapper.findByUserIdAndStatusAndLock(userId,status,isLock,index,limit);
        glr.setGoodsList(goodsList);
        glr.setTotal(goodsMapper.findByUserIdAndStatusAndLockSize(userId,status,isLock));
        result.setPayload(glr);
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("OK");
        return result;
    }
    @Override
    public GoodsListResult findGoodsFromGroup(Long groupId, Integer status, Integer isLock, Integer index, Integer limit){
        GoodsListResult result = new GoodsListResult();
        if(groupId==null
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
            status=GoodsEnums.STATUS.PUBLISHED;
        }
        if(isLock==null){
            isLock=GoodsEnums.IS_LOCK.UN_LOCK;
        }
        GoodsListResponse glr = new GoodsListResponse();
        //todo 取缓存的的数据
        List<Goods> goodsList = goodsMapper.findByGroupIdAndStatusAndLock(groupId,status,isLock,index,limit);
        glr.setGoodsList(goodsList);
        glr.setTotal(goodsMapper.findByGroupIdAndStatusAndLockSize(groupId, status, isLock));
        result.setPayload(glr);
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("OK");
        return result;
    }

    @Override
    public Goods getGoodsOnly(Long goodsId){
        Goods goods = cacheService.getGoods(goodsId);
        if(goods==null){
            goods = goodsMapper.findById(goodsId);
        }
        return goods;
    }

}
