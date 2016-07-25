package com.lost.zou.pedometer.presentation.common.utils;

import android.content.Context;

/**
 * @zoubo
 */
// CHECKSTYLE:OFF
public class Machine {
    private static boolean sCheckTablet = false;
    private static boolean sIsTablet = false;



    // 判断当前设备是否为平板
    private static boolean isPad() {
        if (DrawUtil.sDensity >= 1.5 || DrawUtil.sDensity <= 0) {
            return false;
        }
        if (DrawUtil.sWidthPixels < DrawUtil.sHeightPixels) {
            if (DrawUtil.sWidthPixels > 480 && DrawUtil.sHeightPixels > 800) {
                return true;
            }
        } else {
            if (DrawUtil.sWidthPixels > 800 && DrawUtil.sHeightPixels > 480) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTablet(Context context) {
        if (sCheckTablet == true) {
            return sIsTablet;
        }
        sCheckTablet = true;
        sIsTablet = isPad();
        return sIsTablet;
    }

}
