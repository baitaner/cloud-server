package com.baitaner.common.service;

import com.baitaner.common.domain.result.Result;
import com.baitaner.common.domain.base.Indent;
import com.baitaner.common.domain.base.IndentGoods;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zliu on 15/1/28.
 */
public interface IIndentService {
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Result saveIndent(Indent indent, List<IndentGoods> indentGoodses);

    Result updateIndent(Long indentId, Indent indent, List<IndentGoods> indentGoodses);

    Result operatIndentStatus(Long indentId, int status);

    Result getIndent(Long inedntId);

    Result findIndentByUser(Long userId);
}
