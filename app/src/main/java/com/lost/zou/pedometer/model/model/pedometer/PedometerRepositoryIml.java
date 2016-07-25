package com.lost.zou.pedometer.model.model.pedometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.lost.zou.pedometer.model.model.database.PedometerCardEntity;
import com.lost.zou.pedometer.model.repository.PedometerRepository;
import com.lost.zou.pedometer.presentation.common.BaseApplication;
import com.lost.zou.pedometer.presentation.common.utils.DateUtil;


/**
 * @author zoubo
 *         计步器
 */
public class PedometerRepositoryIml implements SensorEventListener, PedometerRepository {
    private Context mContext;

    private static int FIRST_STEP_COUNT = 0;    //每天打开软件记录的初始步数，用于带count传感器算法的方式
    private static int CURRENT_STEP = 0;        //当天的记步数总数


    /**
     * 加速度传感器计算相应参数
     */
    private static float SENSITIVITY = 6.0f;   //SENSITIVITY灵敏度
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;
    private static long end = 0;
    private static long start = 0;

    /**
     * 最后加速度方向
     */
    private float mLastDirections[] = new float[3 * 2];
    private float mLastExtremes[][] = {new float[3 * 2], new float[3 * 2]};
    private float mLastDiff[] = new float[3 * 2];
    private int mLastMatch = -1;


    private PedometerCardEntity mTodayStepEntity;
    private static int TODAY_ENTITY_STEPS;


    public PedometerRepositoryIml() {
        mContext = BaseApplication.getAppContext();
    }

    /**
     *  初始化传感器相关数据
     */
    public void initData() {
        initAccData();

        FIRST_STEP_COUNT = 0;
        CURRENT_STEP = 0;
        TODAY_ENTITY_STEPS = 0;

        mTodayStepEntity = new PedometerCardEntity(null, 0.0, DateUtil.getStringDateShort(), 0, 0.0, "记步", 100, 0);

        Log.i("zou", "<PedometerRepositoryIml> initData TODAY_ENTITY_STEPS = " + TODAY_ENTITY_STEPS
                + "getTargetStepCount= " + mTodayStepEntity.getTargetStepCount());
    }

    private void initAccData() {
        int h = 480;
        mYOffset = h * 0.5f;
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor == null) {
            return;
        }

        synchronized (this) {
            if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                // Step Counter，要准确很多,读取开机的传感器步数
                if (FIRST_STEP_COUNT == 0) {
                    FIRST_STEP_COUNT = (int) event.values[0];
                } else {
                    CURRENT_STEP = Math.abs((int) event.values[0] - FIRST_STEP_COUNT);
                    showSteps(CURRENT_STEP);
                    Log.i("zou", "<PedometerRepositoryIml> onSensorChanged TYPE_STEP_COUNTER event.values[0]= " + event.values[0]
                            + " CURRENT_STEP = " + CURRENT_STEP);
                }
            } else if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //加速度取模
                float vSum = 0;
                for (int i = 0; i < 3; i++) {
                    final float v = mYOffset + event.values[i] * mScale[1];
                    vSum += v;
                }
                int k = 0;
                float v = vSum / 3;

                float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
                if (direction == -mLastDirections[k]) {
                    // Direction changed
                    int extType = (direction > 0 ? 0 : 1); // minumum or
                    // maximum?
                    mLastExtremes[extType][k] = mLastValues[k];
                    float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);

                    if (diff > SENSITIVITY) {
                        boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                        boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                        boolean isNotContra = (mLastMatch != 1 - extType);

                        if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                            end = System.currentTimeMillis();

                            //步数矫正，步伐间隔<200ms和>2000ms，认为是无效步数,这部分也是目前终端计步器算法的核心。
                            if (end - start > 200 && end - start < 2000) {  // 此时判断为走了一步
                                CURRENT_STEP++;
                                mLastMatch = extType;
                                showSteps(CURRENT_STEP);
                            }
                            start = end;
                        } else {
                            mLastMatch = -1;
                        }
                    }
                    mLastDiff[k] = diff;
                }
                mLastDirections[k] = direction;
                mLastValues[k] = v;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /**
     * 获取行走步数
     */
    private void showSteps(int steps) {
        if (steps < 0) {
            return;
        }
        steps = steps + TODAY_ENTITY_STEPS;

        Log.i("zou", "<PedometerRepositoryIml> showSteps TODAY_ENTITY_STEPS= " + TODAY_ENTITY_STEPS + " steps = " + steps);

        mTodayStepEntity.setStepCount(steps);

        PedometerEvent event = new PedometerEvent();
        event.mIsUpdate = true;
        BaseApplication.postEvent(event);

    }

    @Override
    public void getPedometerStep(IGetPedometerResult result) {

        result.onSuccessGet(mTodayStepEntity);
    }

    public void onDestroy() {
        FIRST_STEP_COUNT = 0;
        mTodayStepEntity = null;
    }

}
