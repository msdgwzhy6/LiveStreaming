package com.dali.admin.livestreaming.mvc.view;

import android.content.Context;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.mvc.controller.MVCMainController;
import com.dali.admin.livestreaming.mvc.view.Iview.MVCIMainView;

public class MVCMainActivity extends MVCIMainView{

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
        mMainController = new MVCMainController(this,this);
        mMainController.initFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvcmain;
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
