package com.dali.admin.livestreaming.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dali.admin.livestreaming.mvc.model.MVCLiveModel;

import java.util.ArrayList;

/**
 * Created by dali on 2017/4/10.
 */

public class MVCLiveMainPagerAdapter extends FragmentStatePagerAdapter {

   private ArrayList<MVCLiveModel> mModels;

    public MVCLiveMainPagerAdapter(FragmentManager fm,ArrayList<MVCLiveModel> models) {
        super(fm);
        this.mModels = models;
    }

    @Override
    public Fragment getItem(int position) {
        return mModels.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mModels.get(position).getTitle();
    }
}
