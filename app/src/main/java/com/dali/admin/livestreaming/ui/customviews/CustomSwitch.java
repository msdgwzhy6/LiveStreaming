package com.dali.admin.livestreaming.ui.customviews;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import com.dali.admin.livestreaming.R;

/**
 * 带动画的switch
 * Created by dali on 2017/4/26.
 */

public class CustomSwitch extends android.support.v7.widget.AppCompatImageView {

    private boolean mChecked = false;

    private AnimationDrawable mAnimationDrawable;
    private Handler mHandler;
    private Runnable mRunnable;


    public CustomSwitch(Context context) {
        this(context, null);
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                onAnimation();
            }
        };
    }

    /**
     * 动画播放
     */
    private void onAnimation() {
        if (mChecked) {
            setImageResource(R.drawable.btn_switch_on);
        } else {
            setImageResource(R.drawable.btn_switch_off);
        }
    }

    public void setChecked(boolean checked, boolean playAnim) {
        if (checked == mChecked) {
            return;
        }

        mChecked = checked;

        if (playAnim) {
            setImageResource(mChecked ? R.drawable.switch_open : R.drawable.switch_close);
            mAnimationDrawable = (AnimationDrawable) getDrawable();
            mAnimationDrawable.start();
            mHandler.postDelayed(mRunnable, getTotalDuration(mAnimationDrawable));
        }
    }

    /**
     * 返回总时长
     * @param animationDrawable
     * @return
     */
    private long getTotalDuration(AnimationDrawable animationDrawable) {
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        return duration;
    }

    public boolean isChecked() {
        return mChecked;
    }
}
