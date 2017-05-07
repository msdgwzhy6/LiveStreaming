package com.dali.admin.livestreaming.mvp.view.Iview;

import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by dali on 2017/5/7.
 */

public interface IPusherView extends BaseView {
    /**
     * 获取推流地址
     * @param pushUrl
     * @param errorCode 0表示成功 1表示失败
     */
    void onGetPushUrl(String pushUrl, int errorCode);

    /**
     * 推流事件
     * @param event
     * @param bundle
     */
    void onPushEvent(int event, Bundle bundle);

    /**
     * 网络状态
     * @param bundle
     */
    void onNetStatus(Bundle bundle);

    /**
     * 获取FragmentManager
     * @return
     */
    FragmentManager getFragmentMgr();

    /**
     * 结束
     */
    void finish();
}
