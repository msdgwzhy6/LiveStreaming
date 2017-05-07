package com.dali.admin.livestreaming.mvp.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.http.AsyncHttp;
import com.dali.admin.livestreaming.http.request.CreateLiveRequest;
import com.dali.admin.livestreaming.http.request.RequestComm;
import com.dali.admin.livestreaming.http.request.StopLiveRequest;
import com.dali.admin.livestreaming.http.response.CreateLiveResp;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.mvp.presenter.Ipresenter.IPusherPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.IPusherView;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * 推流
 * Created by dali on 2017/5/7.
 */

public class PusherPresenter extends IPusherPresenter {

    public static final int LIVE_STATUS_ONLINE = 1;
    public static final int LIVE_STATUS_OFFLINE = 0;

    private final static String TAG = PusherPresenter.class.getSimpleName();

    private IPusherView mPusherView;
    private TXLivePusher mTXLivePusher;
    private TXCloudVideoView mTXCloudVideoView;

    private PopupWindow mPopupWindow;
    private int mLocX;
    private int mLocY;
    private boolean mflashOn = false;
//    private Bea

//    private int mBeautyLevel;


    public PusherPresenter(IPusherView pusherView) {
        super(pusherView);
        this.mPusherView = pusherView;
    }


    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    @Override
    public void getPusherUrl(String userId, String groupId, String title, String coverPic, String nickName, String headPic, String location) {
        final CreateLiveRequest request = new CreateLiveRequest(RequestComm.createLive,userId,groupId,title,coverPic,location,0);
        AsyncHttp.instance().post(request, new AsyncHttp.IHttpListener() {
            @Override
            public void onStart(int requestId) {

            }

            @Override
            public void onSuccess(int requestId, Response response) {
                if (response.getStatus() == RequestComm.SUCCESS){
                    CreateLiveResp resp = (CreateLiveResp) response.getData();
                    if (resp != null){
                        if (!TextUtils.isEmpty(resp.getPushUrl())){
                            mPusherView.onGetPushUrl(resp.getPushUrl(),0);
                        }else {
                            mPusherView.onGetPushUrl(null,1);
                        }
                    }
                }else {
                    mPusherView.showMsg(response.getMsg());
                    mPusherView.finish();
                }
            }

            @Override
            public void onFailure(int requestId, int httpStatus, Throwable error) {
                mPusherView.onGetPushUrl(null,1);
            }
        });
    }

    @Override
    public void startPusher(TXCloudVideoView videoView, TXLivePushConfig pusherConfig, String pushUrl) {
        if (mTXLivePusher == null){
            mTXLivePusher = new TXLivePusher(mPusherView.getContext());
            mTXLivePusher.setConfig(pusherConfig);
        }
        mTXCloudVideoView = videoView;
        mTXCloudVideoView.setVisibility(View.VISIBLE);
        mTXLivePusher.startCameraPreview(mTXCloudVideoView);
        mTXLivePusher.startPusher(pushUrl);
    }

    @Override
    public void setConfig(TXLivePushConfig pusherConfig) {
        if (mTXLivePusher == null){
            mTXLivePusher.setConfig(pusherConfig);
        }
    }

    @Override
    public void stopPusher() {
        if (mTXLivePusher == null){
            mTXLivePusher.stopCameraPreview(false);
            mTXLivePusher.setPushListener(null);
            mTXLivePusher.stopPusher();
        }
    }

    @Override
    public void resumePusher() {
        if (mTXLivePusher == null){
            mTXLivePusher.resumePusher();
            mTXLivePusher.startCameraPreview(mTXCloudVideoView);
            mTXLivePusher.resumeBGM();
        }
    }

    @Override
    public void pausePusher() {
        if (mTXLivePusher == null){
            mTXLivePusher.pausePusher();
        }
    }

    @Override
    public void showSettingPopupWindow(View targetView, int[] locations) {
        targetView.setBackgroundResource(R.drawable.icon_setting_down);
//        if ()

    }

    @Override
    public void changeLiveStatus(String userId, int status) {

    }

    @Override
    public void stopLive(String userId, String groupId) {
        StopLiveRequest request = new StopLiveRequest(RequestComm.stopLive,userId,groupId);
        AsyncHttp.instance().postJson(request, new AsyncHttp.IHttpListener() {
            @Override
            public void onStart(int requestId) {

            }

            @Override
            public void onSuccess(int requestId, Response response) {

            }

            @Override
            public void onFailure(int requestId, int httpStatus, Throwable error) {

            }
        });
    }
}
