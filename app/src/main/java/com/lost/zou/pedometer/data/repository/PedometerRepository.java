package com.lost.zou.pedometer.data.repository;


import com.lost.zou.pedometer.data.model.pedometer.IGetPedometerResult;

/**
 * Created by zoubo
 */
public interface PedometerRepository {

    /**
     * 获取计步器每日步数
     */
    void getPedometerStep(IGetPedometerResult result);
}
