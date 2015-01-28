package com.baitaner.common.utils;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-5-17
 * Time: 下午7:05
 * To change this template use File | Settings | File Templates.
 */
public class VerifyParams {
    public static boolean temperature(int temp) {
        if (temp < -100 || temp > 100) {
            return false;
        }
        return true;
    }

    public static boolean bitValue(int bit) {
        if (bit == 0 || bit == 1) {
            return true;
        }
        return false;
    }

    public static boolean bitValue(String bit) {
        if (bit == null || "".equals(bit.trim())) {
            return false;
        }
        try {
            Integer result = Integer.valueOf(bit);
            if (result == 0 || result == 1) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }

    public static boolean tinyInt(int value) {
        if (value >= 0 && value <= 50) {
            return true;
        }
        return false;
    }

}
