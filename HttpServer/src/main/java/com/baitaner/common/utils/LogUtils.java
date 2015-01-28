package com.baitaner.common.utils;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by zliu on 14-9-20.
 */
public class LogUtils {
    public static final boolean debug = true;//是否打印调试信息
    public static final boolean warn = true;//是否打印调试信息
    public static final boolean info = true;//是否打印info信息
    public static final boolean error = true;//是否打印error信息
    public static final boolean isNeedNetErrorLog = false;//是否需要网络错误日志

    private static final Logger Log = Logger.getLogger(LogUtils.class.getSimpleName());

    public static void debug(String Tag, String info) {
        if (info == null) {
            info = "null";
        }
        if (debug){
            Log.debug(toFormat(Tag,info));
        }
    }
    public static void warn(String Tag, String info) {
        if (info == null) {
            info = "null";
        }
        if (warn){
            Log.warn(toFormat(Tag,info));
        }
    }

    public static void info(String Tag, String strinfo) {
        if (strinfo == null) {
            strinfo = "null";
        }
        if (info){
            Log.info(toFormat(Tag,strinfo));
        }
    }

    public static void error(String Tag, String strinfo) {
        if (strinfo == null) {
            strinfo = "null";
        }
        if (error) {
            Log.error(toFormat(Tag,strinfo));
        }
    }

    public static void write2File(IOException e, String path) {
        if (isNeedNetErrorLog) {
            try {
                System.setErr(new PrintStream(new FileOutputStream(path, true)));
            } catch (FileNotFoundException e1) {

            }
            System.err.println("\r\n-------" + System.currentTimeMillis() + "---s--\r\n");
            e.printStackTrace();

            System.err.println("\r\n-------" + System.currentTimeMillis() + "---e--\r\n");
        }
    }

    private static String toFormat(String tag,String log){
        return "["+tag+"]\t"+ log;
    }
}
