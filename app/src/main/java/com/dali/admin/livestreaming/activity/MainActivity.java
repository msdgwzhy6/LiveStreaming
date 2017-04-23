package com.dali.admin.livestreaming.activity;

import android.content.Context;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.mvp.presenter.MainPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.IMainView;

public class MainActivity extends BaseActivity implements IMainView {

//    private FragmentTabHost mTabHost;
//    private final Class mFragmentArray[] = {LiveMainFragment.class, PublishFragment.class, UserInfoFragment.class};
//    private int mImageViewArray[] = {R.drawable.tab_live_selector, R.drawable.tab_pubish_selector, R.drawable.tab_my_selector};
//    private String mTextViewArray[] = {"live", "publish", "mine"};

    private MainPresenter mMainPresenter;

    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
//        int fragmentCount = mFragmentArray.length;
//        for (int i = 0; i < fragmentCount; i++) {
//            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i]).setIndicator(getTabItemView(i));
//            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
//            mTabHost.getTabWidget().setDividerDrawable(null);
//        }
    }

    /**
     * 动态获取tab
     * @return
     */
//    private View getTabItemView(int i) {
//        View view;
//        view = LayoutInflater.from(this).inflate(R.layout.tab_live, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
//        imageView.setImageResource(mImageViewArray[i]);
//        return view;
//    }

    @Override
    protected void initView() {
//        mTabHost = obtainView(android.R.id.tabhost);
//        mTabHost.setup(this, getSupportFragmentManager(), R.id.contentPanel);

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
