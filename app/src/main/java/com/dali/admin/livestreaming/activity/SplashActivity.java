package com.dali.admin.livestreaming.activity;

import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.tencent.TIMManager;

public class SplashActivity extends BaseActivity {

    private RelativeLayout mRlSplash;


    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mRlSplash = obtainView(R.id.rl_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Log.d("IMSDK", TIMManager.getInstance().getVersion());

        startAnimation();

    }

    private void startAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);
        animation.setFillAfter(true);
        animation.setDuration(3000);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                invoke(SplashActivity.this,LoginActivity.class);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mRlSplash.startAnimation(animation);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

}
