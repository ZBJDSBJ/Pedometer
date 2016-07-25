package com.lost.zou.pedometer.presentation.view.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import com.lost.zou.pedometer.data.model.pedometer.PedometerRepositoryIml;
import com.lost.zou.pedometer.presentation.common.BaseApplication;
import com.lost.zou.pedometer.presentation.common.utils.HardwarePedometerUtil;
import com.lost.zou.pedometer.presentation.module.ApplicationModule;


/**
 * @author zoubo
 *         计步器服务，启动条件：
 *         1、有TYPE_STEP_COUNTER； 2、版本为4.4(19)以上
 */
public class PedometerService extends Service {
    private Context mContext;
    private SensorManager mSensorManager;  // 传感器服务
    private PedometerRepositoryIml mPedometerRepositoryIml;  // 传感器监听对象

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = BaseApplication.getAppContext();
        Log.i("zou", "<PedometerService> onCreate");

        mPedometerRepositoryIml = ApplicationModule.getInstance().getPedometerRepository();
        mPedometerRepositoryIml.initData();

        ApplicationModule.getInstance().getPedometerManager().init();

        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        if (HardwarePedometerUtil.supportsHardwareStepCounter(mContext)) {
            mSensorManager.registerListener(mPedometerRepositoryIml, mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                    SensorManager.SENSOR_DELAY_UI);
        } else if (HardwarePedometerUtil.supportsHardwareAccelerometer(mContext)) {
            mSensorManager.registerListener(mPedometerRepositoryIml, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("zou", "<PedometerService> onStartCommand");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("zou", "<PedometerService> onDestroy");

        if (mPedometerRepositoryIml != null) {
            mPedometerRepositoryIml.onDestroy();
            mSensorManager.unregisterListener(mPedometerRepositoryIml);
            mPedometerRepositoryIml = null;
        }

        mSensorManager = null;
        mContext = null;

    }

}
