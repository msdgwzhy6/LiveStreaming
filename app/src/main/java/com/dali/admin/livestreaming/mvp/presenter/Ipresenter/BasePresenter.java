package com.dali.admin.livestreaming.mvp.presenter.Ipresenter;

/**
 * Created by admin on 2017/4/7.
 */

public interface BasePresenter{

    /**
     * presenter 开始处理方法
     */
    void start();

    /**
     * 处理一些销毁工作，在界面结束时候调用
     */
    void finish();
}
