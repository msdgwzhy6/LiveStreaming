package com.dali.admin.livestreaming;

import android.app.Application;
import android.util.Log;

import com.dali.admin.livestreaming.logic.IMInitMgr;
import com.dali.admin.livestreaming.logic.IMLogin;
import com.dali.admin.livestreaming.utils.LiveLogUtil;
import com.tencent.TIMManager;
import com.tencent.rtmp.TXLiveBase;

/**
 * Created by dali on 2017/4/22.
 */

public class LiveApplication extends Application{

    private static LiveApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        //判断是否在主进程，app至少由主进程和service两个进程，
        // app 初始化的时候，有几个进程onCreate就会调用几次，
        // 离线处理逻辑只需要在主进程调用，不需要在service调用
//        if (MsfSdkUtils.isMainProcess(this)){
//            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
//                @Override
//                public void handleNotification(TIMOfflinePushNotification timOfflinePushNotification) {
//                    timOfflinePushNotification.doNotify(getApplicationContext(),R.mipmap.ic_launcher);
//                }
//            });
//        }


        IMInitMgr.init(getApplicationContext());
        IMLogin.getInstace();

//        initSDK();

        //初始化
//        TIMManager.getInstance().init(getApplicationContext());

        Log.e("imLogin",TIMManager.getInstance().getVersion());
    }

    private void initSDK() {
        IMInitMgr.init(getApplicationContext());
        IMLogin.getInstace();
        TXLiveBase.getInstance().listener = new LiveLogUtil(getApplicationContext());
        Log.w("LiveLogUitil","app init sdk");
    }

//    private boolean shouldInit() {
//        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
//        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
//        String mainProcessName = getPackageName();
//        int myPid = android.os.Process.myPid();
//
//        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
//            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static LiveApplication getInstance(){
        return instance;
    }
}
