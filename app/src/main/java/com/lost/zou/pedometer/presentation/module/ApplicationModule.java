/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lost.zou.pedometer.presentation.module;

import android.content.Context;

import com.lost.zou.pedometer.data.model.pedometer.PedometerManager;
import com.lost.zou.pedometer.data.model.pedometer.PedometerRepositoryIml;
import com.lost.zou.pedometer.presentation.common.BaseApplication;


public class ApplicationModule {

    private final Context mAppContent;

    private static ApplicationModule sInstance;

    private PedometerManager mPedometerManager;// 计步器管理类:通知和定时器等
    private PedometerRepositoryIml mPedometerRepository;


    /**
     * 初始化单例,在程序启动时调用<br>
     */
    public static ApplicationModule initSingleton() {
        if (sInstance == null) {
            sInstance = new ApplicationModule();
        }

        return sInstance;
    }

    public static ApplicationModule getInstance() {
        return sInstance;
    }

    public void onCreateMainProcess() {
        mPedometerManager = new PedometerManager();
        mPedometerRepository = new PedometerRepositoryIml();

    }


    private ApplicationModule() {
        mAppContent = BaseApplication.getAppContext();
    }


    public PedometerManager getPedometerManager() {
        return mPedometerManager;
    }

    public PedometerRepositoryIml getPedometerRepository() {
        return mPedometerRepository;
    }


}
