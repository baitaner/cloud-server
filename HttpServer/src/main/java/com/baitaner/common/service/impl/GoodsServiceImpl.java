package com.baitaner.common.service.impl;

import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateGoods;
import com.baitaner.common.domain.result.GoodsListResult;
import com.baitaner.common.domain.result.GoodsResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.mapper.IGoodsMapper;
import com.baitaner.common.mapper.IGoodsPhotoMapper;
import com.baitaner.common.mapper.IUserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IGoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public Result save(User user, RequestCreateGoods createGoods){
        return null;
    }
    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result update(Long goodsId, RequestCreateGoods createGoods){
        return null;
    }

    @Override
    public GoodsResult getGoods(Long goodsId){
        return null;
    }

    @Override
    public GoodsListResult findGoodsFromUser(Long userId, Integer status, Integer isLock, Integer index, Integer limit){
        return null;
    }
    @Override
    public GoodsListResult findGoodsFromGroup(Long groupId, Integer status, Integer isLock, Integer index, Integer limit){
        return null;
    }

    @Override
    public Result publish(Long goodsId){
        return null;
    }
    @Override
    public Result cancel(Long goodsId){
        return null;
    }
    @Override
    public Result delete(Long goodsId){
        return null;
    }

    @Override
    public Result lock(Long goodsId){
        return null;
    }

    @Override
    public Result unlock(Long goodsId){
        return null;
    }

}
