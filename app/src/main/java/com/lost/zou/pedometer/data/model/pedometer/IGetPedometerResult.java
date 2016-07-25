package com.lost.zou.pedometer.data.model.pedometer;


import com.lost.zou.pedometer.data.model.database.PedometerCardEntity;

/**
 * Created by zoubo
 */
public interface IGetPedometerResult {

    void onSuccessGet(final PedometerCardEntity cardEntity);

}
