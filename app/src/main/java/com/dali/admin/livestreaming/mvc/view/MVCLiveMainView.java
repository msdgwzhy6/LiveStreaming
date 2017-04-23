package com.dali.admin.livestreaming.mvc.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.adapter.PagerAdapter;
import com.dali.admin.livestreaming.base.BaseFragment;
import com.dali.admin.livestreaming.fragment.LiveListFragment;
import com.dali.admin.livestreaming.mvc.controller.MVCLiveMainController;

import java.util.ArrayList;

/**
 * Created by dali on 2017/4/10.
 */

public abstract class MVCLiveMainView extends BaseFragment {

    protected MVCLiveMainController mMainController;

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mTabStrip;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mFragments;

    public MVCLiveMainView() {
        mMainController = new MVCLiveMainController(this);
    }

    public void initViewPager(View rootView) {
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mTabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.pager_sliding_tab_strip);
        mTabStrip.setTextColorResource(R.color.white);
        mTabStrip.setIndicatorColorResource(R.color.white);
        mTabStrip.setDividerColor(Color.TRANSPARENT);
        mTabStrip.setTextSize(rootView.getResources().getDimensionPixelSize(R.dimen.h6));
        mTabStrip.setUnderlineHeight(1);
        mViewPager.setCurrentItem(0);
    }

    public void initViewPagerData(){
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        mTitles.add("最新");
        mTitles.add("最热");
        mTitles.add("达人");
        mTitles.add("活力");
        mTitles.add("英雄联盟");
        mTitles.add("王者荣耀");
        for (String s:mTitles){
            Bundle bundle = new Bundle();
            bundle.putString("title",s);
            mFragments.add(LiveListFragment.newInstance(bundle));
        }

        mViewPager.setAdapter(getAdapter());
        mTabStrip.setViewPager(mViewPager);
    }

    public FragmentStatePagerAdapter getAdapter() {
        return new PagerAdapter(mContext.getSupportFragmentManager(),mTitles,mFragments);
    }
}
