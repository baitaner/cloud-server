package com.baitaner.common.enums;

/**
 * Created by zliu on 15/1/27.
 */
public interface UserEnums {
    public interface AUTH{
        /**
         1：邮箱未激活（注册后激活）
         2：邮箱激活
         */
        public final static int NO = 1;
        public final static int YES = 2;
    }
    public interface ROLE{
        /**
         1：普通用户
         2：公司管理员
         3：后台管理员
         */
        public final static int NORMAL = 1;
        public final static int GROUP_ADMIN = 2;
        public final static int ADMIN = 3;
    }
    public interface STATUS{
        /**
         1：未认证，或未加入任何公司
         2：加入某个公司
         */
        public final static int NO_JOIN = 1;
        public final static int JOIN = 2;
    }


}
