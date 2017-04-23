package com.dali.admin.livestreaming.mvc.view;

import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.mvc.controller.MVCLoginController;
import com.dali.admin.livestreaming.mvc.view.Iview.MVCILoginView;
import com.dali.admin.livestreaming.mvp.model.UserInfo;

/**
 * Created by dali on 2017/4/10.
 */

public abstract class MVCLoginView extends BaseActivity implements MVCILoginView {

    protected UserInfo mUserInfo;
    protected MVCLoginController mController;

    public void setModel(UserInfo info){
        if (mUserInfo == null){
            throw new NullPointerException("no model");
        }
        this.mUserInfo = info;
    }
}
