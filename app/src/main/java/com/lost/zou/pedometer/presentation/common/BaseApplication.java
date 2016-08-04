package com.lost.zou.pedometer.presentation.common;

import android.app.Application;
import android.content.Context;

import com.lost.zou.pedometer.presentation.common.utils.DrawUtil;
import com.lost.zou.pedometer.presentation.common.utils.FileUtil;

import org.greenrobot.eventbus.EventBus;


/**
 * application
 */
public class BaseApplication extends Application {

    protected static BaseApplication sInstance;

    protected final static EventBus GLOBAL_EVENT_BUS = EventBus.getDefault();


    public BaseApplication() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DrawUtil.resetDensity(this);

        new Thread(new Runnable() {
            @Override
            public void run() {

                FileUtil.createNewFile(Constant.Path.ACCOUNT_DIR);
            }
        }).start();

    }

    public static Context getAppContext() {
        return sInstance;
    }

    /**
     * 获取一个全局的EventBus实例<br>
     *
     * @return
     */
    public static EventBus getGlobalEventBus() {
        return GLOBAL_EVENT_BUS;
    }

    /**
     * 使用全局EventBus post一个事件<br>
     *
     * @param event
     */
    public static void postEvent(Object event) {
        GLOBAL_EVENT_BUS.post(event);
    }

    /**
     * 使用全局EventBus post一个Sticky事件<br>
     *
     * @param event
     */
    public static void postStickyEvent(Object event) {
        GLOBAL_EVENT_BUS.postSticky(event);
    }

    /**
     * 注册事件
     *
     * @param object
     */
    public static void globalRegisterEvent(Object object) {
        GLOBAL_EVENT_BUS.register(object);
    }

    /**
     * 反注册事件
     *
     * @param object
     */
    public static void globalUnRegisterEvent(Object object) {
        GLOBAL_EVENT_BUS.unregister(object);
    }


}
