package com.baitaner.common.service;

import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.goods.RequestCreateIndent;
import com.baitaner.common.domain.result.IndentListResult;
import com.baitaner.common.domain.result.IndentResult;
import com.baitaner.common.domain.result.Result;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zliu on 15/1/28.
 */
public interface IIndentService {
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Result saveIndent(User user,RequestCreateIndent createIndent);

    Result cancelIndent(Long indentId);

    Result confirmIndent(Long indentId);

    IndentResult getIndent(Long indentId);

    IndentListResult findIndentByUser(Long userId,Integer index,Integer limit);
    IndentListResult findIndentByGoods(Long goodsId,Integer index,Integer limit);
    IndentListResult findIndentByGroupAndStatus(Long goodsId,Integer status,Integer index,Integer limit);
}
