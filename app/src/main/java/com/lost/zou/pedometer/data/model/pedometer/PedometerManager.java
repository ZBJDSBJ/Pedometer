package com.lost.zou.pedometer.data.model.pedometer;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lost.zou.pedometer.presentation.common.BaseApplication;
import com.lost.zou.pedometer.presentation.common.utils.HardwarePedometerUtil;
import com.lost.zou.pedometer.presentation.view.service.PedometerService;


/**
 * Created by zoubo
 * 计步器管理类：1、记步服务管理
 */
public class PedometerManager {
    private boolean mIsServiceRunning = false;

    private Context mContext;

    private Intent mService;


    public PedometerManager() {
        mContext = BaseApplication.getAppContext();

        mService = new Intent(mContext, PedometerService.class);
    }

    /**
     * 服务启动后初始化
     */
    public void init() {

    }


    /**
     * Service的检查与启动:1、登录并且卡片存在
     * 1、有TYPE_STEP_COUNTER； 2、版本为4.4(19)以上
     */
    public void startPedometerService() {
        mContext.startService(mService);
        mIsServiceRunning = true;
    }

    /**
     * 1、停止记步服务；2、删除当天数据
     */
    public void stopPedometerService() {
        mIsServiceRunning = false;
        mContext.stopService(mService);
    }


    /**
     * Service的检查与启动:登录并且卡片存在
     */
    public void checkServiceStart() {

        if (HardwarePedometerUtil.supportsHardwareStepCounter(mContext)) {
            ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("qibai.bike.bananacard.presentation.view.service.PedometerService".equals(service.service.getClassName())) {
                    mIsServiceRunning = true;
                }
            }

            Log.i("zou", "<PedometerManager> checkServiceStart mIsServiceRunning = " + mIsServiceRunning);

            if (!mIsServiceRunning) {
                mContext.startService(mService);
            }
        }

    }

}
