package com.dali.admin.livestreaming.mvc.controller;

import android.view.View;

import com.dali.admin.livestreaming.mvc.view.MVCLiveMainView;

/**
 * Created by dali on 2017/4/10.
 */

public class MVCLiveMainController {

    private MVCLiveMainView mMainView;

    public void initViewPager(View rootView){
        mMainView.initViewPager(rootView);
        mMainView.initViewPagerData();
    }

    public MVCLiveMainController(MVCLiveMainView mainView) {
        this.mMainView = mainView;
    }

}
