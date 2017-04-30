package com.dali.admin.livestreaming.mvp.presenter.Ipresenter;

import android.app.Activity;
import android.net.Uri;

import com.dali.admin.livestreaming.mvp.view.Iview.BaseView;

/**
 * 开始直播设置
 * Created by dali on 2017/4/27.
 */

public abstract class IPublishPresenter implements BasePresenter {

    protected BaseView mBaseView;

    public IPublishPresenter(BaseView baseView) {
        mBaseView = baseView;
    }

    /**
     * 检查推流权限
     * @param activity
     * @return
     */
    public abstract boolean checkPublishPermission(Activity activity);

    /**
     * 检查录制权限
     * @return
     */
    public abstract boolean checkSrcRecordPermission();

    /**
     * 裁剪图片
     * @param imgUri 图片地址
     * @return
     */
    public abstract Uri cropImage(Uri imgUri);

    /**
     * 开始直播
     * @param title 直播房间标题
     * @param liveType 直播类型
     * @param location 位置
     * @param bitrateType 码率类型
     * @param isRecord 是否录制
     */
    public abstract void doPublish(String title,int liveType,String location,int bitrateType,boolean isRecord);

    /**
     * 直播定位
     */
    public abstract void doLocation();

    /**
     * 选择图片方式：相机、相册
     * @param mPermission 权限
     * @param type 类型
     * @return
     */
    public abstract Uri pickImage(boolean mPermission,int type);

    /**
     * 上传图片
     * @param path 图片路径
     */
    public abstract void doUploadPic(String path);


}

