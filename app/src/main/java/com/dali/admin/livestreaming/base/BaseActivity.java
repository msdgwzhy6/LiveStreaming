package com.dali.admin.livestreaming.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dali on 2017/3/10. 20:27
 */
public abstract class BaseActivity extends FragmentActivity {

    protected Context mContext;
    protected Handler mHandler = new Handler();

    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBeforeLayout();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        if (getLayoutId() != 0){
            setContentView(getLayoutId());
        }

        initView();
        initData();
        setActionBar();
        setListener();
    }


    protected abstract void setActionBar();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 返回当前布局文件
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 设置布局
     */
    protected void setBeforeLayout(){}

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取资源id
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T obtainView(int resId){
        return (T)findViewById(resId);
    }

    /**
     * 显示toast
     * @param resId
     */
    public void showToast(final int resId){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this,getString(resId),Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示toast
     * @param msg
     */
    public void showToast(final String msg){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void invoke(Context context,Class clz){
        startActivity(new Intent(context,clz));
    }

}

