package com.lost.zou.pedometer.presentation.view.iview;


import com.lost.zou.pedometer.data.model.database.PedometerCardEntity;

/**
 * Created by zoubo
 * 计步器view接口
 */
public interface IPedometerView {
    void onReaderPedometer(PedometerCardEntity cardEntity);
}
