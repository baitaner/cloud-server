package com.baitaner.common.constant;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-4-17
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
public class ConstConfig {

    public final static int AUTH_TIMEOUT = 6000;//100分钟

    public final static int GOODS_PHOTO_MAX = 1;

    public final static String CONFIG_PATH="/opt/baitaner/conf/server.xml";
    public final static String HOME_DIR = "/opt/baitaner";
    public final static String LOGS_DIR = HOME_DIR+"/logs";
    public final static String PACKAGE_DIR = HOME_DIR+"/packages";


    public static enum YES_OR_NO {
        NO(0),
        YES(1),
        UNKNOWN(2);

        private int value;

        private YES_OR_NO(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum ON_OR_OFF {
        OFF(0),
        ON(1),
        UNKNOWN(2);

        private int value;

        private ON_OR_OFF(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

