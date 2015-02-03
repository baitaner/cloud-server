package com.baitaner.common.service;

import com.baitaner.common.domain.base.Group;
import com.baitaner.common.domain.result.GroupListResult;
import com.baitaner.common.domain.result.GroupResult;
import com.baitaner.common.domain.result.IDResult;
import com.baitaner.common.domain.result.Result;

/**
 * Created by zliu on 15/1/28.
 */
public interface IGroupService {
    GroupResult getGroup(Long id);

    Result updateGroup(Long id, Group group);

    IDResult saveGroup(Group group);

    GroupListResult find(Integer index,Integer limit);
}
