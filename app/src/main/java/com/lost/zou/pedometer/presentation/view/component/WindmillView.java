package com.lost.zou.pedometer.presentation.view.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.lost.zou.pedometer.R;

/**
 * Created by Alost on 16/8/18.
 * 风车自定义View
 * 1、画扇叶,采用贝塞尔曲线
 * 2、旋转扇叶
 */
public class WindmillView extends View {
    private Context mContext;

    private static final int DEFAULT_LEAF_COLOR = Color.RED;
    private int mLeafColor = DEFAULT_LEAF_COLOR;
    private int mCircleRadius = 5;
    private float mDefaultBezierDel = 4.3f;  // 贝塞尔曲线坐标默认差值


    private Paint mPaint;

    private float mWidth, mHeight;
    private float centerX, centerY; //圆心
    //扇叶的五个坐标点
    private float x1, y1, x2, y2, x3, y3, x4, y4, x5, y5;

    private RotateAnimation mAnimation;

    private int mWindVelocity = 1; //风速


    public WindmillView(Context context) {
        this(context, null);
    }

    public WindmillView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindmillView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WindmillView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.WindmillView_leaf_color:
                    mLeafColor = array.getColor(attr, DEFAULT_LEAF_COLOR);
                    break;
                case R.styleable.WindmillView_WindVelocity:
                    mWindVelocity = array.getInt(attr, mWindVelocity);
                    break;
                case R.styleable.WindmillView_CircleRadius:
                    mCircleRadius = array.getInt(attr, mCircleRadius);
                    break;
            }
        }
        array.recycle();

        init();
    }

    private void init() {
        initPaint();

        mAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setRepeatCount(-1); //设置为无限重复
        mAnimation.setInterpolator(new LinearInterpolator());//匀速
        mAnimation.setFillAfter(true);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(mLeafColor);
    }


    float mRadius;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        float width = mWidth - getPaddingLeft() - getPaddingRight();
        float height = mHeight - getPaddingTop() - getPaddingBottom();
        mRadius = Math.min(width, height);

        measureBezier();
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }

    /**
     * 给出贝塞尔曲线坐标
     */
    private void measureBezier() {
        x1 = mRadius / 2;
        y1 = mRadius / 2 - mCircleRadius;
        x2 = mRadius / 2 + mCircleRadius;
        y2 = mRadius / 2 - mDefaultBezierDel * 2.5f;
        x3 = x2;
        y3 = mRadius / 2 - mDefaultBezierDel * 3;
        x4 = mRadius / 2;
        y4 = 0;
        x5 = mRadius / 2 - mDefaultBezierDel * 0.5f;
        y5 = y2 / 2;

        centerX = mRadius / 2;
        centerY = mRadius / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        canvas.translate((mWidth - mRadius) / 2, (mHeight - mRadius) / 2);
        drawPoint(canvas);
        drawFan(canvas);

        canvas.restore();
    }

    /**
     * 绘制中心圆点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, mCircleRadius, mPaint);
    }

    /**
     * 绘制扇叶
     *
     * @param canvas
     */
    private void drawFan(Canvas canvas) {
        Path path = new Path();
        path.moveTo(x1, y1);

        path.cubicTo(x2, y2, x3, y3, x4, y4);
        path.quadTo(x5, y5, x1, y1);
        canvas.drawPath(path, mPaint);

        canvas.rotate(120, centerX, centerY);
        canvas.drawPath(path, mPaint);

        canvas.rotate(120, centerX, centerY);
        canvas.drawPath(path, mPaint);
    }


    public void setWindVelocity(int value) {
        if (value == 0) {
            value = 1;
        }
        if (value > 15) {
            value = 15;
        }
        this.mWindVelocity = value;
    }

    public void startAnim() {
        stopAnim();
        mAnimation.setDuration(1800 - mWindVelocity * 100);
        Log.i("zou", 1800 - mWindVelocity * 100 + "");
        startAnimation(mAnimation);
    }

    public void stopAnim() {
        clearAnimation();
    }


}
