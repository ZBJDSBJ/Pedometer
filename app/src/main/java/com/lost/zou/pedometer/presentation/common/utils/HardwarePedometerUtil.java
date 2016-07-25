package com.lost.zou.pedometer.presentation.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Alost on 16/2/16.
 * 计步器硬件传感器工具类
 */
public class HardwarePedometerUtil {
    public static List<String> mStepCounterWhiteList = Arrays.asList(new String[]{"nexus5", "nexus6", "mx4", "mx5"});

    private static boolean areSensorsPresent(Context context) {
        return !((SensorManager) context.getSystemService(context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_STEP_COUNTER).isEmpty();
    }

    /**
     * step counter白名单列表
     */
    public static boolean isThisDeviceInStepCounterWhiteList() {
        return mStepCounterWhiteList.contains(Build.MODEL.toLowerCase().replace(" ", ""));
    }

    /**
     * 是否支持step counter记步传感器
     * 方式：1、有TYPE_STEP_COUNTER；2、版本为4.4(19)以上
     */
    public static boolean supportsHardwareStepCounter(Context context) {
        SensorManager mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Log.i("zou", "sensor.getType() mStepCount = \n " + mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
                + "\n mStepDetector = " + mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
                + "\n mAccelerometer = " + mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));

        return (AndroidVersionUtil.isVersionKitKatOrHigher()) && (areSensorsPresent(context));
    }

    /**
     * 是否支持accelerometer传感器
     */
    public static boolean supportsHardwareAccelerometer(Context context) {
        return !((SensorManager) context.getSystemService(context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ACCELEROMETER).isEmpty();
    }

    /**
     * Service的检查与启动:是否正在运行
     */
    public static boolean checkServiceRunning(Context context) {
        boolean isServiceRunning = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("bike.qibai.bananacard.presentation.service.PedometerService".equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }

        return isServiceRunning;
    }
}
