package com.baitaner.common.service.impl;

import com.baitaner.common.domain.result.Result;
import com.baitaner.common.domain.base.Group;
import com.baitaner.common.mapper.IGroupMapper;
import com.baitaner.common.mapper.IUserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IGroupService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-4-30
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
@Service("groupService")
public class GroupServiceImpl implements IGroupService {
    private static final Logger logger = Logger
            .getLogger(GroupServiceImpl.class.getSimpleName());
    @Autowired
    private ICacheService cacheService;

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private IGroupMapper groupMapper;


    @Override
    public Result getGroup(Long id){
        return null;
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateGroup(Long id, Group group){
        return null;
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result saveGroup(Group group){
        return null;
    }
}
