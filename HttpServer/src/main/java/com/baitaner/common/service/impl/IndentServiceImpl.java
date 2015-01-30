package com.baitaner.common.service.impl;

import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateIndent;
import com.baitaner.common.domain.result.IndentListResult;
import com.baitaner.common.domain.result.IndentResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.mapper.IGoodsMapper;
import com.baitaner.common.mapper.IGoodsPhotoMapper;
import com.baitaner.common.mapper.IIndentMapper;
import com.baitaner.common.mapper.IUserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IIndentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        return null;
    }

    @Override
    public Result cancelIndent(Long indentId){
        return null;
    }
    @Override
    public Result confirmIndent(Long indentId) {
        return null;
    }

    @Override
    public IndentResult getIndent(Long inedntId){
        return null;
    }

    @Override
    public IndentListResult findIndentByUser(Long userId,Integer index,Integer limit){
        return null;
    }

    @Override
    public IndentListResult findIndentByGoods(Long infoId, Integer index,Integer limit) {
        return null;
    }

    @Override
    public IndentListResult findIndentByGroupAndStatus(Long goodsId, Integer status,Integer index, Integer limit) {
        return null;
    }
}
