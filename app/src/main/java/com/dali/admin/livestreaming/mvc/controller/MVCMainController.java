package com.dali.admin.livestreaming.mvc.controller;

import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.mvc.controller.Icontroller.MVCIMainController;
import com.dali.admin.livestreaming.mvc.view.Iview.MVCIMainView;

/**
 * Created by dali on 2017/4/16.
 */

public class MVCMainController extends MVCIMainController{

    private MVCIMainView mIMainView;

    public MVCMainController(MVCIMainView mainView, BaseActivity context) {
        super(mainView,context);
        mIMainView = mainView;
    }


    @Override
    public void initFragment() {
        mIMainView.setModel();
    }
}

