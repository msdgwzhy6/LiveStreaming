package com.dali.admin.livestreaming.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.utils.Constants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePlayerActivity extends BaseActivity {

    private TXCloudVideoView mPlayerView;
    private TXLivePlayer mLivePlayer;
    private String flvUrl;

    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //mPlayerView即step1中添加的界面view
        mPlayerView = obtainView(R.id.video_view);
        //创建player对象
        mLivePlayer = new TXLivePlayer(this);
        mLivePlayer.enableHardwareDecode(true);
        //关键player对象与界面view
        mPlayerView.setVisibility(View.VISIBLE);
        mLivePlayer.setPlayerView(mPlayerView);

        flvUrl = getIntent().getStringExtra(Constants.PLAY_URL);
        if (flvUrl !=null) {
            mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐FLV
        }else {
            showToast("player url is null");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_player;
    }

    public static void invoke(Activity activity, String playerUrl) {
        Intent intent = new Intent(activity,LivePlayerActivity.class);
        intent.putExtra(Constants.PLAY_URL,playerUrl);
        activity.startActivityForResult(intent,Constants.LIVE_PLAYER_REQUEST_CODE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停
        mLivePlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //继续
        mLivePlayer.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        mPlayerView.onDestroy();
    }
}
