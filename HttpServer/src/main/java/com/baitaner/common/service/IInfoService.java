package com.baitaner.common.service;

import com.baitaner.common.domain.request.info.RequestCreateInfo;
import com.baitaner.common.domain.result.InfoListResult;
import com.baitaner.common.domain.result.InfoResult;
import com.baitaner.common.domain.result.Result;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zliu on 15/1/28.
 */
public interface IInfoService {
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Result saveInfo(RequestCreateInfo createInfo);

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Result updateInfo(Long infoId, RequestCreateInfo createInfo);

    InfoResult getInfo(Long infoId);

    Result getInfoGoods(Long infoId);

    InfoListResult findInfoFromUser(Long userId,Integer status,Integer isLock,Integer limit);
    InfoListResult findInfoFromGroup(Long groupId,Integer status,Integer isLock,Integer limit);

    Result publish(Long infoId);

    Result cancel(Long infoId);

    Result delete(Long infoId);
}
