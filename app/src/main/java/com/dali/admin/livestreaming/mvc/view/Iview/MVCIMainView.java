package com.dali.admin.livestreaming.mvc.view.Iview;

import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.fragment.MVCLivaMainFragment;
import com.dali.admin.livestreaming.fragment.PublishFragment;
import com.dali.admin.livestreaming.fragment.UserInfoFragment;
import com.dali.admin.livestreaming.mvc.controller.MVCMainController;

/**
 * Created by dali on 2017/4/16.
 */

public abstract class MVCIMainView extends BaseActivity implements MVCBaseView {

    protected MVCMainController mMainController;

    public FragmentTabHost mTabHost;
    protected final Class mFragmentArray[] = {MVCLivaMainFragment.class, PublishFragment.class, UserInfoFragment.class};
    protected int mImageViewArray[] = {R.drawable.tab_live_selector, R.drawable.tab_pubish_selector, R.drawable.tab_my_selector};
    protected String mTextViewArray[] = {"live", "publish", "mine"};

    public void setModel(){
        mTabHost = obtainView(android.R.id.tabhost);
        mTabHost.setup(mContext,getSupportFragmentManager(), R.id.contentPanel);
        int fragmentCount = mFragmentArray.length;
        for (int i = 0; i < fragmentCount; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i]).setIndicator(getTabItemView(i,mImageViewArray));
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            mTabHost.getTabWidget().setDividerDrawable(null);
        }
    }

    private View getTabItemView(int i,int imageViewArray[]) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.tab_live, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageResource(imageViewArray[i]);
        return view;
    }
}
