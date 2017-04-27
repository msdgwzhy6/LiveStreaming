package com.dali.admin.livestreaming.mvp.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.fragment.LiveMainFragment;
import com.dali.admin.livestreaming.fragment.UserInfoFragment;
import com.dali.admin.livestreaming.mvp.presenter.Ipresenter.IMainPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.IMainView;

/**
 * Created by dali on 2017/4/9.
 * MainActivity交互
 */

public class MainPresenter extends IMainPresenter {

    private IMainView mIMainView;

    public FragmentTabHost mTabHost;
    private final Class mFragmentArray[] = {LiveMainFragment.class, Fragment.class, UserInfoFragment.class};
    private int mImageViewArray[] = {R.drawable.tab_live_selector, R.drawable.tab_pubish_selector, R.drawable.tab_my_selector};
    private String mTextViewArray[] = {"live", "publish", "mine"};

    public MainPresenter(IMainView mainView, BaseActivity context) {
        super(mainView,context);
        mIMainView = mainView;
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {
        if (mIMainView!=null){
            mIMainView = null;
        }
    }


    @Override
    public void initFragment() {
        mTabHost = mContext.obtainView(android.R.id.tabhost);
        mTabHost.setup(mContext, mContext.getSupportFragmentManager(), R.id.contentPanel);

        int fragmentCount = mFragmentArray.length;
        for (int i = 0; i < fragmentCount; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            mTabHost.getTabWidget().setDividerDrawable(null);
        }
    }

    @Override
    protected View getTabItemView(int i) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.tab_live, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageResource(mImageViewArray[i]);
        return view;
    }

    @Override
    protected void checkCacheAndLogin() {
//        if (TextUtils.isEmpty(TIMManager.getInstance().getLoginUser())){
//            final IMLogin tcLoginMgr = IMLogin.getInstace();
//            final TLSUserInfo info = IMLogin.getInstace().getLastUserInfo();
//            tcLoginMgr.setIMLoginListener(new IMLogin.IMLoginListener() {
//                @Override
//                public void onSuccess() {
//                    tcLoginMgr.removeIMLoginListener();;
//                    ImUserInfoMgr.getInstance().setUserId(info.identifier,null);
//                }
//
//                @Override
//                public void onFailure(int code, String msg) {
//                    tcLoginMgr.removeIMLoginListener();
//                }
//            });
//            tcLoginMgr.checkCacheAndLogin();
//        }
    }
}
