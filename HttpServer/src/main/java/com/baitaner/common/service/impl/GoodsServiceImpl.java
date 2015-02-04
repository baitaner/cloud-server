package com.baitaner.common.service.impl;

import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.GoodsPhoto;
import com.baitaner.common.domain.request.goods.RequestCreateGoods;
import com.baitaner.common.domain.response.GoodsListResponse;
import com.baitaner.common.domain.response.GoodsResponse;
import com.baitaner.common.domain.result.*;
import com.baitaner.common.enums.GoodsEnums;
import com.baitaner.common.enums.IndentEnums;
import com.baitaner.common.mapper.base.GoodsMapper;
import com.baitaner.common.mapper.base.GoodsPhotoMapper;
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
import java.util.List;

@Service("goodsService")
public class GoodsServiceImpl implements IGoodsService {
    private static final Logger logger = Logger.getLogger(GoodsServiceImpl.class
            .getSimpleName());

    @Autowired
    private ICacheService cacheService;
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsPhotoMapper goodsPhotoMapper;

    @Autowired
    private IIndentService indentService;

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public IDResult save(Long userId,Long groupId, RequestCreateGoods createGoods){
        //判断user是否为空，以及id是否存在
        IDResult result = new IDResult();
        if(userId==null||createGoods==null||groupId==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Long publishSize = goodsMapper.findByUserIdAndStatusAndLockSize(userId,GoodsEnums.STATUS.PUBLISHED,GoodsEnums.IS_LOCK.UN_LOCK);
        Long cancelSize = goodsMapper.findByUserIdAndStatusAndLockSize(userId,GoodsEnums.STATUS.CANCELED,GoodsEnums.IS_LOCK.UN_LOCK);
        Long completeSize = goodsMapper.findByUserIdAndStatusAndLockSize(userId,GoodsEnums.STATUS.TERMINATE,GoodsEnums.IS_LOCK.UN_LOCK);
        if(publishSize>=ConstConfig.PUBLISH_MAX || cancelSize>=ConstConfig.PUBLISH_MAX || completeSize>=ConstConfig.PUBLISH_MAX){
            result.setErrorCode(ErrorCodeConfig.BEYOND_MAX_VALUE);
            result.setMsg("User publish goods beyond max value");
            return result;
        }
        Goods goods = new Goods();
        goods.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goods.setGroupId(groupId);
        goods.setUserId(userId);
        goods.setPrice(createGoods.getPrice());
        goods.setTotal(createGoods.getTotal());
        goods.setTitle(createGoods.getTitle());
        //暂时直接发布，以后有需要再改
        goods.setStatus(GoodsEnums.STATUS.PUBLISHED);
        goods.setDescription(createGoods.getDescription());
        goods.setExpireTime(new Timestamp(System.currentTimeMillis()+createGoods.getExpireTime()*1000));
        goods.setIsLock(GoodsEnums.IS_LOCK.UN_LOCK);
        goods.setPreviousStatus(GoodsEnums.STATUS.UN_PUBLISHED);
        goods.setSellCount(0);
        int count =goodsMapper.insert(goods);
        if(count>0){
            if(createGoods.getPhotoList()!=null && createGoods.getPhotoList().size()>0) {
                for (GoodsPhoto photo : createGoods.getPhotoList()) {
                    photo.setGoodsId(goods.getId());
                    goodsPhotoMapper.insert(photo);
                }
            }
        }
        cacheService.putPublishList(goods.getGroupId(),goods.getId());
        return ResultUtils.getIDSuccess(goods.getId());
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
        if(!goods.getStatus().equals(GoodsEnums.STATUS.UN_PUBLISHED)){
            result.setErrorCode(ErrorCodeConfig.STATUS_ERROR);
            result.setMsg("Goods status error!");
            return result;
        }
        if(createGoods.getPhotoList()!=null && createGoods.getPhotoList().size()>0){
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
        if(createGoods.getPrice()!=null){
            goods.setPrice(createGoods.getPrice());
        }
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsMapper.update(goods);
        cacheService.putGoods(goods);
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
        Long publishSize = goodsMapper.findByUserIdAndStatusAndLockSize(goods.getUserId(),GoodsEnums.STATUS.PUBLISHED,GoodsEnums.IS_LOCK.UN_LOCK);
        Long cancelSize = goodsMapper.findByUserIdAndStatusAndLockSize(goods.getUserId(),GoodsEnums.STATUS.CANCELED,GoodsEnums.IS_LOCK.UN_LOCK);
        Long terminateSize = goodsMapper.findByUserIdAndStatusAndLockSize(goods.getUserId(),GoodsEnums.STATUS.TERMINATE,GoodsEnums.IS_LOCK.UN_LOCK);
        if(publishSize>=ConstConfig.PUBLISH_MAX || cancelSize>=ConstConfig.PUBLISH_MAX || terminateSize>=ConstConfig.PUBLISH_MAX){
            result.setErrorCode(ErrorCodeConfig.BEYOND_MAX_VALUE);
            result.setMsg("User publish goods beyond max value");
            return result;
        }        //判断允许发布的最大的数量
        if(publishSize<ConstConfig.PUBLISH_MAX){
            if(goods.getStatus().equals(GoodsEnums.STATUS.UN_PUBLISHED)){
                goods.setPreviousStatus(goods.getStatus());
                goods.setStatus(GoodsEnums.STATUS.PUBLISHED);
                goods.setPublishTime(new Timestamp(System.currentTimeMillis()));
                goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                goodsMapper.update(goods);
                cacheService.putGoods(goods);
            } else{
                result.setErrorCode(ErrorCodeConfig.STATUS_ERROR);
                result.setMsg("Goods status error!");
                return result;
            }
        } else{
            result.setErrorCode(ErrorCodeConfig.BEYOND_MAX_VALUE);
            result.setMsg("User publish goods beyond max value");
            return result;
        }
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
        if(goods.getStatus().equals(GoodsEnums.STATUS.PUBLISHED) || goods.getStatus().equals(GoodsEnums.STATUS.UN_PUBLISHED)){
            goods.setPreviousStatus(goods.getStatus());
            goods.setStatus(GoodsEnums.STATUS.CANCELED);
            goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            goodsMapper.update(goods);
            cacheService.putGoods(goods);
            //直接调用完成，里面会做检查
            complete(goods.getId());
        } else{
            result.setErrorCode(ErrorCodeConfig.STATUS_ERROR);
            result.setMsg("Goods status error!");
            return result;
        }
        return ResultUtils.getSuccess();
    }

    @Override
    public Result rollbackGoods(Long goodsId, Integer count){
        Result result = new Result();
        //获取缓存信息
        Goods goods = this.getGoodsOnly(goodsId);
        if(goods!=null){
            goods.setSellCount(goods.getSellCount()-count);
            if(goods.getSellCount()<0){
                goods.setSellCount(0);
            }
            goodsMapper.update(goods);
            cacheService.putGoods(goods);
            //有可能状态是取消状态
            complete(goodsId);
        }
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("ok");
        return result;
    }
    @Override
    public Result sell(Long goodsId, Integer count){
        Result result = new Result();
        //获取缓存信息
        Goods goods = this.getGoodsOnly(goodsId);
        if(goods!=null){
            goods.setSellCount(goods.getSellCount()+count);
            goodsMapper.update(goods);
            cacheService.putGoods(goods);
            if(goods.getSellCount()>=goods.getTotal()){
                terminate(goodsId);
            }
        }
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("ok");
        return result;
    }

    /**
     * 时间过期或卖完
     * @param goodsId
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result terminate(Long goodsId){
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
        if(goods.getStatus().equals(GoodsEnums.STATUS.PUBLISHED)){
            goods.setPreviousStatus(goods.getStatus());
            goods.setStatus(GoodsEnums.STATUS.TERMINATE);
            goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            goodsMapper.update(goods);
            cacheService.putGoods(goods);
            //直接调用完成，里面会做检查
            complete(goods.getId());
        } else{
            result.setErrorCode(ErrorCodeConfig.STATUS_ERROR);
            result.setMsg("Goods status error!");
            return result;
        }
        return ResultUtils.getSuccess();
    }

    /**
     * 当信息结束或取消，订单处理完成后归档为完成
     * @param goodsId
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result complete(Long goodsId){
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
        if(goods.getStatus().equals(GoodsEnums.STATUS.TERMINATE) || goods.getStatus().equals(GoodsEnums.STATUS.CANCELED)){
            //todo 需要检查订单状态
            IndentListResult ilresult = indentService.findIndentByGoodsAndStatus(goods.getId(), IndentEnums.STATUS.NEW, 0, 0);
            //如果有订单没有处理过得订单
            if(ilresult.getErrorCode()==ErrorCodeConfig.SUCCESS && ilresult.getPayload().getTotal()>0){
                result.setErrorCode(ErrorCodeConfig.STATUS_ERROR);
                result.setMsg("Already exist new indent");
                return result;
            }
            goods.setPreviousStatus(goods.getStatus());
            goods.setStatus(GoodsEnums.STATUS.COMPLETE);
            goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            goodsMapper.update(goods);
            cacheService.putGoods(goods);
        } else{
            result.setErrorCode(ErrorCodeConfig.STATUS_ERROR);
            result.setMsg("Goods status error!");
            return result;
        }
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
        Goods goods = getGoodsOnly(goodsId);
        if(goods.getStatus().equals(GoodsEnums.STATUS.COMPLETE)
                ) {
            // 清除缓存
            cacheService.deleteGoods(goodsId);
            goodsPhotoMapper.deleteByGoodsId(goodsId);
            goodsMapper.delete(goodsId);
        }else{
            result.setErrorCode(ErrorCodeConfig.STATUS_ERROR);
            result.setMsg("Goods status error!");
            return result;
        }
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
        cacheService.putGoods(goods);

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
        cacheService.putGoods(goods);
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
        Goods goods = getGoodsOnly(goodsId);
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
        if(limit>0) {
            List<Goods> goodsList = goodsMapper.findByUserIdAndStatusAndLock(userId, status, isLock, index, limit);
            glr.setGoodsList(goodsList);
        }
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
        if(limit>0) {
            List<Goods> goodsList = goodsMapper.findByGroupIdAndStatusAndLock(groupId, status, isLock, index, limit);
            glr.setGoodsList(goodsList);
        }
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
            if(goods!=null){
                cacheService.putGoods(goods);
            }
        }
        return goods;
    }

}
