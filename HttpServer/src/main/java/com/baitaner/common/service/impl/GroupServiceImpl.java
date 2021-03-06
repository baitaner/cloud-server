package com.baitaner.common.service.impl;

import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.base.Group;
import com.baitaner.common.domain.response.GroupListResponse;
import com.baitaner.common.domain.result.GroupListResult;
import com.baitaner.common.domain.result.GroupResult;
import com.baitaner.common.domain.result.IDResult;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.mapper.base.GroupMapper;
import com.baitaner.common.mapper.base.UserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IGroupService;
import com.baitaner.common.utils.ResultUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

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
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;


    @Override
    public GroupResult getGroup(Long id){
        Group group = groupMapper.findById(id);
        GroupResult result = new GroupResult();
        if(group==null) {
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST Group");
        } else{
            result.setErrorCode(ErrorCodeConfig.SUCCESS);
            result.setMsg("ok");
            result.setPayload(group);
        }
        return result;
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateGroup(Long id, Group group){
        Result result = new Result();
        if(id==null || group==null){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Group localGroup = groupMapper.findById(id);
        if(localGroup==null) {
            result.setErrorCode(ErrorCodeConfig.NO_RECORD_DB);
            result.setMsg("NOT EXIST Group");
            return result;
        }

        if(group.getEmailPostfix()!=null){
            localGroup.setEmailPostfix(group.getEmailPostfix());
        }
        if(group.getName()!=null){
            localGroup.setName(group.getName());
        }
        if(group.getCity()!=null){
            localGroup.setCity(group.getCity());
        }
        groupMapper.update(localGroup);
        return ResultUtils.getSuccess();
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public IDResult saveGroup(Group group){
        IDResult result = new IDResult();
        if(group==null
                || group.getCity()==null
                || group.getName()==null
                || group.getEmailPostfix()==null
                ){
            result.setErrorCode(ErrorCodeConfig.INVALID_PARAMS);
            result.setMsg("INVALID_PARAMS");
            return result;
        }
        Group localGroup = groupMapper.findByName(group.getName());
        if(localGroup!=null) {
            result.setErrorCode(ErrorCodeConfig.ALREADY_EXISTS);
            result.setMsg("Already EXIST Group");
            return result;
        }

        group.setCreateTime(new Timestamp(System.currentTimeMillis()));
        groupMapper.insert(group);

        return ResultUtils.getIDSuccess(group.getId());
    }

    @Override
    public GroupListResult find(Integer index, Integer limit){
        GroupListResult result = new GroupListResult();
        if(index==null){
            index=0;
        }
        if(limit==null){
            limit = ConstConfig.GET_INFO_MAX;
        }
        GroupListResponse groupListResponse = new GroupListResponse();
        if(limit>0) {
            List<Group> groupList = groupMapper.find(index, limit);
            groupListResponse.setGroupList(groupList);
        }
        groupListResponse.setTotal(groupMapper.findSize());
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("ok");
        result.setPayload(groupListResponse);
        return result;
    }
}
