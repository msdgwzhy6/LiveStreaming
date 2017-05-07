package com.dali.admin.livestreaming.mvp.presenter.Ipresenter;

import android.view.View;

import com.dali.admin.livestreaming.mvp.view.Iview.BaseView;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * 推流
 * Created by dali on 2017/5/7.
 */

public abstract class IPusherPresenter implements BasePresenter {
    protected BaseView mBaseView;

    public IPusherPresenter(BaseView baseView) {
        mBaseView = baseView;
    }

    /**
     * 获取推流地址
     * @param userId 用户Id
     * @param groupId 群组Id
     * @param title 直播房间标题
     * @param coverPic 直播封面图
     * @param nickName 昵称
     * @param headPic 头像
     * @param location 位置
     */
    public abstract void getPusherUrl(final String userId, final String groupId, final String title,
                                      final String coverPic, final String nickName, final String headPic, final String location);

    /**
     * 开始推流
     * @param videoView 腾讯云直播控件
     * @param pusherConfig 推流配置
     * @param pushUrl 推流地址
     */
    public abstract void startPusher(TXCloudVideoView videoView, TXLivePushConfig pusherConfig, String pushUrl);

    /**
     * 设置推流配置信息
     * @param pusherConfig
     */
    public abstract void setConfig(TXLivePushConfig pusherConfig);

    /**
     * 停止推流
     */
    public abstract void stopPusher();

    /**
     * 重新开始推流
     */
    public abstract void resumePusher();

    /**
     * 停止推流
     */
    public abstract void pausePusher();

    /**
     * 直播设置弹出窗口（开闪光、翻转、开美颜、滤镜）
     * @param targetView
     * @param locations
     */
    public abstract void showSettingPopupWindow(View targetView, int[] locations);

    /**
     * 直播状态改变1，在线，直播中，0不在线，直播结束
     * @param userId 用户id
     * @param status 状态
     */
    public abstract void changeLiveStatus(String userId, int status);

    /**
     * 结束直播
     * @param userId 用户id
     * @param groupId 群组id
     */
    public abstract void stopLive(String userId, String groupId);


}
