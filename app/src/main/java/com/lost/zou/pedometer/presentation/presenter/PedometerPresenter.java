package com.lost.zou.pedometer.presentation.presenter;


import com.lost.zou.pedometer.model.model.database.PedometerCardEntity;
import com.lost.zou.pedometer.model.model.pedometer.IGetPedometerResult;
import com.lost.zou.pedometer.model.model.pedometer.PedometerEvent;
import com.lost.zou.pedometer.model.repository.PedometerRepository;
import com.lost.zou.pedometer.presentation.common.BaseApplication;
import com.lost.zou.pedometer.presentation.module.ApplicationModule;
import com.lost.zou.pedometer.presentation.view.iview.IPedometerView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by zoubo
 * 计步器presenter
 */
public class PedometerPresenter implements Presenter {
    private IPedometerView mView;
    private PedometerRepository mPedometerRepository;

    public PedometerPresenter(IPedometerView view) {
        BaseApplication.globalRegisterEvent(this);

        mView = view;
        mPedometerRepository = ApplicationModule.getInstance().getPedometerRepository();
    }

    @Override
    public void resume() {
        //第一次初始化数据:包括退出应用后再进入的数据初始化
        getPedometerStep();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        BaseApplication.globalUnRegisterEvent(this);
    }

    /**
     * 监听数据
     */
    @Subscribe
    public void onEventMainThread(PedometerEvent event) {
        if (event.mIsUpdate) {
            getPedometerStep();
        }
    }

    /**
     * 获取当天计步器的数据：1、如果当天有记步数据，则返回当天的记步数据
     */
    public void getPedometerStep() {
        mPedometerRepository.getPedometerStep(new IGetPedometerResult() {
            @Override
            public void onSuccessGet(PedometerCardEntity cardEntity) {
                if (mView != null) {
                    mView.onReaderPedometer(cardEntity);
                }
            }

        });
    }
}