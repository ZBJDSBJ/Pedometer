package com.lost.zou.pedometer.model.repository;


import com.lost.zou.pedometer.model.model.pedometer.IGetPedometerResult;

/**
 * Created by zoubo
 */
public interface PedometerRepository {

    /**
     * 获取计步器每日步数
     */
    void getPedometerStep(IGetPedometerResult result);
}
