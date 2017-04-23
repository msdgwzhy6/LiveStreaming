package com.dali.admin.livestreaming.logic;

import android.content.Context;

import com.tencent.TIMManager;

/**
 * Created by dali on 2017/4/22.
 */

public class IMInitMgr {
    private static boolean isSDKInit = false;

    public static void init(final Context context) {

        if (isSDKInit)
            return;

        //禁止服务器自动代替上报已读
//        TIMManager.getInstance().disableAutoReport();
        //初始化imsdk
        TIMManager.getInstance().init(context);
        //初始化群设置
//        TIMManager.getInstance().initGroupSettings(new TIMGroupSettings());

        //注册sig失败监听回调
//        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
//            @Override
//            public void onForceOffline() {
//                LocalBroadcastManager.getInstance(context.getApplicationContext())
//                .sendBroadcast(new Intent(Constants.EXIT_APP));
//            }
//
//            @Override
//            public void onUserSigExpired() {
//                IMLogin.getInstace().reLogin();
//            }
//        });

        //初始化登录模块
        IMLogin.getInstace();

        isSDKInit = true;
    }
}
