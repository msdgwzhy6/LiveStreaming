package com.dali.admin.livestreaming.mvc.model;

import android.support.v4.app.Fragment;

/**
 * Created by dali on 2017/4/10.
 */

public class MVCLiveModel {

    private String mTitle;
    private Fragment mFragment;

    public MVCLiveModel(String title, Fragment fragment) {
        mTitle = title;
        mFragment = fragment;
    }

    public MVCLiveModel() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }
}
