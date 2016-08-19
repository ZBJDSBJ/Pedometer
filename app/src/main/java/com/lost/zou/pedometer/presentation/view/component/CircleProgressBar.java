package com.lost.zou.pedometer.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Alost on 16/8/13.
 * 环形进度条
 */
public class CircleProgressBar extends ProgressBar {

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
