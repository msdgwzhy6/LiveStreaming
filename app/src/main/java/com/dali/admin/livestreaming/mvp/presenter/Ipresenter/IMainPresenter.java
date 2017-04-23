package com.dali.admin.livestreaming.mvp.presenter.Ipresenter;

import android.view.View;

import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.mvp.view.Iview.BaseView;

/**
 * Created by dali on 2017/4/9.
 */

public abstract class IMainPresenter implements BasePresenter{

    protected BaseView mBaseView;
    protected BaseActivity mContext;

    public IMainPresenter(BaseView baseView,BaseActivity context) {
        this.mBaseView = baseView;
        this.mContext = context;
    }

    //初始化 FragmentTabHost
    protected abstract void initFragment();

    //展示 Tab 选项卡
    protected abstract View getTabItemView(int i);

    /**
     * 监测缓存和登录
     */
    protected abstract void checkCacheAndLogin();
}
