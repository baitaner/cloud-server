package com.baitaner.common.enums;

/**
 * Created by zliu on 15/1/27.
 */
public interface IndentEnums {
    public interface STATUS{
        /**
         1：新增订单  正在进行交易
         2：取消订单
         3：订单结束 交易完成
         */
        public final static int NEW = 1;
        public final static int CANCELED = 2;
        public final static int COMPLETED = 3;
    }
}
