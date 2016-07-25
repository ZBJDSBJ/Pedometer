package com.lost.zou.pedometer.model.model.pedometer;


import com.lost.zou.pedometer.model.model.database.PedometerCardEntity;

/**
 * Created by zoubo
 */
public interface IGetPedometerResult {

    void onSuccessGet(final PedometerCardEntity cardEntity);

}
