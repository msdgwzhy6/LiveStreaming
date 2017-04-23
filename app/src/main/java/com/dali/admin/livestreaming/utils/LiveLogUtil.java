package com.dali.admin.livestreaming.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.tencent.rtmp.ITXLiveBaseListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 内部log，并保存到文件
 * 1.实现ITXLiveBaseListener回调接口获取RTMPSDK的log
 * 2.开启单独线程将log保存到sdcard路径：tencent/imsdklogs/com/tencent/qcloud/xiaozhibo/rtmpsdk_日期.log
 *   其中日期以天为单位，每天保存一个文件，如rtmpsdk_20160901.log
 * 3.app的log使用TXLog和RTMPSDK的log一起保存
 * Created by dali on 2017/4/23.
 */

public class LiveLogUtil implements ITXLiveBaseListener {

    private static int LOG_MSG = 1001;
    private static String LOG_PATH = "/tencent/imsdklogs/";
    private Handler mLogHandler;
    private HandlerThread mLogThread;

    public LiveLogUtil(Context context) {
        mLogThread = new HandlerThread("TCLogThread");
        mLogThread.start();
        mLogHandler = new TCLogHandler(context,mLogThread.getLooper());
    }


    /**
     * RTMP SDK log 回调，app自己保存log
     * @param level
     * @param module
     * @param msg
     */
    @Override
    public void OnLog(int level, String module, String msg) {
        Bundle data = new Bundle();
        data.putInt("LEVEL",level);
        data.putString("MODULE",module);
        data.putString("MSG",msg);
        Message logMsg = new Message();
        logMsg.what = LOG_MSG;
        logMsg.setData(data);
        if (mLogHandler != null){
            mLogHandler.sendMessage(logMsg);
        }
    }

    private class TCLogHandler extends Handler {

        private FileOutputStream mLogOutputStream;
        private Context mContext;
        public TCLogHandler(Context context, Looper looper) {
            super(looper);
            this.mContext = context;
            openLogFile();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOG_MSG){
                Bundle data = msg.getData();
                if (data!=null){
                    appendMsg(data.getInt("LEVEL",0),data.getString("MODULE",""),data.getString("MSG",""));
                }
            }
        }

        /**
         * 追加消息
         * @param level
         * @param module
         * @param msg
         */
        private void appendMsg(int level,String module,String msg) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String data = sdf.format(System.currentTimeMillis());
            if (mLogOutputStream != null){
                String logMsg = data +"|level:"+level+"|module:"+module+"|msg:"+msg+"\n";
                try {
                    mLogOutputStream.write(logMsg.getBytes());
                    Log.d(module,msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 打开Log文件
         */
        private void openLogFile() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String data = sdf.format(System.currentTimeMillis());

            File sdcard = Environment.getExternalStorageDirectory();
            String pkgName = mContext.getPackageName();
            String pkgPath = pkgName.replace(".","/");
            File dir = new File(sdcard.getAbsolutePath()+LOG_PATH+pkgPath);
            if (!dir.exists()){
                dir.mkdirs();
            }

            String fileName = "rtmpsdk "+data+".log";
            File logFile = new File(dir,fileName);
            try {
                mLogOutputStream = new FileOutputStream(logFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                mLogOutputStream = null;
            }
        }
    }
}
