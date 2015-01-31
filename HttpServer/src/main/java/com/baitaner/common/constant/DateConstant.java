package com.baitaner.common.constant;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-9-13
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public interface DateConstant {
    public static final Long HALF_SEC = 500l;
    public static final Long ONE_SEC = 1000l;
    public static final Long ONE_MIN = 1000*60l;
    public static final Long ONE_HOUR = ONE_MIN*60l;
    public static final Long ONE_DAY = ONE_HOUR*24;

    public static final Long MCU_BASE_TIME = 946684800000l;

    public final static String BASE_TIME_STR = "GMT 2000-01-01 00:00:00";

    public SimpleDateFormat LOG_FILE_TIME = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//日志名称格式   sdcard 不支持冒号命名

}
