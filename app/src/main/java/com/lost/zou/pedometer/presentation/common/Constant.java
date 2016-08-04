package com.lost.zou.pedometer.presentation.common;

import android.os.Environment;

/**
 * @author zoubo
 */
public class Constant {

    public static final class Path {
        public final static String SDCARD = Environment.getExternalStorageDirectory().getPath();
        public static String sAPP_DIR = SDCARD + "/Pedometer";

        //启动页存放路径
        public static final String START_UP_MEDIA_PATH = sAPP_DIR + "/download/StartUpMedia/";

        public static final String IMAGE_CACHE_DIR = sAPP_DIR + "/imageCache"; // 缓存保存路径
        public static final String DATA_CACHE_DIR = sAPP_DIR + "/dataCache"; // 缓存保存路径

        public static final String WEBVIEW_DB_DIR = sAPP_DIR + "/webviewDB";

        //用户信息路径
        public final static String ACCOUNT_DIR = sAPP_DIR + "/account/";

        //用户信息路径
        public final static String COURSE_DIR = sAPP_DIR + "/account/course";

        //图片格式
        public final static String PICTURE_EX_JPG = ".jpg";
        // png后缀
        public final static String PICTURE_EX_PNG = ".png";
        public final static String PICTURE_EX_JPEG = ".jpeg";
    }
}
