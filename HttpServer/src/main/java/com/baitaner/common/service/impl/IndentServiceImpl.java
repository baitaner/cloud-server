package com.baitaner.common.service.impl;

import com.baitaner.common.domain.result.Result;
import com.baitaner.common.domain.base.Indent;
import com.baitaner.common.domain.base.IndentGoods;
import com.baitaner.common.mapper.*;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IIndentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private IIndentGoodsMapper indentGoodsMapper;


    @Autowired
    private IGoodsMapper goodsMapper;

    @Autowired
    private IGoodsPhotoMapper goodsPhotoMapper;

    @Autowired
    private IUserMapper userMapper;

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result saveIndent(Indent indent, List<IndentGoods> indentGoodses){
        return null;
    }

    @Override
    public Result updateIndent(Long indentId, Indent indent, List<IndentGoods> indentGoodses){
        return null;
    }

    @Override
    public Result operatIndentStatus(Long indentId, int status){
        return null;
    }

    @Override
    public Result getIndent(Long inedntId){
        return null;
    }

    @Override
    public Result findIndentByUser(Long userId){
        return null;
    }
}
