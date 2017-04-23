package com.dali.admin.livestreaming.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseFragment;
import com.dali.admin.livestreaming.mvp.presenter.LiveMainPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveMainFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mTabStrip;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mFragments;

    private LiveMainPresenter mLiveMainPresenter;


    @Override
    protected void initData() {

//        mTitles = new ArrayList<>();
//        mFragments = new ArrayList<>();
//        mTitles.add("最新");
//        mTitles.add("最热");
//        mTitles.add("达人");
//        mTitles.add("活力");
//        mTitles.add("英雄联盟");
//        mTitles.add("王者荣耀");
//        for (String s:mTitles){
//            Bundle bundle = new Bundle();
//            bundle.putString("title",s);
//            mFragments.add(LiveListFragment.newInstance(bundle));
//        }
//        mLiveMainPresenter = new LiveMainPresenter(mContext,mTitles,mFragments);
//
//        mViewPager.setAdapter(mLiveMainPresenter.getAdapter());
//        mTabStrip.setViewPager(mViewPager);
//        mViewPager.setCurrentItem(0);


    }

    @Override
    protected void setListener() {
        obtainView(R.id.iv_search).setOnClickListener(this);
        obtainView(R.id.iv_message).setOnClickListener(this);
    }

    @Override
    protected void initView(View rootView) {
//        mViewPager = obtainView(R.id.viewpager);
//        mTabStrip = obtainView(R.id.pager_sliding_tab_strip);
//        mTabStrip.setTextColorResource(R.color.white);
//        mTabStrip.setIndicatorColorResource(R.color.white);
//        mTabStrip.setDividerColor(Color.TRANSPARENT);
//        mTabStrip.setTextSize(getResources().getDimensionPixelSize(R.dimen.h6));
//        mTabStrip.setUnderlineHeight(1);

        mLiveMainPresenter = new LiveMainPresenter(mContext);
        mLiveMainPresenter.initViewPager(rootView);
        mLiveMainPresenter.initViewPagerData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_main;
    }


    @Override
    public void onClick(View v) {

    }
}
