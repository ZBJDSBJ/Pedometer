package com.lost.zou.pedometer.presentation.module;

import com.lost.zou.pedometer.presentation.common.BaseApplication;


public class PedometerApplication extends BaseApplication {


    public PedometerApplication() {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationModule.initSingleton().onCreateMainProcess();

    }



}
