package com.baitaner.common.service;

import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateGoods;
import com.baitaner.common.domain.result.GoodsListResult;
import com.baitaner.common.domain.result.GoodsResult;
import com.baitaner.common.domain.result.Result;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zliu on 15/1/28.
 */
public interface IGoodsService {

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Result save(User user,RequestCreateGoods createGoods);

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Result update(Long goodsId, RequestCreateGoods createGoods);

    GoodsResult getGoods(Long goodsId);

    GoodsListResult findGoodsFromUser(Long userId, Integer status, Integer isLock, Integer index, Integer limit);

    GoodsListResult findGoodsFromGroup(Long groupId, Integer status, Integer isLock, Integer index, Integer limit);

    Result publish(Long goodsId);

    Result cancel(Long goodsId);

    Result delete(Long goodsId);

    Result lock(Long goodsId);

    Result unlock(Long goodsId);
}
