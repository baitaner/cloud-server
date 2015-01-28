package com.baitaner.common.service.impl;

import com.baitaner.common.domain.request.info.RequestCreateInfo;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.mapper.IGoodsMapper;
import com.baitaner.common.mapper.IGoodsPhotoMapper;
import com.baitaner.common.mapper.IInfoMapper;
import com.baitaner.common.mapper.IUserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("infoService")
public class InfoServiceImpl implements IInfoService {
    private static final Logger logger = Logger.getLogger(InfoServiceImpl.class
            .getSimpleName());

    @Autowired
    private ICacheService cacheService;
    @Autowired
    private IInfoMapper infoMapper;

    @Autowired
    private IGoodsMapper goodsMapper;

    @Autowired
    private IGoodsPhotoMapper goodsPhotoMapper;

    @Autowired
    private IUserMapper userMapper;

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result saveInfo(RequestCreateInfo createInfo){
        return null;
    }
    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateInfo(Long infoId, RequestCreateInfo createInfo){
        return null;
    }

    @Override
    public Result getInfo(Long infoId){
        return null;
    }

    @Override
    public Result getInfoGoods(Long infoId){
        return null;
    }

    @Override
    public Result findInfo(Long userId){
        return null;
    }
}
