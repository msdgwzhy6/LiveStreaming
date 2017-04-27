package com.dali.admin.livestreaming.activity;

import android.content.Context;
import android.view.View;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.mvp.presenter.MainPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.IMainView;

public class MainActivity extends BaseActivity implements IMainView {

    private MainPresenter mMainPresenter;

    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setListener() {
        mMainPresenter.mTabHost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoke(MainActivity.this,PublishActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMainPresenter = new MainPresenter(this,this);
        mMainPresenter.initFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showMsg(int msgId) {

    }

    @Override
    public Context getContext() {
        return this;
    }

}
