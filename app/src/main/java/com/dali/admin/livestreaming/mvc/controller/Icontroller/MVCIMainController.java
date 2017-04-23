package com.dali.admin.livestreaming.mvc.controller.Icontroller;

import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.mvc.view.Iview.MVCBaseView;

/**
 * Created by dali on 2017/4/16.
 */

public abstract class MVCIMainController {
    protected MVCBaseView mBaseView;
    protected BaseActivity mContext;

    public MVCIMainController(MVCBaseView baseView,BaseActivity context) {
        this.mBaseView = baseView;
        this.mContext = context;
    }

    //初始化 FragmentTabHost
    protected abstract void initFragment();
}
