//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？

package com.lost.zou.pedometer;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lost.zou.pedometer.data.model.database.core.PedometerCardEntity;
import com.lost.zou.pedometer.presentation.common.utils.HardwarePedometerUtil;
import com.lost.zou.pedometer.presentation.module.ApplicationModule;
import com.lost.zou.pedometer.presentation.presenter.PedometerPresenter;
import com.lost.zou.pedometer.presentation.view.activity.BaseActivity;
import com.lost.zou.pedometer.presentation.view.component.leafLoading.AnimationUtils;
import com.lost.zou.pedometer.presentation.view.component.leafLoading.LeafLoadingView;
import com.lost.zou.pedometer.presentation.view.iview.IPedometerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IPedometerView {

    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_steps)
    TextView tvSteps;

    PedometerPresenter mPedometerPresenter;
    @BindView(R.id.tv_accelerometer)
    TextView tvAccelerometer;
    @BindView(R.id.tv_step_counter)
    TextView tvStepCounter;
    @BindView(R.id.tv_target_steps)
    TextView tvTargetSteps;


    @BindView(R.id.leaf_loading)
    LeafLoadingView leafLoading;
    @BindView(R.id.fan_pic)
    ImageView fanPic;
    @BindView(R.id.leaf_content)
    RelativeLayout leafContent;


    private boolean mIsStart = false;

    private int mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mPedometerPresenter = new PedometerPresenter(this);
        mPedometerPresenter.resume();

        if (HardwarePedometerUtil.supportsHardwareAccelerometer(this)) {
            tvAccelerometer.setText("是");
        } else {
            tvAccelerometer.setText("否");
        }

        if (HardwarePedometerUtil.supportsHardwareStepCounter(this)) {
            tvStepCounter.setText("是");
        } else {
            tvStepCounter.setText("否");
        }
    }

    @Override
    public void onReaderPedometer(PedometerCardEntity cardEntity) {

        if (cardEntity != null) {
            tvSteps.setText(cardEntity.getStepCount() + "步");
            tvTargetSteps.setText("目标步数：" + cardEntity.getTargetStepCount() + "步");

            mProgress = (int) (100 * cardEntity.getStepCount() / (cardEntity.getTargetStepCount() * 1.0f));

            RotateAnimation rotateAnimation = AnimationUtils.initRotateAnimation(false, 1500, true,
                    Animation.INFINITE);
            fanPic.startAnimation(rotateAnimation);
            leafLoading.setProgress(mProgress);

        }

    }

    @OnClick({R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (!mIsStart) {
                    mIsStart = true;
                    ApplicationModule.getInstance().getPedometerManager().startPedometerService();
                    btnStart.setText("停止计步");
                } else {
                    mIsStart = false;
                    ApplicationModule.getInstance().getPedometerManager().stopPedometerService();
                    btnStart.setText("开始计步");
                }

                break;
        }
    }
}
