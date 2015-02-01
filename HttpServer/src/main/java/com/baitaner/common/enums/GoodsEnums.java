package com.baitaner.common.enums;

/**
 * Created by zliu on 15/1/27.
 */
public interface GoodsEnums {
    public interface IS_LOCK{
        /**
         * 1：未锁定
         2：锁定
         */
        public final static int UN_LOCK = 1;
        public final static int LOCK = 2;


    }
    public interface STATUS{
        /**
         1：未发布（新建）
         2：发布
         3：取消
         4：完成
         5：结束
         */
        public final static int UN_PUBLISHED = 1;
        public final static int PUBLISHED = 2;
        public final static int CANCELED = 3;
        public final static int COMPLETED = 4;
        public final static int TERMINATE = 5;
    }
}
