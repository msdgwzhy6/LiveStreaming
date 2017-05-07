package com.dali.admin.livestreaming.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.http.AsyncHttp;
import com.dali.admin.livestreaming.http.request.CreateLiveRequest;
import com.dali.admin.livestreaming.http.request.RequestComm;
import com.dali.admin.livestreaming.http.response.CreateLiveResp;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.utils.AsimpleCache.ACache;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * 直播发布
 * Created by dali on 2017/5/7.
 */
public class LivePublishActivity extends BaseActivity{

    private static final String TAG = LivePublishActivity.class.getSimpleName();

    private TXCloudVideoView mTXCloudVideoView;
    private TXLivePusher  mLivePusher;
    private String mPushUrl = "rtmp://5438.livepush.myqcloud.com/live/5438_102?bizid=5438&txSecret=a9986ea5417b7cd4ab67c1f935e1892a&txTime=590ee3a0&record=flv";

    @Override
    protected void setBeforeLayout() {
        super.setBeforeLayout();
    }


    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.disableLog(false);
        }

    }

    public void getPushUrl(final String userId, final String groupId, final String title, final String coverPic, final String location){
        final CreateLiveRequest request = new CreateLiveRequest(RequestComm.createLive,userId,groupId,title,coverPic,location,0);
        Log.e("imLogin","liveUrl:"+request.getUrl());
        AsyncHttp.instance().postJson(request, new AsyncHttp.IHttpListener() {
            @Override
            public void onStart(int requestId) {

            }

            @Override
            public void onSuccess(int requestId, Response response) {
                if (response.getStatus() == RequestComm.SUCCESS){
                    CreateLiveResp resp = (CreateLiveResp) response.getData();
                    if (resp != null){
                        mPushUrl = resp.getPushUrl();
                        if (!TextUtils.isEmpty(mPushUrl)){
                            startPublish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int requestId, int httpStatus, Throwable error) {

            }
        });
    }

    @Override
    protected void initView() {
        mTXCloudVideoView = obtainView(R.id.video_view);
        mTXCloudVideoView.setVisibility(View.VISIBLE);

        getPushUrl(ACache.get(this).getAsString("user_id"),"@TGS#aQ2TUQJE4","菜鸟窝美女直播","","不显示地理位置");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_publish;
    }


    private void startPublish() {
        mLivePusher = new TXLivePusher(this);
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        mLivePusher.setConfig(mLivePushConfig);

        mLivePusher.startPusher(mPushUrl);
        mLivePusher.startCameraPreview(mTXCloudVideoView);
    }

}
