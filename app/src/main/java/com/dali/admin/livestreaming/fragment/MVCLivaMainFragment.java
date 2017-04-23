package com.dali.admin.livestreaming.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.mvc.view.MVCLiveMainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MVCLivaMainFragment extends MVCLiveMainView {

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView(View rootView) {
        mMainController.initViewPager(rootView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mvcliva_main;
    }
}
